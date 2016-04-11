package com.Main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * This class will take in a date string in the form of "MM dd yyyy" e.g. 12 31 2016
 * and it will search from the TaskList for Tasks that has start-time on that date.
 * Then it will get an arrayList of Task objects fulfilling the requirement.
 * Before return, it will call the ConverterToString Class with the arrayList with the parameter.
 * The ConverterToString Object will return a string to the processor for display.
 * 
 */

public class SearcherByDate implements Commander {
	private ArrayList<Task> TaskList;
	private GregorianCalendar desiredDay = new GregorianCalendar();
	private ArrayList<Task> TaskListWanted = new ArrayList<Task>();
	
	private boolean debug = false;
	
	public SearcherByDate(String[] parsedUserInput, ArrayList<Task> TaskList) {
		DateFormat formatter = new SimpleDateFormat("MM dd yyyy");
		Date date = new Date();
		try {
			date = formatter.parse(parsedUserInput[0]); // String to Date object
		} catch (ParseException e) {
			System.out.println("date object is not successfully parsed from its string counterpart" + e.getMessage());
		}
		desiredDay.setTime(date);
		
		this.TaskList = TaskList;
	}
	
	public SearcherByDate(Date date, ArrayList<Task> TaskList) {
		
		desiredDay.setTime(date);
		
		this.TaskList = TaskList;
	}
	
	@Override
	public String execute() {
		GregorianCalendar dayToCompare;
		Task taskInst = null;
		for (int i = 0; i < TaskList.size(); i++){
			taskInst = TaskList.get(i);
			if ( taskInst.getStartCal() != null ){
				dayToCompare = taskInst.getStartCal();
			}else
				if( taskInst.getEndCal() != null){
					dayToCompare = taskInst.getEndCal();
				}else{
					if (debug){
						String refer = Integer.toString(i);
						System.out.println("SearcherByDate: both StartCal and EndCal are empty"+"reference num: "+refer);
						
					}
					continue;
				}
						
			int DesiredYear = desiredDay.get(GregorianCalendar.YEAR);
			int TaskYear = dayToCompare.get(GregorianCalendar.YEAR);
			int DesiredDay = desiredDay.get(GregorianCalendar.DAY_OF_YEAR);
			int TaskDay = dayToCompare.get(GregorianCalendar.DAY_OF_YEAR);
			
			boolean sameDay = ((DesiredYear==TaskYear)&&(DesiredDay==TaskDay));
			if (sameDay){
				TaskListWanted.add(TaskList.get(i));
			}
		}
		if(TaskListWanted.size() == 0 ){
			return "No relevant tasks found.";
		}
		ConverterToString output = new ConverterToString(TaskListWanted);
		//Processor.setLastCommanderInst(this);
		return output.convert();
	}
	public ArrayList<Task> executeforDisplayOnStartTime() {
		GregorianCalendar dayToCompare;
		for (int i = 0; i < TaskList.size(); i++){
			Task taskIns = TaskList.get(i);
			if ( taskIns.getStartCal() != null ){
				dayToCompare = taskIns.getStartCal();
			}else
			{
				continue;
			}
			
			
			int DesiredYear = desiredDay.get(GregorianCalendar.YEAR);
			int TaskYear = dayToCompare.get(GregorianCalendar.YEAR);
			int DesiredDay = desiredDay.get(GregorianCalendar.DAY_OF_YEAR);
			int TaskDay = dayToCompare.get(GregorianCalendar.DAY_OF_YEAR);
			
			boolean sameDay = ((DesiredYear==TaskYear)&&(DesiredDay==TaskDay));
			if (sameDay){
				TaskListWanted.add(taskIns);
			}
		}
		return TaskListWanted;
	}
	public ArrayList<Task> executeforDisplayOnEndTime() {
		GregorianCalendar dayToCompare;
		for (int i = 0; i < TaskList.size(); i++){
			Task taskIn = TaskList.get(i);
			if ( taskIn.getStartCal() != null ){
				dayToCompare = taskIn.getStartCal();
			}else
			{
				continue;
			}
			
			int DesiredYear = desiredDay.get(GregorianCalendar.YEAR);
			int TaskYear = dayToCompare.get(GregorianCalendar.YEAR);
			int DesiredDay = desiredDay.get(GregorianCalendar.DAY_OF_YEAR);
			int TaskDay = dayToCompare.get(GregorianCalendar.DAY_OF_YEAR);
			
			boolean sameDay = ((DesiredYear==TaskYear)&&(DesiredDay==TaskDay));
			if (sameDay){
				TaskListWanted.add(taskIn);
			}
		}
		return TaskListWanted;
	}

	@Override
	public String undo() {
		//String feedback = "Your last action is searching, which cannot be undone.";
		//return feedback;
		return "";
	}

}
/*
 * Calendar cal1 = Calendar.getInstance();
Calendar cal2 = Calendar.getInstance();
cal1.setTime(date1);
cal2.setTime(date2);
boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
*/