package com.examples.ezoo.model;

import com.examples.ezoo.model.Animal;
import com.examples.ezoo.model.FeedingSchedule;

public class AnimalScheduleJoin {
	
	private long animal_id = 0L;
	private long schedule_id = 0L;
	private String animal_name="";
	private String feeding_time="";
	
	public AnimalScheduleJoin() {};
	
	public AnimalScheduleJoin(Animal animal, FeedingSchedule schedule) {
		super(); 
		this.animal_id = animal.getAnimalID();
		this.animal_name = animal.getName();
		this.schedule_id = schedule.getFeedingScheduleID();
		this.feeding_time = schedule.getFeedingTime();
	};
	
	public AnimalScheduleJoin(long animal_id, long schedule_id) {
		super();
		this.animal_id = animal_id;
		this.schedule_id = schedule_id;
	};
	
	public long getScheduleID() {
		return schedule_id;
	};
	
	public void setScheduleID(long schedule_id) {
		this.schedule_id = schedule_id;
	};
	
	public long getAnimalID() {
		return animal_id;
	};
	
	public void setAnimalID(long animal_id) {
		this.animal_id = animal_id;
	};
	public String getAnimalName() {
		return animal_name;
	}
	
	public void setAnimalName(String animal_name) {
		this.animal_name = animal_name;
	}
	
	public String getScheduleFeedingTime() {
		return feeding_time;
	}
	
	public void setScheduleFeedingTime(String feeding_time) {
		this.feeding_time = feeding_time;
	}
	
	public String toString() {
		return "Animal with name" + animal_name + " and id " + animal_id  + " animal_id " + " has feeding schedule starting at " + feeding_time + " and id " + schedule_id + ".";
	};
	

}
