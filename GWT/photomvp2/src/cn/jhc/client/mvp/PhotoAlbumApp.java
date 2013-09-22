package cn.jhc.client.mvp;

import cn.jhc.client.mvp.events.AppBusyEvent;
import cn.jhc.client.mvp.events.AppBusyHandler;
import cn.jhc.client.mvp.events.AppFreeEvent;
import cn.jhc.client.mvp.events.AppFreeHandler;
import cn.jhc.client.mvp.places.WelcomePlace;
import cn.jhc.client.mvp.ui.BusyIndicator;
import cn.jhc.shared.PhotoAlbumServiceAsync;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PhotoAlbumApp {

	EventBus eventBus;
	PhotoAlbumServiceAsync rpcService;
	ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private Place defaultPlace = new WelcomePlace();

	public PhotoAlbumApp(PhotoAlbumServiceAsync rpcService, EventBus eventBus, AcceptsOneWidget appWidget) {
		PlaceController placeController = clientFactory.getPlaceController();
		
		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager  activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(appWidget);
		
		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlacesHistoryMapper appPlacesHistoryMapper = GWT.create(AppPlacesHistoryMapper.class);
		PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(appPlacesHistoryMapper);
		placeHistoryHandler.register(placeController, eventBus, defaultPlace);
		
		// Goes to the place represented on URL else default place
		placeHistoryHandler.handleCurrentHistory();
		
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		bind();
	}

	private void bind() {

		// Listen for AppBusy events on the event bus
		eventBus.addHandler(AppBusyEvent.getType(), new AppBusyHandler() {
			public void onAppBusyEvent(AppBusyEvent event) {
				BusyIndicator.busy();
			}
		});

		// Listen for AppFree events on the event bus
		eventBus.addHandler(AppFreeEvent.getType(), new AppFreeHandler() {
			public void onAppFreeEvent(AppFreeEvent event) {
				BusyIndicator.free();
			}
		});
	}

}
