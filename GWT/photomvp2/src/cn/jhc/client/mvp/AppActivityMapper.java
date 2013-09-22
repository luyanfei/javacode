package cn.jhc.client.mvp;

import cn.jhc.client.mvp.activities.PhotoDetailsActivity;
import cn.jhc.client.mvp.activities.PhotoListActivity;
import cn.jhc.client.mvp.activities.PhotoListTextActivity;
import cn.jhc.client.mvp.activities.WelcomeActivity;
import cn.jhc.client.mvp.places.PhotoDetailsPlace;
import cn.jhc.client.mvp.places.PhotoListPlace;
import cn.jhc.client.mvp.places.PhotoListTextPlace;
import cn.jhc.client.mvp.places.WelcomePlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	public Activity getActivity(Place place) {
		if (place instanceof WelcomePlace)
			return new WelcomeActivity(clientFactory);
		else if (place instanceof PhotoListPlace)
			return new PhotoListActivity(clientFactory);
		else if (place instanceof PhotoListTextPlace)
			return new PhotoListTextActivity(clientFactory);
		else if (place instanceof PhotoDetailsPlace)
			return new PhotoDetailsActivity((PhotoDetailsPlace) place,
					clientFactory);
		else
			return null;
	}
}
