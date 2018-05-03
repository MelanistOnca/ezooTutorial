package com.examples.ezoo.servlets;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.examples.ezoo.dao.AnimalDAO;
import com.examples.ezoo.dao.DAOUtilities;
//import com.examples.ezoo.model.Animal;
//
//import com.examples.ezoo.dao.FeedingScheduleDAO;
//import com.examples.ezoo.dao.DAOUtilities;
//import com.examples.ezoo.model.FeedingSchedule;

//import com.examples.ezoo.model.AnimalScheduleJoin;
import com.examples.ezoo.dao.AnimalScheduleJoinDAO;

/**
 * Servlet implementation class AddFeedingScheduleServlet
 */
@WebServlet("/unassignFeedingSchedule")
public class UnassignFeedingScheduleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L; //this is used for Serializable. for more detail, see https://stackoverflow.com/a/285809
       
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.getRequestDispatcher("unassignFeedingSchedule.jsp").forward(request, response); // TODO: the string passed to getRequestDispatcher may need to change
//		response.sendRedirect("manageAnimalSchedules");
		request.getRequestDispatcher("manageAnimalSchedules.jsp").forward(request, response); //i feel like this is the wrong way to handle someone trying to get unassign... but it works for me
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get Parameters
		String combined_id = request.getParameter("id");
//		System.out.println(combined_id);
//		System.out.println("$%$%$%$%$%$% was combined_id");
		
		//split from https://stackoverflow.com/a/3481842
		String[] split_ids = combined_id.split(",");
//		System.out.println(split_ids);
//		System.out.println("was split_ids");
		Long animal_id = Long.parseLong(split_ids[0],10);
		Long schedule_id = Long.parseLong(split_ids[1],10);
//		System.out.println(animal_id);
//		System.out.println(schedule_id);
//		System.out.println("was animal_id then schedule_id");
		
//		long animal_id =99,schedule_id = 99;
		
		//Call DAO method
		AnimalScheduleJoinDAO dao = DAOUtilities.getAnimalScheduleJoinDao();
		
		try {
			dao.unassignJoin(animal_id, schedule_id);
			request.getSession().setAttribute("message", "Feeding schedule id " + schedule_id + " successfully assigned from animal id " + animal_id);
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("manageAnimalSchedules");


		}catch(SQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			
			//change the message
//			request.getSession().setAttribute("message", "Id of animal" + animal_id + " is already in use");
			request.getSession().setAttribute("message", "I don't think this message should ever appear");
			request.getSession().setAttribute("messageClass", "alert-danger");
			
			request.getRequestDispatcher("manageAnimalSchedules.jsp").forward(request, response); 
			
		}catch (Exception e){
			e.printStackTrace();
			
			//change the message
			request.getSession().setAttribute("message", "There was a problem unassigning the feeding schedule at this time");
			request.getSession().setAttribute("messageClass", "alert-danger");
			
			request.getRequestDispatcher("manageAnimalSchedules.jsp").forward(request, response);

		}
	}
}
