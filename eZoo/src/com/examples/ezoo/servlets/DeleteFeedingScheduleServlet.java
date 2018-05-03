package com.examples.ezoo.servlets;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
//import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.examples.ezoo.dao.AnimalDAO;
//import com.examples.ezoo.dao.DAOUtilities;
//import com.examples.ezoo.model.Animal;

import com.examples.ezoo.dao.FeedingScheduleDAO;
//import com.examples.ezoo.dao.AnimalDAO;
import com.examples.ezoo.dao.DAOUtilities;
//import com.examples.ezoo.model.Animal;
//import com.examples.ezoo.model.FeedingSchedule;

/**
 * Servlet implementation class DeleteFeedingScheduleServlet
 */
@WebServlet("/deleteFeedingSchedule")
public class DeleteFeedingScheduleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L; //this is used for Serializable. for more detail, see https://stackoverflow.com/a/285809
       
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//				System.out.println("Logs from doGet on DeleteFeedingScheduleServlet");
//				System.out.println(request.getParameter("id")); //this is returning the value now, after i added the value="..." bit to the hidden input, because DUH YOU NEED A VALUE JACKASS
//				System.out.println();
//				
//				System.out.println(request.getParameterNames().toString());
//				
//				System.out.println(request.getParameterValues("id").toString());
//				System.out.println("getParameterMap start");
//				System.out.println(request.getParameterMap().toString());
//				System.out.println(request.getParameterMap().values());
//				System.out.println(request.getParameterMap().keySet());
//				System.out.println("getParameterMap end");
//				System.out.println(request.getParameter("id"));
//				System.out.println(request.getParameter("id^\\d*[1-9]\\d*$"));
//				System.out.println(request.getParameter("^\\d*[1-9]\\d*$"));
//				System.out.println(request.getParameter("3"));
//				System.out.println(request.getParameter("2"));

				// Populate the list into a variable that will be stored in the session
//				request.getSession().setAttribute("id", id);
				
		
		
		request.getRequestDispatcher("feedingScheduleHome.jsp").forward(request, response); 
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get Parameters
		//We MUST convert to a Long since parameters are always Strings
//		System.out.println(request);
//		System.out.println("was request");
//		System.out.println("logs from DeleteFeedingScheduleServlet");
//		System.out.println(request.getParameterNames().toString());
//		System.out.println(request.getParameterValues("id").toString());
//		System.out.println(request.getParameterMap().toString());
//		System.out.println(request.getParameter("id"));
//		System.out.println(request.getParameter("id^\\d*[1-9]\\d*$"));
//		System.out.println(request.getParameter("^\\d*[1-9]\\d*$"));
//		System.out.println(request.getParameter("3"));
		
		long feedingScheduleID = Long.parseLong(request.getParameter("id")); //backup
		//do i need to use a regex here so that I can specify exactly which submit i'm pulling from  on the feeding schedule homepage?
//		i feel like this shouldn't be necessary??
//		long feedingScheduleID = Long.parseLong(request.getParameter("id^\\d*[1-9]\\d*$"));
		//using this for regex https://stackoverflow.com/a/32228415
		// ^\d*[1-9]\d*$
		
		//Call DAO method
		FeedingScheduleDAO dao = DAOUtilities.getFeedingScheduleDao();
		try {
			dao.deleteFeedingSchedule(feedingScheduleID);
			request.getSession().setAttribute("message", "Feeding Schedule successfully deleted");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("feedingScheduleHome"); //you're probably supposed to do something else since this function is a button on this page already, but eh


		}catch(SQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			
			//change the message
			request.getSession().setAttribute("message", "Id of " + feedingScheduleID + " is throwing a SQLIntegrityConstraintViolationException which I think won't apply in a delete case but i honestly don't know so i'm leaving this in just in case");
			request.getSession().setAttribute("messageClass", "alert-danger");
			
			request.getRequestDispatcher("feedingScheduleHome.jsp").forward(request, response); 
			
		}catch (Exception e){
			e.printStackTrace();
			
			//change the message
			request.getSession().setAttribute("message", "There was a problem removing the feeding schedule at this time");
			request.getSession().setAttribute("messageClass", "alert-danger");
			
			request.getRequestDispatcher("feedingScheduleHome.jsp").forward(request, response);

		}
	}
}
