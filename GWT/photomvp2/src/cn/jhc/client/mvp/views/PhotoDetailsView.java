package cn.jhc.client.mvp.views;

import cn.jhc.client.mvp.presenters.PhotoDetailsPresenter;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;

public interface PhotoDetailsView extends IsWidget {
	HasValue<String> getPhotoTitle();
	HasValue<String> getPhotoTags();
	void setPhoto(String largeUrl);
	void setPresenter(PhotoDetailsPresenter presenter);
}
