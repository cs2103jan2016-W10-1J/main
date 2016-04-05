package com.Main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * This class takes in the original TaskList and a desired time slot as input parameter for its constructor
 * the desired time slot should be in the form of a string array.
 * The 1st element of the string array contain the start time 
 * The 2st element of the string array contain the end time 
 * E.g.: [1000][1400] (looking for a free time slot from 10am to 2pm)
 * The return value is a string, indicating the dates within 7 days that can take in the desired time slot.
 * for V0.3, this function will only return the earliest day, NOT considering later available day.
 */

//@@author A0097119X
public class SearcherForFreeTimeSlot implements Commander {
		private String currentDate;
		private int currentTime;
		private int startTime;
		private int endTime;
		private ArrayList<Task> TaskList;
		DateFormat formatCurrentDate = new SimpleDateFormat("MM dd yyyy");
		DateFormat formatCurrentTime = new SimpleDateFormat("HHmm");
		Date current;
		
	public SearcherForFreeTimeSlot(String[] parsedUserInput, ArrayList<Task> TaskList) { 
		String userInputToSplit;
		userInputToSplit = parsedUserInput[0];
		parsedUserInput = userInputToSplit.split(" ");
		startTime = Integer.parseInt(parsedUserInput[0]);
		endTime = Integer.parseInt(parsedUserInput[1]);
		
		this.TaskList = TaskList;
	}

	@Override
	public String execute() {
		current = new Date();
		LocalDate today = LocalDate.now();
		DayOfWeek dow = today.getDayOfWeek();
		String dateToCompare;
		String dayName;
		String datesFound = new String();
		int startTimeToCompare;
		int endTimeToCompare;
		boolean foundTimeSlot = false;
		boolean foundConflict = false;
		boolean isDateToday = true;
		
		currentDate = formatCurrentDate.format(current); //nearest free day 
		currentTime = Integer.parseInt(formatCurrentTime.format(new Date())); //real life time in integer format
		System.out.println("Current Time is : " + currentTime);
		System.out.println("Current Day is : ");
		
		Processor.setLastCommanderInst(this);
		
		for(int j=0; j<8; j++){
			foundConflict = false;
			
		for(int i=0; i<TaskList.size(); i++){
			dateToCompare = TaskList.get(i).getDate();
			
			try {
				if(!TaskList.get(i).getStart().trim().equals("")||!TaskList.get(i).getEnd().trim().equals("")){
				
				startTimeToCompare = Integer.parseInt(TaskList.get(i).getStart().trim());
				endTimeToCompare = Integer.parseInt(TaskList.get(i).getEnd().trim());
				
				System.out.println("Current Time is : " + currentTime);
				System.out.println("Start Time to compare with : " + startTimeToCompare);
				
				if(dateToCompare.equals(currentDate)){
					if(startTime>=startTimeToCompare && startTime<endTimeToCompare || endTime>startTimeToCompare && endTime<=endTimeToCompare){
						foundConflict = true;
						break;
					}
				}
}
			} catch (NumberFormatException e) {
			}
		}
		
		if(isDateToday){
			if(currentTime > startTime){ //To handle first case where there is no task for today but it may already be too late to schedule the task in
				foundConflict = true;
				System.out.println("Conflict found");
			}
			isDateToday = false;
		}
		
		if(foundConflict){
			//Increase date, set time as zero
			SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(sdf.parse(currentDate));
				c.add(Calendar.DATE, 1);
				currentDate = sdf.format(c.getTime());
				System.out.println("Current Date is : " + currentDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			currentTime = 0;
		}
		
		else{
			foundTimeSlot = true;
			
			
			dayName = dow.plus(j).getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
			datesFound = datesFound + currentDate + " (" + dayName + ")" + "\n";
			
			
			//Increase date, set time as zero
			SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(sdf.parse(currentDate));
				c.add(Calendar.DATE, 1);
				currentDate = sdf.format(c.getTime());
				System.out.println("Current Date is : " + currentDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			currentTime = 0;
		}
		}
		if(foundTimeSlot){
		return startTime + " to " + endTime + " is available on : \n" + datesFound;
		}
		
		else{
			return "Timeslot not available in the upcoming week";
		}
	}

	@Override
	public String undo() {
		String feedback = "Your last action is searching, which cannot be undone.";
		return feedback;
	}

}
