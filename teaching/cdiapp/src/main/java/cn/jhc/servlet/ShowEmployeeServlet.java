package cn.jhc.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jhc.bean.Employee;
import cn.jhc.manager.EmployeeManager;

@WebServlet(name="show employee",urlPatterns= {"/show.do"})
public class ShowEmployeeServlet extends HttpServlet {
	@Inject
	private EmployeeManager empManager;
	
	@Override 
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = 0;
		try{
			id = Integer.parseInt(req.getParameter("id"));
		} catch (NumberFormatException nf) {
			resp.sendRedirect("index.html");
		}
		Employee e = empManager.find(id);
		req.getSession().setAttribute("emp", e);
		req.getRequestDispatcher("/edit.jsp").forward(req, resp);
	}
}
