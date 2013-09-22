package cn.jhc.client.mvp.activities;

import cn.jhc.client.mvp.ClientFactory;
import cn.jhc.client.mvp.places.PhotoDetailsPlace;
import cn.jhc.client.mvp.presenters.PhotoListPresenter;
import cn.jhc.client.mvp.views.PhotoListTextView;
import cn.jhc.shared.PhotoAlbumServiceAsync;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PhotoListTextActivity extends AbstractActivity implements
		PhotoListPresenter {


	private ClientFactory clientFactory;
	private PhotoListTextView photoListTextView;
	private PhotoAlbumServiceAsync rpcService;
	private EventBus appEventBus;
	
	public PhotoListTextActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		this.rpcService = clientFactory.getPhotoServices();
		this.appEventBus = clientFactory.getEventBus();
	}
	
	@Override
	public void bind() {
		photoListTextView.setPresenter(this);
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		photoListTextView = clientFactory.getListTextView();
		this.onNewPhotosNeeded();
		bind();
		panel.setWidget(photoListTextView.asWidget());
	}

    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
    
	@Override
	public void onSelectPhotoClicked(String id) {
		goTo(new PhotoDetailsPlace(id));
	}

	@Override
	public void onNewPhotosNeeded() {
	}

}
