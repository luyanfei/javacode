package cn.jhc.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jhc.manager.EmployeeManager;

@WebServlet(name="end session",urlPatterns= {"/endsession.do"})
public class EndSessionServlet extends HttpServlet {

	@Inject
	private EmployeeManager empManager;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		empManager.flush();
		req.getSession().invalidate();
	}
}
