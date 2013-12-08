package cn.jhc.dao;

import java.util.List;

import cn.jhc.bean.Department;
import cn.jhc.bean.Employee;

public interface JoinDao {

	public List<Employee> findWithNoJoin(String deptName);

	public List<Department> findWithJoin(String name);

	public List<Department> findWithFetchJoin(String name);

}