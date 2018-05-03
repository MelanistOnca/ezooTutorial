package com.examples.ezoo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Class used to retrieve DAO Implementations. Serves as a factory.
 * 
 * @author anon
 *
 */
public class DAOUtilities {

	private static final String CONNECTION_USERNAME = "postgres";
	private static final String CONNECTION_PASSWORD = "testpassword";
	private static final String URL = "jdbc:postgresql://localhost:5432/eZoo";
	
	private static AnimalDaoImpl animalDaoImpl;
	private static FeedingScheduleDaoImpl feedingScheduleDaoImpl;
	private static AnimalScheduleJoinDaoImpl animalScheduleJoinDaoImpl;
	private static Connection connection;

	public static synchronized AnimalDAO getAnimalDao() { //could this be generalize to getModelDao()? I think i would need a ModelDAO class for both AnimalDAO and FeedingScheduleDAO to inherit from to make that work, since the returned object would need to be of that type?

		if (animalDaoImpl == null) {
			animalDaoImpl = new AnimalDaoImpl();
		}
		return animalDaoImpl;
	}
	public static synchronized FeedingScheduleDAO getFeedingScheduleDao() { //could this be generalize to getModelDao()? I think i would need a ModelDAO class for both AnimalDAO and FeedingScheduleDAO to inherit from to make that work, since the returned object would need to be of that type?

		if (feedingScheduleDaoImpl == null) {
			feedingScheduleDaoImpl = new FeedingScheduleDaoImpl();
		}
		return feedingScheduleDaoImpl;
	}
	public static synchronized AnimalScheduleJoinDAO getAnimalScheduleJoinDao() { //could this be generalize to getModelDao()? I think i would need a ModelDAO class for both AnimalDAO and FeedingScheduleDAO to inherit from to make that work, since the returned object would need to be of that type?

		if (animalScheduleJoinDaoImpl == null) {
			animalScheduleJoinDaoImpl = new AnimalScheduleJoinDaoImpl();
		}
		return animalScheduleJoinDaoImpl;
	}

	static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		//If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			System.out.println("getting new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}

}
