package cn.jhc.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jhc.bean.Employee;
import cn.jhc.manager.EmployeeManager;

@WebServlet(name="edit employee",urlPatterns= {"/edit.do"} )
public class EditEmployeeServlet extends HttpServlet {

	@Inject
	private EmployeeManager empManager;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Employee e = (Employee) req.getSession().getAttribute("emp");
		try {
			e.setName(req.getParameter("name"));
			e.setSalary(Long.parseLong(req.getParameter("salary")));
			e.setHireDate(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("hiredate")));
		} catch (Exception ex) {
			ex.printStackTrace();
			req.getSession().invalidate();
			resp.sendRedirect("index.html");
			return;
		} 
		resp.sendRedirect("index.html");
	}
}
