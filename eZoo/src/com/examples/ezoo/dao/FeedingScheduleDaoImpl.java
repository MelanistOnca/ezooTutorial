package com.examples.ezoo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import com.examples.ezoo.model.Animal;
import com.examples.ezoo.model.FeedingSchedule;

public class FeedingScheduleDaoImpl implements FeedingScheduleDAO{
	
	@Override
	public List<FeedingSchedule> getAllFeedingSchedules() {
		List<FeedingSchedule> feedingSchedules = new ArrayList<>();
		Connection connection = null;
		Statement stmt = null;

		try {
			connection = DAOUtilities.getConnection();

			stmt = connection.createStatement();

			String sql = "SELECT * FROM FEEDING_SCHEDULES";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
//				System.out.println(rs);
				FeedingSchedule fs = new FeedingSchedule();

				fs.setFeedingScheduleID(rs.getLong("schedule_ID"));
				fs.setFeedingTime(rs.getTime("feeding_time"));

				fs.setFood(rs.getString("food"));
				fs.setNotes(rs.getString("notes"));
				fs.setRecurrence(rs.getString("recurrence"));
				
				feedingSchedules.add(fs);
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

		return feedingSchedules;
	}
	
	@Override
	public void saveFeedingSchedule(FeedingSchedule feedingSchedule) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		int success = 0;
//		System.out.println(feedingSchedule.getFeedingTime());
//		System.out.println("was feeding schedule");
//		System.out.println(feedingSchedule.getFeedingScheduleID());
//		System.out.println("was schedule ID");
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO feeding_schedules VALUES (?,CAST (? AS time),?,?, CAST (? AS interval) )"; //with id entered

			// Setup PreparedStatement
			stmt = connection.prepareStatement(sql);

			// Add parameters from feedingSchedule into PreparedStatement
			stmt.setLong(1, feedingSchedule.getFeedingScheduleID());
			stmt.setString(2, feedingSchedule.getFeedingTime());
			stmt.setString(3, feedingSchedule.getFood());
			stmt.setString(4, feedingSchedule.getNotes());
			stmt.setString(5, feedingSchedule.getRecurrence());
			
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
			throw new Exception("Insert feedingSchedule failed: " + feedingSchedule);
		}

	}
	
	@Override
	public void updateFeedingSchedule(FeedingSchedule feedingSchedule) throws Exception{
		Connection connection = null;
		PreparedStatement stmt = null;
		int success = 0;
//		System.out.println(feedingSchedule.getFeedingTime());
//		System.out.println("was feeding schedule");
//		System.out.println(feedingSchedule.getFeedingScheduleID());
//		System.out.println("was schedule ID");
		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE feeding_schedules SET"
					+ " feeding_time = CAST (? AS time),"
					+ " food = ?,"
					+ " notes = ?,"
					+ " recurrence = CAST (? AS interval) "
					+ "WHERE schedule_id = ?"; 

			// Setup PreparedStatement
			stmt = connection.prepareStatement(sql);
			// Add parameters from feedingSchedule into PreparedStatement
			stmt.setString(1, feedingSchedule.getFeedingTime());
			stmt.setString(2, feedingSchedule.getFood());
			stmt.setString(3, feedingSchedule.getNotes());
			stmt.setString(4, feedingSchedule.getRecurrence());
			
			stmt.setLong(5, feedingSchedule.getFeedingScheduleID());
			
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
			throw new Exception("Update feedingSchedule failed: " + feedingSchedule);
		}
	}

	@Override
	public void deleteFeedingSchedule(long feedingScheduleID) throws Exception {
	//remove FS from DB.
	Connection connection = null;
	PreparedStatement stmt = null;
	int success = 0;
	
		try {
			connection = DAOUtilities.getConnection();
			String sql = "Delete From FEEDING_SCHEDULES where schedule_id = CAST (? AS bigint)";
	
			// Setup PreparedStatement
			stmt = connection.prepareStatement(sql);
			
			// Add parameters from feedingSchedule into PreparedStatement
			stmt.setLong(1, feedingScheduleID);
			
			success = stmt.executeUpdate(); //NOTE: im just copying this from saveFeedingSchedule() above, not 100% that its the correct thing
			
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
			throw new Exception("Delete feedingSchedule failed for feedingSchedule with ID " + feedingScheduleID);
		}
	}
	
	@Override
	public FeedingSchedule getScheduleByID(long schedule_id) {
		Connection connection = null;
		PreparedStatement stmt = null;
		FeedingSchedule fs = new FeedingSchedule();
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM feeding_schedules WHERE schedule_id = ? ";
			
			stmt = connection.prepareStatement(sql);
			stmt.setLong(1, schedule_id);
			
			ResultSet rs = stmt.executeQuery();

			
//			System.out.println(rs);
//			System.out.println("*****************");
			
			while (rs.next()) { 
				
				fs.setFeedingScheduleID(rs.getLong("schedule_ID"));

				fs.setFeedingTime(rs.getTime("feeding_time"));

				fs.setFood(rs.getString("food"));
				fs.setNotes(rs.getString("notes"));
				fs.setRecurrence(rs.getString("recurrence"));
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
		
		return fs;
	}

}