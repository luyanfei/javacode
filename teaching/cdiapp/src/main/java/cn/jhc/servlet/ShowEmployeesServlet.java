package cn.jhc.servlet;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jhc.bean.Employee;
import cn.jhc.manager.EmployeeManager;

@WebServlet(name = "show employees", urlPatterns = { "/showEmps.do" })
public class ShowEmployeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private EmployeeManager empManager;
	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Employee> emps = empManager.getEmployees();

		request.setAttribute("emps", emps);
		request.getRequestDispatcher("/showemps.jsp").forward(request,
				response);
	}

}
