package com.Main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;


public class Task {
	
	enum TASK_TYPE{
		EVENT, FLOAT, DEADLINE;
	}
	private String taskName = "";
	private String date = ""; //"MM dd yyyy"
	private String location = "";
	private String start = "";//"HHmm" format
	private String end = "";//"HHmm" format
	private String tag = "";
	private String notification = "";
	private GregorianCalendar startCal = new GregorianCalendar();
	private GregorianCalendar endCal = new GregorianCalendar();
	
	public GregorianCalendar getEndCal() {
		return endCal;
	}

	public void setEndCal(GregorianCalendar endCal) {
		this.endCal = endCal;
	}

	boolean isTaskDone = false;
	private int taskID;
	private TASK_TYPE taskType;
			
	public TASK_TYPE getTaskType() {
		return taskType;
	}

	public void setTaskType(TASK_TYPE taskType) {
		this.taskType = taskType;
	}
	
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
	public int getTaskID() {
		return taskID;
	}
	public void determineTaskType(){
		//Need to make it complete by guarding against other possibilities 
		if ( date == null){
			this.setTaskType(TASK_TYPE.FLOAT);
		}else 
			if ( date != null && start != null && end != null){
				this.setTaskType(TASK_TYPE.EVENT);
			}else
				if ( date != null && start == null && end != null){
					this.setTaskType(TASK_TYPE.DEADLINE);
				}else{
					this.setTaskType(null);
				} 

	}
	
	/*
 * http://stackoverflow.com/questions/4216745/java-string-to-date-conversion
 */
	public void setCalendar(){
				
		DateFormat formatter = new SimpleDateFormat("MM dd yyyy HHmm");
		Date date = new Date();
		if (this.getStart() != null){
			try {
				date = formatter.parse(this.getDate() + " " + this.getStart()); // String to Date object
			} catch (ParseException e) {
				//e.printStackTrace();
				//System.out.println("date object is not successfully parsed from its string counterpart" + e.getMessage());
			}
			startCal.setTime(date);
		}
		
		if (this.getEnd() != null){
			try {
				date = formatter.parse(getDate() + " " + getEnd()); // String to Date object
			} catch (ParseException e) {
				//e.printStackTrace();
				//System.out.println("date object is not successfully parsed from its string counterpart" + e.getMessage());
			}
			endCal.setTime(date);
		}
	}
	public GregorianCalendar getStartCal() {
		return startCal;
	}
	
	public Task(){
	}

	public String getTaskName() {
		return taskName;
	}

	public String getLocation() {
		return location;
	}
	public String getDate(){
		return date;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public String getTag() {
		return tag;
	}

	public String getNotification() {
		return notification;
	}
	
	public boolean isTaskDone(){
		return isTaskDone;
	}

	public void setTask(String taskName) {
		this.taskName = taskName;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}
	
	//@@author A0097119X
	public void setTaskAsDone(){
		this.isTaskDone = true;
	}
	
	public void setTaskAsUndone(){
		this.isTaskDone = false;
	}
	
	public static Comparator<Task> COMPARE_BY_DATE = new Comparator<Task>() {
        public int compare(Task one, Task other) {
            return one.getDate().compareTo(other.getDate());
        }
    };

    public static Comparator<Task> COMPARE_BY_TIME = new Comparator<Task>() {
        public int compare(Task one, Task other) {
            return one.getStart().compareTo(other.getStart());
        }
    };
	
	
}

/*
 * The following is the information on Date and Calendar Class.
 * The last 2 line contain references on StackOverFlow.
 * The code can be copied and pasted for your own exercise to get a hand on it.
 * import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class HelloWorld {
	public static void main(String[] args){
		System.out.println("Hello world!");
		//Scanner scan = new Scanner (System.in);
		//System.out.println("Enter some value:");
		
		GregorianCalendar startCal = new GregorianCalendar();
		DateFormat formatter = new SimpleDateFormat("MM dd yyyy HHmm");
		Date dateIns = new Date();
		String dateStr = "12 25 2016";
		String startTime = "1900";
		
		try {
			dateIns = formatter.parse(dateStr + " " + startTime);
		} catch (ParseException e) {
			
			System.out.println("catch ParseException: "+e.getMessage());
		}
		startCal.setTime(dateIns);
		System.out.println("set time success");
		
		System.out.println(startCal.getTime());
		//http://stackoverflow.com/questions/43802/how-to-convert-a-date-string-to-a-date-or-calendar-object
		//http://stackoverflow.com/questions/4216745/java-string-to-date-conversion		
		
	}
}

 */
