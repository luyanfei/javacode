package cn.jhc.client.mvp;

import cn.jhc.client.mvp.views.PhotoDetailsView;
import cn.jhc.client.mvp.views.PhotoListTextView;
import cn.jhc.client.mvp.views.PhotoListView;
import cn.jhc.client.mvp.views.WelcomeView;
import cn.jhc.shared.PhotoAlbumServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
	public EventBus getEventBus();
	PhotoAlbumServiceAsync getPhotoServices();
	PhotoDetailsView getPhotoView();
	PhotoListView getListView();
	WelcomeView getWelcomeView();
	PhotoListTextView getListTextView();
	public PlaceController getPlaceController();
}
