package cn.jhc.client.mvp.activities;

import cn.jhc.client.mvp.ClientFactory;
import cn.jhc.client.mvp.events.AppBusyEvent;
import cn.jhc.client.mvp.events.AppFreeEvent;
import cn.jhc.client.mvp.places.PhotoDetailsPlace;
import cn.jhc.client.mvp.presenters.PhotoDetailsPresenter;
import cn.jhc.client.mvp.views.PhotoDetailsView;
import cn.jhc.shared.PhotoAlbumServiceAsync;
import cn.jhc.shared.PhotoDetails;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PhotoDetailsActivity implements Activity, PhotoDetailsPresenter {

	private ClientFactory clientFactory;
	private PhotoDetails photoDetails;
	private String id;
	private PhotoAlbumServiceAsync rpcService;
	private EventBus appEventBus;
	private PhotoDetailsView photoDetailsView;
	private Request currHttpRequest;

	boolean madeEdits = false;
	boolean initialised = false;

	public PhotoDetailsActivity(PhotoDetailsPlace place,
			ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.id = place.getPhotoId();
		// Get and link up the necessary aspects
		this.appEventBus = clientFactory.getEventBus();
		this.rpcService = clientFactory.getPhotoServices();
	}

	@Override
	public void bind() {
		photoDetailsView.setPresenter(this);
	}

	@Override
	public void onUpdateTitle() {
		onUpdatePhoto();
	}

	@Override
	public void onUpdateTags() {
		onUpdatePhoto();
	}

	private void onUpdatePhoto() {
		if (!initialised)
			return;
		photoDetails.setTitle(photoDetailsView.getPhotoTitle().getValue());
		photoDetails.setTags(photoDetailsView.getPhotoTags().getValue());
		appEventBus.fireEvent(new AppBusyEvent());
		rpcService.updatePhotoDetails(photoDetails,
				new AsyncCallback<PhotoDetails>() {
					public void onSuccess(PhotoDetails result) {
						madeEdits = true;
						Scheduler.get().scheduleFixedDelay(
								new RepeatingCommand() {
									public boolean execute() {
										appEventBus
												.fireEvent(new AppFreeEvent());
										return false;
									}

								}, 1500);
					}

					public void onFailure(Throwable caught) {
						Scheduler.get().scheduleFixedDelay(
								new RepeatingCommand() {
									public boolean execute() {
										appEventBus
												.fireEvent(new AppFreeEvent());
										return false;
									}

								}, 500);
					}
				});
	}

	@Override
	public String mayStop() {
		if (madeEdits)
			return "Are you happy with the edits made to the photo?";
		else
			return null;
	}

	@Override
	public void onCancel() {
		if ((currHttpRequest != null) && (currHttpRequest.isPending())) {
			currHttpRequest.cancel();
			Window.alert("Cancelling RPC");
		}
	}

	@Override
	public void onStop() {
		if (photoDetailsView != null)
			photoDetailsView.setPhoto("");
	}

	@Override
	public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
		this.photoDetailsView = clientFactory.getPhotoView();
		bind();

		appEventBus.fireEvent(new AppBusyEvent());
		currHttpRequest = rpcService.getPhotoDetails(id,
				new AsyncCallback<PhotoDetails>() {
					public void onSuccess(PhotoDetails result) {
						photoDetails = result;
						photoDetailsView.getPhotoTitle().setValue(
								photoDetails.getTitle());
						photoDetailsView.getPhotoTags().setValue(
								photoDetails.getTags());
						photoDetailsView.setPhoto(photoDetails.getLargeUrl());
						appEventBus.fireEvent(new AppFreeEvent());
						panel.setWidget(photoDetailsView.asWidget());
						madeEdits = false;
						initialised = true;
					}

					public void onFailure(Throwable caught) {
						appEventBus.fireEvent(new AppFreeEvent());
					}
				});
	}

}
