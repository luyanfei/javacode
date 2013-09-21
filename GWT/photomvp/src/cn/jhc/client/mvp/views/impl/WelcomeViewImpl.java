package cn.jhc.client.mvp.views.impl;

import cn.jhc.client.mvp.presenters.WelcomePresenter;
import cn.jhc.client.mvp.ui.AppMenuBar;
import cn.jhc.client.mvp.views.WelcomeView;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class WelcomeViewImpl extends Composite implements WelcomeView {

	private WelcomePresenter welcomePresenter;
	private Button showPhotoList;

	public WelcomeViewImpl() {
		DockLayoutPanel view = new DockLayoutPanel(Unit.PX);
		AppMenuBar menu = new AppMenuBar();
		showPhotoList = new Button("Show Photos");
		FlowPanel fp = new FlowPanel();
		fp.add(new Label("GWTiA MVP PhotoApp Example"));
		fp.add(new Label(
				"Click the Show Photos button below to see a grid of photos, click on a photo to do more"));
		fp.add(showPhotoList);
		view.addNorth(menu, 25);
		view.add(fp);
		view.setSize("100%", "100%");
		view.forceLayout();
		initWidget(view);

		bind();
	}

	public Widget asWidget() {
		return this;
	}

	public void bind() {
		showPhotoList.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (welcomePresenter!= null) {
					welcomePresenter.onShowPhotosButtonClicked();
				}
			}
		});
	}

	@Override
	public void setPresenter(WelcomePresenter presenter) {
		this.welcomePresenter = presenter;
	}

}
