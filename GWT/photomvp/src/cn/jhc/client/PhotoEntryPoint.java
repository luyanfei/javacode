package cn.jhc.client;

import cn.jhc.client.mvp.MvpComposite;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class PhotoEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootPanel.get().add(new MvpComposite(),0,0);
	}

}
