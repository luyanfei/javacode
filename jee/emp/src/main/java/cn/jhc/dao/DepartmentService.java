package cn.jhc.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.jhc.bean.Department;

@Repository("deptService")
public class DepartmentService implements DepartmentDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override @Transactional
	public void save(Department department) {
		em.persist(department);
	}

}
