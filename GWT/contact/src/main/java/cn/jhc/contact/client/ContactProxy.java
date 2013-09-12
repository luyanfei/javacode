package cn.jhc.contact.client;

import java.util.List;

import cn.jhc.contact.server.Contact;
import cn.jhc.contact.server.Contact.Phone;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyFor(value = Contact.class)
public interface ContactProxy extends EntityProxy {
	Long getId();

	String getName();

	void setName(String text);

	String getEmail();

	void setEmail(String text);

	List<PhoneProxy> getPhones();

	void setPhones(List<PhoneProxy> phones);

	String getNotes();

	void setNotes(String text);

	@ProxyFor(Phone.class)
	public interface PhoneProxy extends ValueProxy {
		String getType();

		void setType(String type);

		String getNumber();

		void setNumber(String number);
	}

}
