package cn.jhc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cn.jhc.bean.Department;
import cn.jhc.bean.Employee;

@Repository("joinService")
public class JoinService implements JoinDao {

	@PersistenceContext
	private EntityManager em;
	
	/* (non-Javadoc)
	 * @see cn.jhc.dao.JoinDao#findWithNoJoin(java.lang.String)
	 */
	@Override
	public List<Employee> findWithNoJoin(String deptName){
		return em.createQuery("select e from Employee e where e.department.name = :dept",Employee.class)
			.setParameter("dept", deptName)
			.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see cn.jhc.dao.JoinDao#findWithJoin(java.lang.String)
	 */
	@Override
	public List<Department> findWithJoin(String name){
		return em.createQuery("select d from Department d join d.employees e where e.name=:name", Department.class)
				.setParameter("name", name)
				.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see cn.jhc.dao.JoinDao#findWithFetchJoin(java.lang.String)
	 */
	@Override
	public List<Department> findWithFetchJoin(String name){
		return em.createQuery("select d from Department d join fetch d.employees e where e.name=:name", Department.class)
				.setParameter("name", name)
				.getResultList();
	}
	
}
