package cn.jhc.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginDialogBox extends PopupPanel {

	interface MyStyle extends CssResource {
		String hidden();

		String borderOk();

		String borderEmpty();

		String borderError();
	}

	private static LoginDialogBoxUiBinder uiBinder = GWT
			.create(LoginDialogBoxUiBinder.class);

	interface LoginDialogBoxUiBinder extends UiBinder<Widget, LoginDialogBox> {
	}

	@UiField
	MyStyle mystyle;
	@UiField
	TextBox txtEmail;
	@UiField(provided = true)
	TextBox txtPassword;
	@UiField
	SpanElement eEmailErrorText;
	@UiField
	SpanElement ePassErrorText;
	@UiField
	Element eEmailError;
	@UiField
	Element ePassError;
	@UiField
	Button btnLogin;

	public LoginDialogBox() {
		setStyleName("");
		txtPassword = new PasswordTextBox();
		add(uiBinder.createAndBindUi(this));
		centerPopupOnPage();
		Window.addResizeHandler(resizeHandler);
	}

	/**
	 * A position callback to position the dialog box in the center of the
	 * screen, but a little high. If the browser area is too small, we use a min
	 * left/top offset of 10px.
	 */
	private void centerPopupOnPage() {
		int minOffset = 10; // px
		int knownDialogWidth = 400; // this is in the CSS
		int heightAboveCenter = 200; // will set the top to 200px above center

		int left = Math.max(minOffset, (Window.getClientWidth() / 2)
				- (knownDialogWidth / 2));
		int top = Math.max(minOffset, (Window.getClientHeight() / 2)
				- heightAboveCenter);
		setPopupPosition(left, top);
	}

	private ResizeHandler resizeHandler = new ResizeHandler() {

		@Override
		public void onResize(ResizeEvent event) {
			centerPopupOnPage();
		}
	};

	/* ************* UTILITY FUNCTIONS *************** */
	/*
	 * These methods are used by other methods within the class. Most of these
	 * are called by event handlers.
	 */

	private void setBorderStyle(TextBox textBox, String styleName) {
		textBox.removeStyleName(mystyle.borderOk());
		textBox.removeStyleName(mystyle.borderError());
		textBox.removeStyleName(mystyle.borderEmpty());
		textBox.addStyleName(styleName);
	}

	private boolean validateEmail() {
		return txtEmail.getText().matches("[^\\s@]+\\@[^\\s@]+");
	}

	private boolean validatePassword() {
		return txtPassword.getText().length() >= 6;
	}

	/* ************* EVENT HANDLERS *************** */
	/*
	 * These are the handlers for events within the widget. Things like
	 * focus/blur events and button clicks.
	 */

	@UiHandler("txtEmail")
	void emailHasFocus(FocusEvent event) {
		eEmailError.addClassName(mystyle.hidden());
		setBorderStyle(txtEmail, mystyle.borderOk());
	}

	@UiHandler("txtEmail")
	void emailBlur(BlurEvent event) {
		if (validateEmail()) {
			eEmailError.addClassName(mystyle.hidden());
			setBorderStyle(txtEmail, mystyle.borderOk());
		} else {
			eEmailErrorText.setInnerHTML("Email is not valid");
			eEmailError.removeClassName(mystyle.hidden());
			setBorderStyle(txtEmail, mystyle.borderError());
		}
	}

	@UiHandler("txtPassword")
	void passwordHasFocus(FocusEvent event) {
		ePassError.addClassName(mystyle.hidden());
		setBorderStyle(txtPassword, mystyle.borderOk());
	}

	@UiHandler("txtPassword")
	void passwordBlur(BlurEvent event) {
		if (validatePassword()) {
			ePassError.addClassName(mystyle.hidden());
			setBorderStyle(txtPassword, mystyle.borderOk());
		} else {
			ePassErrorText.setInnerHTML("Password is not valid");
			ePassError.removeClassName(mystyle.hidden());
			setBorderStyle(txtPassword, mystyle.borderError());
		}
	}

	@UiHandler("btnLogin")
	void submitLoginForm(ClickEvent event) {
		if (validateEmail() && validatePassword()) {
			Window.alert("Logging in...");
		}
	}

}
