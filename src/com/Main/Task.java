package com.Main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

//@@author A0100111R
public class Task {

	private boolean debug = false;

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
	boolean isTaskDone = false;

	private GregorianCalendar startCal = new GregorianCalendar();
	private GregorianCalendar endCal = new GregorianCalendar();

	private int taskID;
	private TASK_TYPE taskType;


	public GregorianCalendar getEndCal() {
		return endCal;
	}

	public void setEndCal(GregorianCalendar endCal) {
		this.endCal = endCal;
	}

	public void setStartCal(GregorianCalendar startCal) {
		this.startCal = startCal;
	}

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

		if ( date.equals(" ") || date.equals("") || date == null){
			this.setTaskType(TASK_TYPE.FLOAT);
			return;
		}else {
			if ( !start.equals(" ") && !end.equals(" ") ){ // have date start-time end-time
				this.setTaskType(TASK_TYPE.EVENT);
				return;
			}else
				if ( start.equals(" ") && !end.equals(" ")){ //only no start time
					this.setTaskType(TASK_TYPE.DEADLINE);
					return;
				}else{
					this.setTaskType(TASK_TYPE.FLOAT); // rest of the cases
					return;
				} 
		}

	}

	/*
	 * http://stackoverflow.com/questions/4216745/java-string-to-date-conversion
	 */
	public void setCalendar(){
		if( debug)
			System. out.println( "see what inside the dateString: "+"<"+this.getDate()+ ">");

		if ( date.equals(" ") || date.equals("") || date == null){
			if( debug){
				System.out.println( "No date, is the rest of Code executed? ");
			}
			return;
		}

		DateFormat formatter = new SimpleDateFormat( "MM dd yyyy HHmm");
		Date date = new Date();
		if ( !this.getStart().equals(" ")){
			try {
				date = formatter.parse( this.getDate() + " " + this.getStart()); // String to Date object
			} catch (ParseException e) {
				System. out.println( "Start date object is not successfully parsed from its string counterpart:" + e.getMessage());
			}
			startCal.setTime( date);
		}

		if ( !this.getEnd().equals(" ")){
			try {
				date = formatter.parse( this.getDate() + " " + this.getEnd()); // String to Date object
			} catch (ParseException e) {
				System. out.println( "End date object is not successfully parsed from its string counterpart" + e.getMessage());
			}
			endCal.setTime( date);
		}

		/*
		 *        //re-format the date string according to actual Gregorian Date
       DateFormat dateFormatter = new SimpleDateFormat("MM dd yyyy ");
       Date dateInst = new Date();
       try {
            dateInst = dateFormatter.parse(this.getDate());
       } catch (ParseException e1) {
            System.out.println("in Task.java, fail to re-format the date string according to actual Gregorian Date");
       }
        int month = dateInst.getMonth()+1;
        int day = dateInst.getDate();
        int year = dateInst.getYear()+1900;
       String monthStr = Integer.toString(month);
       String dayStr = Integer.toString(day);
       String yearStr = Integer.toString(year);
       date = monthStr +" "+ dayStr + " "+ yearStr;
		 */      
	}



	public void updateNonStringField(){
		this.setCalendar();
		this.determineTaskType();
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
			if(other.getStart().trim().length()>0 && one.getStart().trim().length()>0){
				return one.getStart().compareTo(other.getStart());
			}
			else if(other.getStart().trim().length()<1 && one.getStart().trim().length()>0){
				return one.getEnd().compareTo(other.getStart());
			}
			else if(other.getStart().trim().length()>0 && one.getStart().trim().length()<1){
				return one.getStart().compareTo(other.getEnd());
			}
			else{
				return one.getEnd().compareTo(other.getEnd());
			}
		}
	};


}
