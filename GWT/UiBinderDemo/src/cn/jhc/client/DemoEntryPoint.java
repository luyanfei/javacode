package cn.jhc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class DemoEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootPanel.get().add(new DemoPanel(), 0, 0);
	}

}
