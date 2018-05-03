package com.examples.ezoo.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.examples.ezoo.dao.DAOUtilities;

import com.examples.ezoo.dao.AnimalScheduleJoinDAO;
import com.examples.ezoo.model.AnimalScheduleJoin;


/**
 * Servlet implementation class ManageAnimalScheduleServlet
 */
@WebServlet(description = "This servlet is the main interface into the Manage Animal Feeding Schedule System", urlPatterns = { "/manageAnimalSchedules" })
public class ManageAnimalScheduleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		AnimalScheduleJoinDAO dao = DAOUtilities.getAnimalScheduleJoinDao();
		List<AnimalScheduleJoin> joins = dao.getAllJoins();
//		System.out.println(joins);
//		System.out.println("was joins");
		
		request.getSession().setAttribute("joins", joins);
		
		request.getRequestDispatcher("manageAnimalSchedules.jsp").forward(request, response);
		
	}
	
}
