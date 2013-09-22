package cn.jhc.client.mvp.views.impl;

import cn.jhc.client.mvp.presenters.PhotoDetailsPresenter;
import cn.jhc.client.mvp.ui.AppMenuBar;
import cn.jhc.client.mvp.ui.EditableLabel;
import cn.jhc.client.mvp.ui.TagListWidget;
import cn.jhc.client.mvp.views.PhotoDetailsView;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;

public class PhotoDetailsViewImpl extends Composite implements PhotoDetailsView {

	private PhotoDetailsPresenter presenter;
	private final EditableLabel title;
	private final TagListWidget tags;
	private final DockLayoutPanel container;
	Image thePhoto;

	public PhotoDetailsViewImpl() {

		DockLayoutPanel view = new DockLayoutPanel(Unit.PX);

		view.addNorth(new AppMenuBar(), 25);

		container = new DockLayoutPanel(Unit.PX);
		title = new EditableLabel();
		tags = new TagListWidget();
		thePhoto = new Image();
		thePhoto.setSize("500px", "400px");

		container.addNorth(title, 30);
		FlowPanel photo = new FlowPanel();

		photo.add(thePhoto);
		photo.add(tags);
		container.add(photo);
		container.forceLayout();

		view.add(container);

		initWidget(view);

		view.setSize("100%", "100%");
		view.forceLayout();

		bind();
	}

	private void bind() {
		title.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				if (presenter != null) {
					presenter.onUpdateTitle();
				}
			}

		});
		tags.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				if (presenter != null) {
					presenter.onUpdateTags();
				}
			}
		});
	}

	@Override
	public HasValue<String> getPhotoTitle() {
		return title;
	}

	@Override
	public HasValue<String> getPhotoTags() {
		return tags;
	}

	@Override
	public void setPhoto(String url) {
		// thePhoto.setUrl(url);
		// Actually, the URL in our example is really the background colour
		thePhoto.getElement().getStyle().setBackgroundColor(url);
	}

	@Override
	public void setPresenter(PhotoDetailsPresenter presenter) {
		this.presenter = presenter;
	}

}
