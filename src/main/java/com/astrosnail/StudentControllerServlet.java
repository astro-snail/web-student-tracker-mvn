package com.astrosnail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			//new org.eclipse.jetty.plus.jndi.Resource("jdbc/web_student_tracker", dataSource);
			studentDbUtil = new StudentDbUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String theCommand = request.getParameter("command");
			
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			switch(theCommand) {
			
			case "LIST":
				listStudents(request, response);
				break;
			
			case "SEARCH":
				searchStudents(request, response);
				break;
				
			case "UPDATE":
				loadStudentUpdate(request, response);
				break;
				
			case "DELETE":
				loadStudentDelete(request, response);
				break;
		
			default:	
				listStudents(request, response);
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String theCommand = request.getParameter("command");
			
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			switch(theCommand) {
			
			case "ADD":
				addStudent(request, response);
				break;
				
			case "UPDATE":
				updateStudent(request, response);
				break;
				
			case "DELETE":
				deleteStudent(request, response);
				break;
				
			default:
				listStudents(request, response);
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void loadStudentUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		loadStudent(request, response, "/update-student-form.jsp");
	}

	private void loadStudentDelete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		loadStudent(request, response, "/delete-student-form.jsp");
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response, String path) throws Exception {
		
		int id = Integer.parseInt(request.getParameter("studentId"));
		
		try {
			Student theStudent = studentDbUtil.getStudent(id);
			request.setAttribute("THE_STUDENT", theStudent);
		} catch (SQLException e) {
			request.setAttribute("message", e.getMessage());
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student theStudent = new Student(firstName, lastName, email);
		
		studentDbUtil.addStudent(theStudent);
		
		response.sendRedirect(request.getContextPath() + request.getServletPath());
	}
	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student theStudent = new Student(id, firstName, lastName, email);
		
		studentDbUtil.updateStudent(theStudent);
		
		response.sendRedirect(request.getContextPath() + request.getServletPath());
	}
	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int id = Integer.parseInt(request.getParameter("studentId"));
				
		studentDbUtil.deleteStudent(id);
		
		response.sendRedirect(request.getContextPath() + request.getServletPath());
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Student> students = studentDbUtil.getStudents();
		request.setAttribute("STUDENT_LIST", students);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

	private void searchStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String theSearchName = request.getParameter("theSearchName");
		
		List<Student> students = studentDbUtil.searchStudents(theSearchName);
		request.setAttribute("STUDENT_LIST", students);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}
}
