package com.examples.ezoo.servlets;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.examples.ezoo.dao.AnimalDAO;
import com.examples.ezoo.dao.DAOUtilities;
import com.examples.ezoo.model.Animal;
//
import com.examples.ezoo.dao.FeedingScheduleDAO;
//import com.examples.ezoo.dao.DAOUtilities;
import com.examples.ezoo.model.FeedingSchedule;

import com.examples.ezoo.dao.AnimalScheduleJoinDAO;
//import com.examples.ezoo.model.AnimalScheduleJoin;


/**
 * Servlet implementation class AddFeedingScheduleServlet
 */
@WebServlet("/assignFeedingSchedule")
public class AssignFeedingScheduleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L; //this is used for Serializable. for more detail, see https://stackoverflow.com/a/285809
       
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("assignFeedingSchedule.jsp").forward(request, response); 
		}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get Parameters
		
		//We MUST convert to a Long since parameters are always Strings

		long animal_id = Long.parseLong(request.getParameter("assign_animal_id")); 
		long schedule_id = Long.parseLong(request.getParameter("assign_schedule_id"));
		
		//Call DAO methods
		AnimalDAO adao = DAOUtilities.getAnimalDao(); //the animalDaoImpl methods are not registering, even though that is the class .getAnimalDao() returns, but here the AnimalDAO class is what the var is declared as
		FeedingScheduleDAO fdao = DAOUtilities.getFeedingScheduleDao();
		AnimalScheduleJoinDAO asj_dao = DAOUtilities.getAnimalScheduleJoinDao();
		
		try {
//			connection = DAOUtilities.getConnection();
			
			Animal animal = adao.getAnimalByID(animal_id);
//			System.out.println(animal.getAnimalID());
//			System.out.println("%%%%%%%%%%%%");
			FeedingSchedule schedule = fdao.getScheduleByID(schedule_id);
//			System.out.println(schedule.getFeedingScheduleID());
//			System.out.println("&&&&&&&&&&&&");
			
			//i was worried the above might introduce async complications, but it doesn't seem to
			
			asj_dao.createJoin(animal, schedule);
			
			request.getSession().setAttribute("message", "Feeding schedule id " + schedule.getFeedingScheduleID() + "successfully assigned to animal id " + animal.getAnimalID());
			request.getSession().setAttribute("messageClass", "alert-success");
			
//			response.sendRedirect("manageAnimalSchedules.jsp"); //no data is showing up on the table after adding.
//			request.getRequestDispatcher("manageAnimalSchedules.jsp").forward(request, response); // here, the table doesn't show the latest addition
			response.sendRedirect("manageAnimalSchedules"); //this works. the .jsp throws it off for whatever reason
		}
		catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			
			request.getSession().setAttribute("message", "Schedule Id of " + schedule_id + " is already assigned to " + animal_id + "."); //i don't think this is actually a constraint of the DB
			request.getSession().setAttribute("messageClass", "alert-danger");
			
			request.getRequestDispatcher("assignFeedingSchedule.jsp").forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
			
			//change the message
			request.getSession().setAttribute("message", "There was a problem assigning the schedule at this time");
			request.getSession().setAttribute("messageClass", "alert-danger");
			
			
			request.getRequestDispatcher("assignFeedingSchedule.jsp").forward(request, response); //TODO: doublecheck this passed string
		} 
	}
}
