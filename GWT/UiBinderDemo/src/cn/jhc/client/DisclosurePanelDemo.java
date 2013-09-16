package cn.jhc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DisclosurePanelDemo extends Composite {

	private static DisclosurePanelDemoUiBinder uiBinder = GWT
			.create(DisclosurePanelDemoUiBinder.class);

	interface DisclosurePanelDemoUiBinder extends
			UiBinder<Widget, DisclosurePanelDemo> {
	}

	public DisclosurePanelDemo() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
