package cn.jhc.shared;

import java.util.Vector;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PhotoAlbumServiceAsync {

	Request updatePhotoDetails(PhotoDetails photoDetails,
			AsyncCallback<PhotoDetails> callback);

	Request getPhotoDetails(String id, AsyncCallback<PhotoDetails> callback);

	Request getPhotoList(int start, int end,
			AsyncCallback<Vector<PhotoDetails>> callback);

}
