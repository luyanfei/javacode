package cn.jhc.bean;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Company
 *
 */
@Entity
@Table(name="company")
public class Company implements Serializable {
	
	@TableGenerator(name="comp_gen",table="id_gen")
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="comp_gen")
	private int id;
	
	private String name;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="state",column=@Column(name="province")),
		@AttributeOverride(name="zip",column=@Column(name="postal_code"))
	})
	private Address address;
	
	private static final long serialVersionUID = 1L;

	public Company() {
		super();
	}

	public Company(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
   
}
