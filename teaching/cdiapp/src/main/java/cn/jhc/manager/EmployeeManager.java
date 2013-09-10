package cn.jhc.manager;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cn.jhc.annotations.Transactional;
import cn.jhc.bean.Employee;

@SessionScoped
@Named
public class EmployeeManager implements Serializable, Manager {

	private static final long serialVersionUID = -3842248385950368874L;

	@Inject
	private EntityManager entityManager;

	@Transactional
	public List<Employee> getEmployees() {
		TypedQuery<Employee> q = entityManager
				.createQuery("select u from Employee u", Employee.class);
		return q.getResultList();
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Transactional
	public Employee find(int id) {
		return entityManager.find(Employee.class, id);
	}

	@Transactional
	public void flush() {
		entityManager.flush();
	}
}
