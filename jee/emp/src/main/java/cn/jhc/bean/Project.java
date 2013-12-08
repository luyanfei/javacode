package cn.jhc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Project
 *
 */
@Entity
@Table(name="project")
public class Project implements Serializable {

	@TableGenerator(name="proj_gen",table="id_gen")
	@Id @GeneratedValue(strategy=GenerationType.TABLE, generator="proj_gen")
	private int id;
	
	private String name;
	
	@ManyToMany(mappedBy="projects")
	private Collection<Employee> employees = new ArrayList<Employee>();
	
	private static final long serialVersionUID = 1L;

	public Project() {
		super();
	}

	public Project(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}
   
	public void addEmployee(Employee e) {
		if(!this.getEmployees().contains(e))
			this.getEmployees().add(e);
		if(!e.getProjects().contains(this))
			e.getProjects().add(this);
	}
}
