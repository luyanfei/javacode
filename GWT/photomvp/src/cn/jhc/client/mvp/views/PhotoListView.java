package cn.jhc.client.mvp.views;

import java.util.Vector;

import cn.jhc.client.mvp.presenters.PhotoListPresenter;
import cn.jhc.shared.PhotoDetails;

import com.google.gwt.user.client.ui.IsWidget;

public interface PhotoListView extends IsWidget {
	void setPresenter(PhotoListPresenter presenter);

	void addPictures(Vector<PhotoDetails> photos);
}
