package cn.jhc.springdata;

import org.springframework.data.repository.CrudRepository;

import cn.jhc.bean.Employee;

public interface EmployeeDao extends CrudRepository<Employee, Long> {

}
