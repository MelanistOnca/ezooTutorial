package com.examples.ezoo.servlets;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.examples.ezoo.dao.AnimalDAO;
import com.examples.ezoo.dao.DAOUtilities;
//import com.examples.ezoo.dao.FeedingScheduleDAO;
//import com.examples.ezoo.dao.FeedingScheduleDAO;
import com.examples.ezoo.model.Animal;
//import com.examples.ezoo.model.FeedingSchedule;
//import com.examples.ezoo.model.FeedingSchedule;

/**
 * Servlet implementation class AddAnimalServlet
 */
@WebServlet("/addAnimal")
public class AddAnimalServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("addAnimal.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get Parameters
		
//		long id = Long.parseLong(request.getParameter("id")); //we define this later since the ID is no longer entered in the web form
		
		String name = request.getParameter("name");

		String kingdom = request.getParameter("kingdom");
		String phylum = request.getParameter("phylum");
		String clazz = request.getParameter("clazz");
		String order = request.getParameter("order");
		String family = request.getParameter("family");
		String genus = request.getParameter("genus");
		String species = request.getParameter("species");
		String type = request.getParameter("type");
		String healthStatus = request.getParameter("healthStatus");
		
		//Since request parameters are ALWAYS Strings we will convert them to Double
		double height = Double.parseDouble(request.getParameter("height"));
		double weight = Double.parseDouble(request.getParameter("weight"));
		
		
		
		//the horror that follows is inspired by https://stackoverflow.com/a/687842
		AnimalDAO dao = DAOUtilities.getAnimalDao();
		List<Animal> animals = dao.getAllAnimals(); //getting the whole list each time you add one feels like it would be inefficient for large values. probably there should be a thing in the DAO that returns the size of the list, or i should have a property/variable that increments when a new thing is added. figure it out later.
//		long id = animals.size()+1; //animal delete isn't implemented yet, but probably will be by the time this project is lvl 300+
		
		long highest_id=0;
		for(Animal animal:animals) {
			highest_id = Math.max(highest_id, animal.getAnimalID() );
		}
		
//		long id = highest_id_schedule.getAnimalID()+1; //we can't use the list size directly, in case items have been removed, which could very quickly lead to duplicate IDs. if we base off the highest current id, then we're ok, even if we've deleted IDs that had previously been higher, since they are now deleted.
		long id = highest_id+1;
		
		//Create an Animal object from the parameters
		Animal animalToSave = new Animal(
				id, 
				name, 
				kingdom,
				phylum,
				clazz,
				order,
				family,
				genus,
				species,
				height,
				weight,
				type,
				healthStatus
				);
		
		//Call DAO method
		// dao has already been defined above for determining the id
		try {
			dao.saveAnimal(animalToSave);
			request.getSession().setAttribute("message", "Animal successfully created");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("animalCare");


		}catch(SQLIntegrityConstraintViolationException e){
			e.printStackTrace();
			
			//change the message
			request.getSession().setAttribute("message", "Id of " + animalToSave.getAnimalID() + " is already in use"); //with the new way animal id is determined, this error should never happen?
			request.getSession().setAttribute("messageClass", "alert-danger");
			
			request.getRequestDispatcher("addAnimal.jsp").forward(request, response);
			
		}catch (Exception e){
			e.printStackTrace();
			
			//change the message
			request.getSession().setAttribute("message", "There was a problem creating the animal at this time");
			request.getSession().setAttribute("messageClass", "alert-danger");
			
			request.getRequestDispatcher("addAnimal.jsp").forward(request, response);

		}
	}

}
