package cn.jhc.client.mvp.views.impl;

import cn.jhc.client.mvp.presenters.PhotoListPresenter;
import cn.jhc.client.mvp.views.PhotoListTextView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PhotoListTextViewImpl extends Composite implements PhotoListTextView{

	private static PhotoListTextViewImplUiBinder uiBinder = GWT
			.create(PhotoListTextViewImplUiBinder.class);

	interface PhotoListTextViewImplUiBinder extends
			UiBinder<Widget, PhotoListTextViewImpl> {
	}

	public PhotoListTextViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(PhotoListPresenter presenter) {
	}

}
