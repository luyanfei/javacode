package cn.jhc.client.mvp.presenters.impl;

import cn.jhc.client.mvp.Tokens;
import cn.jhc.client.mvp.presenters.WelcomePresenter;
import cn.jhc.client.mvp.views.WelcomeView;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class WelcomePresenterImpl implements WelcomePresenter {

	private final WelcomeView welcomeView;

	public WelcomePresenterImpl(WelcomeView welcomeView) {
		this.welcomeView = welcomeView;
		bind();
	}

	public void bind() {
		welcomeView.setPresenter(this);
		// Welcome page does not listen for any events.
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(welcomeView.asWidget());
	}

	public void onShowPhotosButtonClicked() {
		History.newItem(Tokens.LIST);
	}

}
