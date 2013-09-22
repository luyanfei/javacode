package cn.jhc.client.mvp.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PhotoListPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<PhotoListPlace> {

		@Override
		public PhotoListPlace getPlace(String token) {
			return new PhotoListPlace(token);
		}

		@Override
		public String getToken(PhotoListPlace place) {
			return place.getPage();
		}

	}

	private String page;

	public PhotoListPlace(String page) {
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
