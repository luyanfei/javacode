package cn.jhc.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.jhc.bean.Employee;

@Repository("employeeDAO")
@Transactional
public class EmployeeDaoImpl implements EmployeeDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Employee employee) {
		entityManager.persist(employee);
	}

	@Override
	public Employee fineById(long id) {
		return entityManager.find(Employee.class, new Long(id));
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
