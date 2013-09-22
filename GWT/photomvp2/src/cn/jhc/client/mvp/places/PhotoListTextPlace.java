package cn.jhc.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PhotoListTextPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<PhotoListTextPlace> {

		@Override
		public PhotoListTextPlace getPlace(String token) {
			return new PhotoListTextPlace(token);
		}

		@Override
		public String getToken(PhotoListTextPlace place) {
			return place.getPage();
		}

	}

	private String page;

	public PhotoListTextPlace(String page) {
		super();
		this.page = page;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
}
