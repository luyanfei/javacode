package cn.jhc.bean;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Phone
 *
 */
@Entity
@Table(name="phone")
public class Phone implements Serializable {

	@TableGenerator(name="phone_gen",table="id_gen")
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="phone_gen")
	private int id;
	
	private String type;
	private String number;
	
	private static final long serialVersionUID = 1L;

	public Phone() {
		super();
	}

	public Phone(String type, String number) {
		super();
		this.type = type;
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phone other = (Phone) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
   
}
