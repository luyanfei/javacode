package cn.jhc;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jhc.bean.Department;
import cn.jhc.bean.Employee;
import cn.jhc.dao.DepartmentDao;
import cn.jhc.dao.JoinDao;
import cn.jhc.dao.JoinService;

public class SpringTest {

	private ApplicationContext context;
	
	@Before
	public void setUp() {
		context = new ClassPathXmlApplicationContext("app.xml");
	}
	
	@Test
	public void testEmployeeSave() {
		Employee e = new Employee("张三", 2000);
		Department d = new Department("软件开发部");
		Department d2 = new Department("人力资源部");
		d.addEmployee(e);
		
		DepartmentDao deptDao = context.getBean("deptService", DepartmentDao.class);
		deptDao.save(d);
		deptDao.save(d2);

	}
	
	@Test
	public void testFindWithNoJoin() {
		JoinDao dao = context.getBean("joinService", JoinDao.class);
		List<Employee> list = dao.findWithNoJoin("销售部");
		for(Employee e : list)
			System.out.println(e);
	}
	
	@Test
	public void testFindWithJoin() {
		JoinDao dao = context.getBean("joinService", JoinDao.class);
		List<Department> list = dao.findWithJoin("赵三");
		for(Department d : list)
			System.out.println(d);
	}
	
	@Test
	public void testFindWithFetchJoin() {
		JoinDao dao = context.getBean("joinService", JoinDao.class);
		List<Department> list = dao.findWithFetchJoin("赵三");
		for(Department d : list)
			System.out.println(d);
	}
}
