package cn.jhc.client.mvp.presenters.impl;

import cn.jhc.client.mvp.ClientFactory;
import cn.jhc.client.mvp.events.AppBusyEvent;
import cn.jhc.client.mvp.events.AppFreeEvent;
import cn.jhc.client.mvp.presenters.PhotoDetailsPresenter;
import cn.jhc.client.mvp.views.PhotoDetailsView;
import cn.jhc.shared.PhotoAlbumServiceAsync;
import cn.jhc.shared.PhotoDetails;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class PhotoDetailsPresenterImpl implements PhotoDetailsPresenter {

	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private PhotoDetails photoDetails;
	private String id;
	private final PhotoAlbumServiceAsync rpcService;
	private final EventBus eventBus;
	private final PhotoDetailsView photoDetailsView;

	public String getId() {
		return this.id;
	}

	public PhotoDetailsPresenterImpl(final PhotoDetailsView photoDetailsView,
			final String id) {
		this.id = id;
		this.rpcService = clientFactory.getPhotoServices();
		this.eventBus = clientFactory.getEventBus();
		this.photoDetailsView = photoDetailsView;
		eventBus.fireEvent(new AppBusyEvent());
		rpcService.getPhotoDetails(id, new AsyncCallback<PhotoDetails>() {
			public void onSuccess(PhotoDetails result) {
				photoDetails = result;
				photoDetailsView.getPhotoTitle().setValue(
						photoDetails.getTitle());
				photoDetailsView.getPhotoTags()
						.setValue(photoDetails.getTags());
				photoDetailsView.setPhoto(photoDetails.getLargeUrl());
				eventBus.fireEvent(new AppFreeEvent());
			}

			public void onFailure(Throwable caught) {
				eventBus.fireEvent(new AppFreeEvent());
			}
		});
		bind();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(photoDetailsView.asWidget());
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

	public void onUpdatePhoto() {
		photoDetails.setTitle(photoDetailsView.getPhotoTitle().getValue());
		photoDetails.setTags(photoDetailsView.getPhotoTags().getValue());
		eventBus.fireEvent(new AppBusyEvent());
		rpcService.updatePhotoDetails(photoDetails,
				new AsyncCallback<PhotoDetails>() {
					public void onSuccess(PhotoDetails result) {
						Scheduler.get().scheduleFixedDelay(
								new RepeatingCommand() {
									public boolean execute() {
										eventBus.fireEvent(new AppFreeEvent());
										return false;
									}

								}, 1500);
					}

					public void onFailure(Throwable caught) {
						Scheduler.get().scheduleFixedDelay(
								new RepeatingCommand() {
									public boolean execute() {
										eventBus.fireEvent(new AppFreeEvent());
										return false;
									}

								}, 500);
					}
				});
	}

}
