package cn.jhc.bean;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
	name = "employee",
	uniqueConstraints=@UniqueConstraint(columnNames= {"name"})
)
public class Employee {

	@TableGenerator(name="emp_gen",table="id_gen",initialValue=100)
	@Id @GeneratedValue(strategy=GenerationType.TABLE,generator="emp_gen")
	private long id;

	private String name;
	
	private long salary;
	
	public Employee() {}
	
	public Employee(String name, long salary) {
		super();
		this.name = name;
		this.salary = salary;
	}

	@ManyToOne
	@JoinColumn(name="dept_id")
	private Department department;
	
	@OneToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="parking_id")
	private ParkingSpace parkingSpace;
	
	@ManyToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name="emp_proj",
			joinColumns=@JoinColumn(name="emp_id"),
			inverseJoinColumns=@JoinColumn(name="proj_id"))
	private Collection<Project> projects = new ArrayList<Project>();
	
	@OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinTable(name="emp_phone",
			joinColumns=@JoinColumn(name="emp_id"),
			inverseJoinColumns=@JoinColumn(name="phone_id"))
	private Collection<Phone> phones = new ArrayList<Phone>();
	
	@Embedded private Address address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public long getId() {
		return id;
	}

	public void addProject(Project proj) {
		if(!this.getProjects().contains(proj))
			this.getProjects().add(proj);
		if(!proj.getEmployees().contains(this))
			proj.getEmployees().add(this);
	}
	
	public ParkingSpace getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(ParkingSpace parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public Collection<Project> getProjects() {
		return projects;
	}

	public void setProjects(Collection<Project> projects) {
		this.projects = projects;
	}

	public Collection<Phone> getPhones() {
		return phones;
	}
	
	public void addPhone(Phone phone) {
		if(!this.getPhones().contains(phone))
			this.getPhones().add(phone);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary
				+ "]";
	}

}