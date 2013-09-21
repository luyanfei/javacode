package cn.jhc.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PhotoAlbumServiceAsync {

	void updatePhotoDetails(PhotoDetails photoDetails,
			AsyncCallback<PhotoDetails> callback);

	void getPhotoDetails(String id, AsyncCallback<PhotoDetails> callback);

	void getPhotoList(int start, int end,
			AsyncCallback<Vector<PhotoDetails>> callback);

}
