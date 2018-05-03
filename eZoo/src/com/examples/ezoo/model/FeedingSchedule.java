package com.examples.ezoo.model;

public class FeedingSchedule {
	private long schedule_ID = 0L;
	
	private String feeding_time = "";
//	private java.sql.Time feeding_time;
	
	private String food="";
	private String notes="";
	private String recurrence="";
	
	public FeedingSchedule(){}

//	public FeedingSchedule(long schedule_ID, java.sql.Time feeding_time, String food, String notes, String recurrence) {
	public FeedingSchedule(long schedule_ID, String feeding_time, String food, String notes, String recurrence) {
		super();
//		System.out.println(feeding_time);
//		System.out.println("was feeding time $$$$$$$$$$$$$");
		this.schedule_ID = schedule_ID;
//		this.feeding_time = java.sql.Time.valueOf(feeding_time);
		this.feeding_time = feeding_time;
		this.food = food;
		this.notes = notes;
		this.recurrence = recurrence;
	}

	public long getFeedingScheduleID() {
		return schedule_ID;
	}

	public void setFeedingScheduleID(long schedule_ID) {
		this.schedule_ID = schedule_ID;
	}

	public String getFeedingTime() {
//		System.out.println(feeding_time);
//		System.out.println("was feeding time in getFeedingTime");
		return feeding_time;
	}
//	public java.sql.Time getFeedingTime() {
//		return feeding_time;
//	}
//	java.sql.Time feeding_time = java.sql.Time.valueOf( request.getParameter("feeding_time") );
//	public java.sql.Time getFeedingTimeSQL() {
//		return java.sql.Time.valueOf(feeding_time) ;
//	}

	public void setFeedingTime(String feeding_time) {
//		System.out.println(feeding_time);
//		System.out.println("was feeding time in setFeedingTime");
		this.feeding_time = feeding_time;
	}
	public void setFeedingTime(java.sql.Time feeding_time) {
		this.feeding_time = feeding_time.toString();
	}
//	public void setFeedingTime(String feeding_time) {
//		this.feeding_time = java.sql.Time.valueOf(feeding_time);
//	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}


	public String getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}

	@Override
	public String toString() {
		return "FeedingSchedule [schedule_ID=" + schedule_ID + ", feeding_time=" + feeding_time + ", food=" + food + ", notes=" + notes +", recurrence="
				+ recurrence + "]";
	}
}
