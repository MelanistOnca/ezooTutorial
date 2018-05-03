package com.examples.ezoo.dao;

import java.util.List;

import com.examples.ezoo.model.FeedingSchedule;


/**
* Main interface used to execute CRUD methods on Animal class
* @author Patrick
*
*/
public interface FeedingScheduleDAO {
	
	/**
	 * Used to retrieve a list of all FeedingSchedules 
	 * @return
	 */
	List<FeedingSchedule> getAllFeedingSchedules();
	
	/**
	 * Used to persist the feedingSchedule to the datastore
	 * @param feedingScheduleToSave
	 */
	void saveFeedingSchedule(FeedingSchedule feedingScheduleToSave) throws Exception;
	
	void updateFeedingSchedule(FeedingSchedule feedingSchedule) throws Exception;
	/**
	 * Used to remove the feedingSchedule from the datastore
	 * @param feedingScheduleID
	 */
//	void deleteFeedingSchedule(FeedingSchedule feedingScheduleToDelete) throws Exception;
	void deleteFeedingSchedule(long feedingScheduleID) throws Exception;
	FeedingSchedule getScheduleByID (long feedingScheduleID) throws Exception;
	
	

}