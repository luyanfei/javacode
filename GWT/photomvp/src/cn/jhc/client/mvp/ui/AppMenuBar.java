package cn.jhc.client.mvp.ui;

import cn.jhc.client.mvp.Tokens;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class AppMenuBar extends Composite {

	private static AppMenuBarUiBinder uiBinder = GWT
			.create(AppMenuBarUiBinder.class);

	interface AppMenuBarUiBinder extends UiBinder<Widget, AppMenuBar> {
	}

	public AppMenuBar() {
		initWidget(uiBinder.createAndBindUi(this));
		setUp();
	}

	@UiField MenuItem welcome;
	@UiField MenuItem list;
	@UiField MenuItem user;
	
	
	@SuppressWarnings("deprecation")
	private void setUp(){
		welcome.setCommand(new Command(){
			public void execute() {
				History.newItem(Tokens.HOME);
			}			
		});
		list.setCommand(new Command(){
			public void execute() {
				History.newItem(Tokens.LIST);
			}			
		});
		user.setCommand(new Command(){
			public void execute() {
				History.newItem(Tokens.USER);
			}			
		});
	}


}
