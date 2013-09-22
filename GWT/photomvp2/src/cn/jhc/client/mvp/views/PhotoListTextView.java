package cn.jhc.client.mvp.views;

import cn.jhc.client.mvp.presenters.PhotoListPresenter;

import com.google.gwt.user.client.ui.IsWidget;

public interface PhotoListTextView extends IsWidget {
	void setPresenter(PhotoListPresenter presenter);
}
