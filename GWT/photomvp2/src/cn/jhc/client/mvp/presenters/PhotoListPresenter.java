package cn.jhc.client.mvp.presenters;

public interface PhotoListPresenter extends Presenter {
	public void onSelectPhotoClicked(String id);

	public void onNewPhotosNeeded();
}
