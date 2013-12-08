package cn.jhc.dao;

import cn.jhc.bean.Employee;

public interface EmployeeDao {
	void save(Employee employee);
	Employee fineById(long id);
}
