package com.examples.ezoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.examples.ezoo.model.FeedingSchedule;
import com.examples.ezoo.model.Animal;
import com.examples.ezoo.model.AnimalScheduleJoin;

public class AnimalScheduleJoinDaoImpl implements AnimalScheduleJoinDAO{
	
	@Override
	public List<AnimalScheduleJoin> getAllJoins() {
		List<AnimalScheduleJoin> joinList = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;
		
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.createStatement();
			String sql = "SELECT * FROM animal_join_feedingschedules";

			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				AnimalScheduleJoin asj = new AnimalScheduleJoin();
				
				asj.setAnimalID( rs.getLong("animal_id"));
				asj.setAnimalName( rs.getString("animal_name") );
				asj.setScheduleID( rs.getLong("feeding_schedule_id")); //i really need to do a naming convention review
				asj.setScheduleFeedingTime( rs.getString("feeding_time")); //see naming convention issue
				
				
				
				joinList.add(asj);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
//		System.out.println(joinList);
//		System.out.println("was joinList");
		return joinList;
	}
	@Override
	public void createJoin(Animal animal, FeedingSchedule feedingSchedule) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		int success = 0;
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO animal_join_feedingschedules VALUES (?,?,?,CAST (? AS time))";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1, animal.getAnimalID());
			stmt.setLong(2, feedingSchedule.getFeedingScheduleID());
			stmt.setString(3,animal.getName());
			stmt.setString(4, feedingSchedule.getFeedingTime());
			
			success = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (success == 0) {
			// then update didn't occur, throw an exception
			throw new Exception("Insert join failed. Animal: " +animal + " and Feeding Schedule " + feedingSchedule);
		}

		
	}
	
	@Override
	public void deleteJoin(FeedingSchedule feedingSchedule) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		int success = 0;
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "Delete From animal_join_feedingschedules where schedule_id = CAST (? AS bigint)";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1, feedingSchedule.getFeedingScheduleID());
			
			success = stmt.executeUpdate();
		} catch (SQLException e) { //everything from here to the finally {} closing bracket is copied from above
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (success == 0) {
			// then update didn't occur, throw an exception
			throw new Exception("Delete FeedingSchedule failed. Feeding Schedule " + feedingSchedule);
		}
	}
	
	@Override
	public void unassignJoin(long animal_id, long schedule_id) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		int success = 0;
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "Delete From animal_join_feedingschedules where animal_id = CAST (? AS bigint) AND feeding_schedule_id = CAST (? AS bigint)";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setLong(1, animal_id);
			stmt.setLong(2, schedule_id);
			
			success = stmt.executeUpdate();
		} catch (SQLException e) { //everything from here to the finally {} closing bracket is copied from above
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (success == 0) {
			// then update didn't occur, throw an exception
			throw new Exception("Delete join failed. Animal id: " + animal_id + ". Feeding Schedule id: " + schedule_id + ".");
		}
	}
}
