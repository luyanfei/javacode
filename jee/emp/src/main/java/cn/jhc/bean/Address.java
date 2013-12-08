package cn.jhc.bean;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable @Access(AccessType.FIELD)
public class Address {
	
	private String street;
	private String city;
	private String state;
	@Column(name="zip_code")
	private String zip;
	
	public Address() {}
	
	public Address(String street, String city, String state, String zip) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
}
