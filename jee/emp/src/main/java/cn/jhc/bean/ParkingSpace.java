package cn.jhc.bean;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ParkingSpace
 *
 */
@Entity
@Table(name="parkingspace")
public class ParkingSpace implements Serializable {

	@TableGenerator(name="parking_gen",table="id_gen")
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="parking_gen")
	private int id;
	private int lot;   
	private String location;
	private static final long serialVersionUID = 1L;
	
	@OneToOne(mappedBy="parkingSpace")
	private Employee employee;

	public ParkingSpace() {
		super();
	}   
	
	public ParkingSpace(int lot, String location) {
		super();
		this.lot = lot;
		this.location = location;
	}

	public int getLot() {
		return this.lot;
	}

	public void setLot(int lot) {
		this.lot = lot;
	}  
	
	public int getId() {
		return this.id;
	}
 
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
   
}
