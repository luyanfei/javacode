package cn.jhc.client.mvp.activities;

import cn.jhc.client.mvp.ClientFactory;
import cn.jhc.client.mvp.places.PhotoListPlace;
import cn.jhc.client.mvp.places.PhotoListTextPlace;
import cn.jhc.client.mvp.presenters.WelcomePresenter;
import cn.jhc.client.mvp.views.WelcomeView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class WelcomeActivity extends AbstractActivity implements
		WelcomePresenter {

	private ClientFactory clientFactory;
	private WelcomeView welcomeView;

	public WelcomeActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void start(AcceptsOneWidget panel,
			com.google.gwt.event.shared.EventBus eventBus) {
		welcomeView = clientFactory.getWelcomeView();
		panel.setWidget(welcomeView.asWidget());
		bind();
	}

	public void bind() {
		welcomeView.setPresenter(this);
	}

	public void onShowPhotosButtonClicked() {
		goTo(new PhotoListPlace("0"));
	}

	public void onShowPhotosTextButtonClicked() {
		goTo(new PhotoListTextPlace("0"));
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
