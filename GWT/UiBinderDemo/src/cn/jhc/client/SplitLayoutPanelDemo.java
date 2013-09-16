package cn.jhc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SplitLayoutPanelDemo extends Composite {

	private static SplitLayoutPanelDemoUiBinder uiBinder = GWT
			.create(SplitLayoutPanelDemoUiBinder.class);

	interface SplitLayoutPanelDemoUiBinder extends
			UiBinder<Widget, SplitLayoutPanelDemo> {
	}

	public SplitLayoutPanelDemo() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
