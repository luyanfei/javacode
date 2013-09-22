package cn.jhc.client.mvp.ui;

import cn.jhc.client.mvp.ClientFactory;
import cn.jhc.client.mvp.places.PhotoListPlace;
import cn.jhc.client.mvp.places.WelcomePlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class AppMenuBar extends Composite {

	private static AppMenuBarUiBinder uiBinder = GWT
			.create(AppMenuBarUiBinder.class);

	interface AppMenuBarUiBinder extends UiBinder<Widget, AppMenuBar> {
	}

	private ClientFactory clientFactory = GWT.create(ClientFactory.class);

	public AppMenuBar() {
		initWidget(uiBinder.createAndBindUi(this));
		setUp();
	}

	@UiField
	MenuItem welcome;
	@UiField
	MenuItem list;
	@UiField
	MenuItem user;

	@SuppressWarnings("deprecation")
	private void setUp() {
		welcome.setCommand(new Command() {
			public void execute() {
				goTo(new WelcomePlace());
			}
		});
		list.setCommand(new Command() {
			public void execute() {
				goTo(new PhotoListPlace("0"));
			}
		});
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
