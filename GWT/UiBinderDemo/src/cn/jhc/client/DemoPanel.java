package cn.jhc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DemoPanel extends Composite {

	interface Resources extends ClientBundle {

		@Source("gwtia.jpg")
		ImageResource logo();

	}

	private static DemoPanelUiBinder uiBinder = GWT
			.create(DemoPanelUiBinder.class);
	@UiField Button showLoginPanel;
	@UiField Button hideLoginPanel;
	@UiField SimplePanel mainArea;
	@UiField Button showStackPanelDemoButton;
	@UiField Button disclosurePanelButton;
	@UiField Button splitLayoutButton;
	LoginDialogBox loginDialogBox = new LoginDialogBox();

	interface DemoPanelUiBinder extends UiBinder<Widget, DemoPanel> {
	}

	public DemoPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		setWidgetToMaxWidthAndHeight();
	}
	
    private void setWidgetToMaxWidthAndHeight ()
    {
        setWidth("100%");
        setHeight(Window.getClientHeight() + "px");
    }
	private void setMainPanelWidget(Widget widget) {
		mainArea.clear();
		mainArea.add(widget);
	}
	
	@UiHandler("showLoginPanel")
	void onShowLoginPanelClick(ClickEvent event) {
//		setMainPanelWidget(loginDialogBox);
		loginDialogBox.show();
	}

	@UiHandler("hideLoginPanel")
	void onHideLoginPanelClick(ClickEvent event) {
		loginDialogBox.hide();
	}
	@UiHandler("showStackPanelDemoButton")
	void onShowStackPanelDemoButtonClick(ClickEvent event) {
		setMainPanelWidget(new StackPanelDemo());
	}
	@UiHandler("disclosurePanelButton")
	void onDisclosurePanelButtonClick(ClickEvent event) {
		setMainPanelWidget(new DisclosurePanelDemo());
	}
	@UiHandler("splitLayoutButton")
	void onSplitLayoutButtonClick(ClickEvent event) {
		Widget widget = new SplitLayoutPanelDemo();
		widget.setSize("300px", "300px");
		setMainPanelWidget(widget);
	}
}
