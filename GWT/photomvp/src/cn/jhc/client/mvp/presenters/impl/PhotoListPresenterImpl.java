package cn.jhc.client.mvp.presenters.impl;

import java.util.Vector;

import cn.jhc.client.mvp.ClientFactory;
import cn.jhc.client.mvp.Tokens;
import cn.jhc.client.mvp.events.AppBusyEvent;
import cn.jhc.client.mvp.events.AppFreeEvent;
import cn.jhc.client.mvp.presenters.PhotoListPresenter;
import cn.jhc.client.mvp.views.PhotoListView;
import cn.jhc.shared.PhotoAlbumServiceAsync;
import cn.jhc.shared.PhotoDetails;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class PhotoListPresenterImpl implements PhotoListPresenter {

	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	private PhotoAlbumServiceAsync rpcService;
	private EventBus eventBus;
	private PhotoListView photoListView;

	public PhotoListPresenterImpl(PhotoListView photoListView) {
		this.rpcService = clientFactory.getPhotoServices();
		this.eventBus = clientFactory.getEventBus();
		this.photoListView = photoListView;
		this.onNewPhotosNeeded();
		bind();
	}

	@Override
	public void onSelectPhotoClicked(String id) {
		History.newItem(Tokens.DETAIL + "&" + id);
	}

	int START = 0;
	int LENGTH = 10;

	@Override
	public void onNewPhotosNeeded() {
		eventBus.fireEvent(new AppBusyEvent());
		rpcService.getPhotoList(START, LENGTH,
				new AsyncCallback<Vector<PhotoDetails>>() {
					public void onSuccess(Vector<PhotoDetails> result) {
						photoListView.addPictures(result);
						eventBus.fireEvent(new AppFreeEvent());
					}

					public void onFailure(Throwable caught) {
						Window.alert("Error");
						eventBus.fireEvent(new AppFreeEvent());
					}
				});
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(photoListView.asWidget());
	}

	@Override
	public void bind() {
		photoListView.setPresenter(this);
	}

}
