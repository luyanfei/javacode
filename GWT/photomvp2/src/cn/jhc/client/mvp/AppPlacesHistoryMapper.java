package cn.jhc.client.mvp;

import cn.jhc.client.mvp.places.PhotoDetailsPlace;
import cn.jhc.client.mvp.places.PhotoListPlace;
import cn.jhc.client.mvp.places.PhotoListTextPlace;
import cn.jhc.client.mvp.places.WelcomePlace;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({PhotoDetailsPlace.Tokenizer.class,
	PhotoListPlace.Tokenizer.class,
	PhotoListTextPlace.Tokenizer.class,
	WelcomePlace.Tokenizer.class})
public interface AppPlacesHistoryMapper extends PlaceHistoryMapper {

}
