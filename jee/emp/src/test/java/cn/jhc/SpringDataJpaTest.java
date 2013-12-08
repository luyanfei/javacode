package cn.jhc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.jhc.bean.Employee;
import cn.jhc.springdata.EmployeeDao;

public class SpringDataJpaTest {

	private ApplicationContext context;
	
	@Before
	public void setUp(){
		context = new ClassPathXmlApplicationContext("spring-data-jpa.xml"); 
	}
	
	@Test
	public void testSpringDataJpaUse() {
		EmployeeDao dao = context.getBean(EmployeeDao.class);
		dao.save(new Employee("张三",1200));
	}
}
