package cn.jhc.client.mvp;

import cn.jhc.client.mvp.views.PhotoDetailsView;
import cn.jhc.client.mvp.views.PhotoListTextView;
import cn.jhc.client.mvp.views.PhotoListView;
import cn.jhc.client.mvp.views.WelcomeView;
import cn.jhc.client.mvp.views.impl.PhotoDetailsViewImpl;
import cn.jhc.client.mvp.views.impl.PhotoListTextViewImpl;
import cn.jhc.client.mvp.views.impl.PhotoListViewImpl;
import cn.jhc.client.mvp.views.impl.WelcomeViewImpl;
import cn.jhc.shared.PhotoAlbumService;
import cn.jhc.shared.PhotoAlbumServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

public class ClientFactoryImpl implements ClientFactory {
	private static EventBus eventBus;
	private static PhotoAlbumServiceAsync rpcService;
	private static PhotoDetailsView detailView;
	private static PhotoListView listView;
	private static WelcomeView welcomeView;
	private static PhotoListTextView photoListTextView;
	private static PlaceController placeController;
	
	public EventBus getEventBus() {
		if (eventBus == null) eventBus = new SimpleEventBus();
		return eventBus;
	}

	public PhotoAlbumServiceAsync getPhotoServices() {
		if (rpcService == null) 
			rpcService = GWT.create(PhotoAlbumService.class);
		return rpcService;
	}

	public PhotoDetailsView getPhotoView() {
		if (detailView == null) detailView = new PhotoDetailsViewImpl();
		return detailView;
	}

	public PhotoListView getListView() {
		if (listView == null) listView = new PhotoListViewImpl();
		return listView;
	}

	public WelcomeView getWelcomeView() {
		if (welcomeView == null) welcomeView = new WelcomeViewImpl();
		return welcomeView;
	}

	@Override
	public PhotoListTextView getListTextView() {
		if(photoListTextView == null)
			photoListTextView = new PhotoListTextViewImpl();
		return photoListTextView;
	}

	@Override
	public PlaceController getPlaceController() {
		if(placeController == null)
			placeController = new PlaceController(getEventBus());
		return placeController;
	}

}
