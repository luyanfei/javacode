package cn.jhc.client.mvp;

import cn.jhc.shared.PhotoAlbumServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class MvpComposite extends Composite {
	ClientFactory clientFactory = GWT.create(ClientFactory.class);
	SimplePanel simplePanel = new SimplePanel();

	public MvpComposite() {
		EventBus eventBus = clientFactory.getEventBus();
		PhotoAlbumServiceAsync photoAlbumServiceAsync = clientFactory.getPhotoServices();
		new PhotoAlbumApp(photoAlbumServiceAsync, eventBus, simplePanel);
		initWidget(simplePanel);
		simplePanel.setSize("100%", "100%");
	}
}
