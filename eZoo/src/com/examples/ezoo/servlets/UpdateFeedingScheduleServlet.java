package com.examples.ezoo.servlets;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.examples.ezoo.dao.AnimalDAO;
//import com.examples.ezoo.dao.DAOUtilities;
//import com.examples.ezoo.model.Animal;

import com.examples.ezoo.dao.FeedingScheduleDAO;
import com.examples.ezoo.dao.DAOUtilities;
import com.examples.ezoo.model.FeedingSchedule;

/**
 * Servlet implementation class AddFeedingScheduleServlet
 */
@WebServlet("/updateFeedingSchedule")
public class UpdateFeedingScheduleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L; //this is used for Serializable. for more detail, see https://stackoverflow.com/a/285809
       
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("updateFeedingSchedule.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get Parameters
		long id = Long.parseLong(request.getParameter("id")); //i PROBABLY should have the id as a fixed input where they click an update button on the feedingScheduleHome page, but for now we'll leave it like this
		
		String feeding_time = request.getParameter("feeding_time");

		String food = request.getParameter("food");
		String notes = request.getParameter("notes");
		String recurrence = request.getParameter("recurrence");

		//Create an FeedinSchedule object from the parameters
		FeedingSchedule feedingScheduleToSave = new FeedingSchedule(
				id, 
				feeding_time, 
				food,
				notes,
				recurrence
				);
		
		//Call DAO method
		FeedingScheduleDAO dao = DAOUtilities.getFeedingScheduleDao();
		try {
			dao.updateFeedingSchedule(feedingScheduleToSave);
			request.getSession().setAttribute("message", "Feeding Schedule successfully updated");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("feedingScheduleHome");


		}catch(SQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			//i don't think this should happen with an update, but it can't hurt to CMA anyway.
			request.getSession().setAttribute("message", "Id of " + feedingScheduleToSave.getFeedingScheduleID() + " is already in use");
			request.getSession().setAttribute("messageClass", "alert-danger");
			
//			request.getRequestDispatcher("updateFeedingSchedule.jsp").forward(request, response);
			request.getRequestDispatcher("feedingScheduleHome.jsp").forward(request, response); //in case of failed update, i want them to see what the schedules look like before trying to update again?
			
		}catch (Exception e){
			e.printStackTrace();
			request.getSession().setAttribute("message", "There was a problem updating the feeding schedule at this time");
			request.getSession().setAttribute("messageClass", "alert-danger");
			
//			request.getRequestDispatcher("updateFeedingSchedule.jsp").forward(request, response);
			request.getRequestDispatcher("feedingScheduleHome.jsp").forward(request, response); //same idea as above

		}
	}
}
