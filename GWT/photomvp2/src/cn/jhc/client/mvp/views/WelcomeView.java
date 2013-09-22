package cn.jhc.client.mvp.views;

import cn.jhc.client.mvp.presenters.WelcomePresenter;

import com.google.gwt.user.client.ui.IsWidget;

public interface WelcomeView extends IsWidget {
	void setPresenter(WelcomePresenter presenter);
}
