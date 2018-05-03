package com.examples.ezoo.dao;

import java.util.List;

import com.examples.ezoo.model.FeedingSchedule;
import com.examples.ezoo.model.Animal;
import com.examples.ezoo.model.AnimalScheduleJoin;

/**
* Main interface used to execute CRUD methods on join table(s)
* @author Patrick
*
*/


public interface AnimalScheduleJoinDAO {
	/**
	 * Used to retrieve a list of all joins 
	 * @return
	 */
	List<AnimalScheduleJoin> getAllJoins();
	
	
	/**
	 * Used to persist the join to the datastore
	 * @param animal, feedingSchedule
	 */
	void createJoin(Animal animal, FeedingSchedule feedingSchedule) throws Exception;
	
	/**
	 * Used to remove the join from the datastore
	 * @param animal, feedingSchedule
	 */
	void deleteJoin(FeedingSchedule feedingSchedule) throws Exception;
	void unassignJoin(long animal_id, long schedule_id) throws Exception;
}
