package com.tianle.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianle.service.organization.OrgService;
import com.tianle.service.register.AddSthService;

public class AddSth extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		AddSthService ass = new AddSthService(); 
		String type = request.getParameter("type");
		if(type != null) {
			if(type.equals("addclass")) {
				int grade = Integer.parseInt(request.getParameter("grade"));
				String college = request.getParameter("college");
				String classname = request.getParameter("classname");
				if (ass.addClass(grade, college, classname)) {
					//输出添加班级成功信息
					PrintWriter out = response.getWriter();
					out.println("添加班级成功");
					out.close();
				}
			} else if(type.equals("addorg")) {
				String orgname = request.getParameter("orgname");
				String fateruuid = request.getParameter("fatheruuid");
				String uuid = new OrgService().addOrg(orgname, fateruuid);
				if(!uuid.equals("")) {
					PrintWriter out = response.getWriter();
					out.println("添加班级成功,uuid为 " + uuid);
					out.println("组织名称为: " + orgname);
					out.close();
				}
			}
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
