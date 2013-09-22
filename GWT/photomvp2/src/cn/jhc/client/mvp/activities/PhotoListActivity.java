package cn.jhc.client.mvp.activities;

import java.util.Vector;

import cn.jhc.client.mvp.ClientFactory;
import cn.jhc.client.mvp.events.AppBusyEvent;
import cn.jhc.client.mvp.events.AppFreeEvent;
import cn.jhc.client.mvp.places.PhotoDetailsPlace;
import cn.jhc.client.mvp.presenters.PhotoListPresenter;
import cn.jhc.client.mvp.views.PhotoListView;
import cn.jhc.shared.PhotoAlbumServiceAsync;
import cn.jhc.shared.PhotoDetails;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PhotoListActivity extends AbstractActivity implements
		PhotoListPresenter {

	private ClientFactory clientFactory;
	private PhotoAlbumServiceAsync rpcService;
	private EventBus appEventBus;
	private PhotoListView photoListView;
	
    int START = 0;
    int LENGTH = 10;
    
	public PhotoListActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.rpcService = clientFactory.getPhotoServices();
		this.appEventBus = clientFactory.getEventBus();
	}
	
	@Override
	public void bind() {
		photoListView.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		photoListView = clientFactory.getListView();
		this.onNewPhotosNeeded();
		bind();
		panel.setWidget(photoListView.asWidget());
	}

	@Override
	public void onSelectPhotoClicked(String id) {
		goTo(new PhotoDetailsPlace(id));
	}

	@Override
	public void onNewPhotosNeeded() {
		appEventBus.fireEvent(new AppBusyEvent());
		rpcService.getPhotoList(START,LENGTH,
				new AsyncCallback<Vector<PhotoDetails>>() {
					public void onSuccess(Vector<PhotoDetails> result) {
						photoListView.addPictures(result);
						appEventBus.fireEvent(new AppFreeEvent());
					}

					public void onFailure(Throwable caught) {
						Window.alert("Error");
						appEventBus.fireEvent(new AppFreeEvent());
					}
				});
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
