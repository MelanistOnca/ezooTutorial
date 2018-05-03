package com.examples.ezoo.servlets;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

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
@WebServlet("/addFeedingSchedule")
public class AddFeedingScheduleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L; //this is used for Serializable. for more detail, see https://stackoverflow.com/a/285809
       
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("addFeedingSchedule.jsp").forward(request, response); 
		}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get Parameters
//		long id = Long.parseLong(request.getParameter("id")); //id is defined later	
		String feeding_time = request.getParameter("feeding_time");
		
		// java.sql.Time.valueOf()
//		java.sql.Time feeding_time = java.sql.Time.valueOf( request.getParameter("feeding_time") );

		String food = request.getParameter("food");
		String notes = request.getParameter("notes");
		String recurrence = request.getParameter("recurrence");
		
		
		FeedingScheduleDAO dao = DAOUtilities.getFeedingScheduleDao();
		List<FeedingSchedule> feedingSchedules = dao.getAllFeedingSchedules();
		
		//the horror that follows was inspired by https://stackoverflow.com/a/687842 but has changed since then
//		E e = list.get(list.size() - 1);
//		E is the element type. If the list is empty, get throws an IndexOutOfBoundsException. You can find the whole API documentation http://java.sun.com/javase/6/docs/api/
//		long id = feedingSchedules.size()+1; //if the list is 5, this should return 5+1, which will be the id of the soon-to-be-added schedule
//		long id;
		//above left in for archival purposes. i really need to see if i can keep these projects on a git project

//		feedingSchedules.size()+1 = number greater than CURRENT list size, 4 currently
//		feedingSchedules.size() = number equal to size of CURRENT list, 3 currently and the index by which to find the last item

//		lists are not sorted by index, apparently
		long highest_id=0;
		for(FeedingSchedule schedule:feedingSchedules) {
			highest_id = Math.max(highest_id, schedule.getFeedingScheduleID());
		}
		
//		long id = highest_id_schedule.getFeedingScheduleID()+1; //we can't use the list size directly, in case items have been removed, which could very quickly lead to duplicate IDs. if we base off the highest current id, then we're ok, even if we've deleted IDs that had previously been higher, since they are now deleted.
		long id = highest_id+1;

//		System.out.println(id);
//		System.out.println("was id");
		//Create an FeedingSchedule object from the parameters
		FeedingSchedule feedingScheduleToSave = new FeedingSchedule(
				id, 
				feeding_time, 
				food,
				notes,
				recurrence
				);
		
		//Call DAO method
//		dao called above to define id
		try {
			dao.saveFeedingSchedule(feedingScheduleToSave);
//			dao.getAllFeedingSchedules();
			request.getSession().setAttribute("message", "Feeding Schedule successfully created");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("feedingScheduleHome");

		}
		catch(SQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			
			//change the message
			request.getSession().setAttribute("message", "Id of " + feedingScheduleToSave.getFeedingScheduleID() + " is already in use"); //this shouldn't ever happen since id is never input by user
			
			request.getSession().setAttribute("messageClass", "alert-danger");
			
			request.getRequestDispatcher("addFeedingSchedule.jsp").forward(request, response); 
			
		}
		catch (Exception e){
			e.printStackTrace();
			
			request.getSession().setAttribute("message", "There was a problem creating the feeding schedule at this time");
			request.getSession().setAttribute("messageClass", "alert-danger");
			
			request.getRequestDispatcher("addFeedingSchedule.jsp").forward(request, response);

		}
	}
}
