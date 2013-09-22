package cn.jhc.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class PhotoDetailsPlace extends Place {

	@Prefix("showPhotoNumber")
	public static class Tokenizer implements PlaceTokenizer<PhotoDetailsPlace> {

		@Override
		public PhotoDetailsPlace getPlace(String token) {
			return new PhotoDetailsPlace(token);
		}

		@Override
		public String getToken(PhotoDetailsPlace place) {
			return place.getPhotoId();
		}


	}

	private String photoId;

	public PhotoDetailsPlace(String photoId) {
		super();
		this.photoId = photoId;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

}
