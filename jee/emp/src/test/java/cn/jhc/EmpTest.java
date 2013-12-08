package cn.jhc;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.jhc.bean.Address;
import cn.jhc.bean.Company;
import cn.jhc.bean.Department;
import cn.jhc.bean.Employee;
import cn.jhc.bean.ParkingSpace;
import cn.jhc.bean.Phone;
import cn.jhc.bean.Project;

public class EmpTest {

	private EntityManagerFactory factory;
	private EntityManager em;
	
	@Before
	public void setUp() {
		factory = Persistence.createEntityManagerFactory("emp");
		em = factory.createEntityManager();
	}
	
	@Test
	public void testInsert() {
		Employee e = new Employee("张三", 3000);
		Department dep = new Department("软件开发部");
		dep.addEmployee(e);
		
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.persist(dep);
			//		em.persist(e);
			tx.commit();
		} catch (RuntimeException re) {
			re.printStackTrace();
			try {
				tx.rollback();
			} catch (RuntimeException rbEx) {
				rbEx.printStackTrace();
			}
			throw re;
		}
	}
	
	@Test
	public void testGenerator() {
		ArrayList<Employee> emplist = new ArrayList<Employee>();
		ArrayList<Department> deplist = new ArrayList<Department>();
		for(int i=0; i<52; i++) {
			Employee e = new Employee("random" + new Random().nextInt(), 3000);
			emplist.add(e);
			Department d = new Department("dept" + new Random().nextInt());
			d.getEmployees().add(e);
			deplist.add(d);
		}
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
//			for (Employee employee : emplist) {
//				em.persist(employee);
//			}
			for (Department department : deplist) {
				em.persist(department);
			}
			tx.commit();
		} catch (RuntimeException re) {
			re.printStackTrace();
			try {
				tx.rollback();
			} catch (RuntimeException rbEx) {
				rbEx.printStackTrace();
			}
			throw re;
		}
		
	}
	
	@Test
	public void testOneToOne() {
		ParkingSpace ps = new ParkingSpace(20, "时代停车场");
		Employee e = new Employee("张三", 3000);
		e.setParkingSpace(ps);
		assertNull(ps.getEmployee());
		
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.persist(e);
			tx.commit();
		} catch (RuntimeException re) {
			re.printStackTrace();
			try {
				tx.rollback();
			} catch (RuntimeException rbEx) {
				rbEx.printStackTrace();
			}
			throw re;
		}
	}
	
	@Test
	public void testManyToMany() {
		Employee e = new Employee("张三", 3000);
		Project p = new Project("工程一");
		e.addProject(p);
		
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.persist(e);
			tx.commit();
		} catch (RuntimeException re) {
			re.printStackTrace();
			try {
				tx.rollback();
			} catch (RuntimeException rbEx) {
				rbEx.printStackTrace();
			}
			throw re;
		}
	}
	
	@Test
	public void testUniqOneToMany() {
		Employee e = new Employee("张三", 3000);
		Phone phone = new Phone("智能手机","18300010001");
		e.addPhone(phone);
		
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.persist(e);
			tx.commit();
		} catch (RuntimeException re) {
			re.printStackTrace();
			try {
				tx.rollback();
			} catch (RuntimeException rbEx) {
				rbEx.printStackTrace();
			}
			throw re;
		}
	}
	
	@Test
	public void testEmbeddable() {
		Employee e = new Employee("张三", 3000);
		Address addr = new Address("新华街", "杭州", "浙江", "555555");
		e.setAddress(addr);
		
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.persist(e);
			tx.commit();
		} catch (RuntimeException re) {
			re.printStackTrace();
			try {
				tx.rollback();
			} catch (RuntimeException rbEx) {
				rbEx.printStackTrace();
			}
			throw re;
		}
	}
	
	@Test
	public void testSharedEmbeddable() {
		Employee e = new Employee("张三", 3000);
		Company c = new Company("网易");
		Address addr = new Address("新华街", "杭州", "浙江", "555555");
		e.setAddress(addr);
		c.setAddress(addr);
		
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.persist(e);
			em.persist(c);
			tx.commit();
		} catch (RuntimeException re) {
			re.printStackTrace();
			try {
				tx.rollback();
			} catch (RuntimeException rbEx) {
				rbEx.printStackTrace();
			}
			throw re;
		}
	}
	
	@After
	public void shutDown() {
		em.close();
		factory.close();
	}
}
