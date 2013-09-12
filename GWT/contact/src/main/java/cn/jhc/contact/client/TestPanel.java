package cn.jhc.contact.client;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

import cn.jhc.contact.client.ContactProxy.PhoneProxy;
import cn.jhc.contact.client.Factory.ContactRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class TestPanel extends Composite {

	private static TestPanelUiBinder uiBinder = GWT
			.create(TestPanelUiBinder.class);
	private Logger log = Logger.getLogger("");
	private Factory factory;

	interface TestPanelUiBinder extends UiBinder<Widget, TestPanel> {
	}

	public TestPanel() {
		factory = GWT.create(Factory.class);
		factory.initialize(new SimpleEventBus());
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button persistButton;
	@UiField
	Button countButton;
	@UiField
	Button persistInvalidButton;
	@UiField
	Button listButton;
	@UiField
	Button deleteAllButton;
	@UiField
	TextBox inputText;
	@UiField
	Button fetchButton;
	@UiField
	Button updateButton;
	@UiField
	Button deleteButton;

	@UiHandler("persistButton")
	void onClick(ClickEvent e) {
		String rand = "" + (int) (Math.random() * 9999);
		ContactRequest request = factory.createContactRequest();
		PhoneProxy phoneProxy = request.create(PhoneProxy.class);
		phoneProxy.setType("Home");
		phoneProxy.setNumber("555-" + rand);

		ContactProxy contactProxy = request.create(ContactProxy.class);
		contactProxy.setEmail(rand + "@example.com");
		contactProxy.setName(rand);
		contactProxy.setPhones(Arrays.asList(phoneProxy));
		contactProxy.setNotes("Random notes for " + rand);

		request.persist().using(contactProxy).fire();
	}

	@UiHandler("countButton")
	void onCountButtonClick(ClickEvent event) {
		Receiver<Integer> receiver = new Receiver<Integer>() {

			@Override
			public void onSuccess(Integer response) {
				log.info(response.toString());
			}
		};
		factory.createContactRequest().count().fire(receiver);
	}

	@UiHandler("listButton")
	void onListButtonClick(ClickEvent event) {
		Receiver<List<ContactProxy>> receiver = new Receiver<List<ContactProxy>>() {

			@Override
			public void onSuccess(List<ContactProxy> response) {
				for (ContactProxy contactProxy : response) {
					log.info("Contact: " + contactProxy.getId() + "="
							+ contactProxy.getEmail());
				}
			}

			@Override
			public void onFailure(ServerFailure error) {
				Window.alert(error.getMessage());
			}
		};

		factory.createContactRequest().findAllContacts().fire(receiver);
	}

	@UiHandler("fetchButton")
	void onFetchButtonClick(ClickEvent event) {
		Receiver<ContactProxy> receiver = new Receiver<ContactProxy>() {

			@Override
			public void onSuccess(ContactProxy contact) {
				log.info("id: " + contact.getId());
				log.info("name: " + contact.getName());
				log.info("email: " + contact.getEmail());
				if (contact.getPhones() != null) {
					for (PhoneProxy p : contact.getPhones())
						log.info("phone: " + p.getType() + "/" + p.getNumber());
				} else {
					log.info("phone: null");
				}
				log.info("notes: " + contact.getNotes());
			}
		};
		
		factory.createContactRequest()
			.find(inputTextAsLong())
			.with("phones").fire(receiver);
	}

	private long inputTextAsLong() {
		return Long.parseLong(inputText.getText());
	}
	
	@UiHandler("updateButton")
	void onUpdateButtonClick(ClickEvent event) {
		Receiver<ContactProxy> receiver = new Receiver<ContactProxy>() {

			@Override
			public void onSuccess(ContactProxy response) {
				ContactRequest request = factory.createContactRequest();
				ContactProxy contactProxy = request.edit(response);
				contactProxy.setNotes("Last Updated: " + new Date());
				request.persist().using(contactProxy).fire();
			}
		};
		
		factory.createContactRequest().find(inputTextAsLong()).fire(receiver);
	}
	@UiHandler("deleteButton")
	void onDeleteButtonClick(ClickEvent event) {
		Receiver<ContactProxy> receiver = new Receiver<ContactProxy>() {

			@Override
			public void onSuccess(ContactProxy response) {
				ContactRequest request = factory.createContactRequest();
				ContactProxy proxy = request.edit(response);
				request.remove().using(proxy).fire();
			}
		};
		factory.createContactRequest().find(inputTextAsLong()).fire(receiver);
	}
	@UiHandler("deleteAllButton")
	void onDeleteAllButtonClick(ClickEvent event) {
		Receiver<List<ContactProxy>> receiver = new Receiver<List<ContactProxy>>() {

			@Override
			public void onSuccess(List<ContactProxy> response) {
				ContactRequest request = factory.createContactRequest();
				for (ContactProxy contactProxy : response) {
					request.remove().using(request.edit(contactProxy));
				}
				request.fire();
			}
		};
		
		factory.createContactRequest().findAllContacts().fire(receiver);
	}
	@UiHandler("persistInvalidButton")
	void onPersistInvalidButtonClick(ClickEvent event) {
		ContactRequest request = factory.createContactRequest();
		ContactProxy contactProxy = request.create(ContactProxy.class);
		contactProxy.setEmail("Invalid email.");
		contactProxy.setName("");
		String notes = "";
		for (int i = 0; i < 20; i++) { notes += "too-long"; }
		contactProxy.setNotes(notes);
		
		Receiver<Void> receiver = new Receiver<Void>() {

			@Override
			public void onSuccess(Void response) {
				log.info("We passed validation.");
			}
			
			@Override
			public void onConstraintViolation(
					Set<ConstraintViolation<?>> violations) {
				for (ConstraintViolation<?> constraintViolation : violations) {
					log.info(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
				}
			}
			
			@Override
			public void onFailure(ServerFailure error) {
				log.info("Server failure: " + error.getMessage());
			}
		};
		
		request.persist().using(contactProxy).fire(receiver);
	}
}
