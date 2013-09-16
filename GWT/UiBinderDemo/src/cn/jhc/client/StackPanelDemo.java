package cn.jhc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class StackPanelDemo extends Composite {

	private static StackPanelDemoUiBinder uiBinder = GWT
			.create(StackPanelDemoUiBinder.class);

	interface StackPanelDemoUiBinder extends UiBinder<Widget, StackPanelDemo> {
	}

	public StackPanelDemo() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
