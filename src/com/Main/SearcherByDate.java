//@@author A0100111R
package com.Main;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * This class will take in a date string in the form of "MM dd yyyy" e.g. 12 31 2016
 * and it will search from the originalTaskList for Tasks that has start-time on that date.
 * Then it will get an arrayList of Task objects fulfilling the requirement.
 * Before return, it will call the ConverterToString Class with the arrayList with the parameter.
 * The ConverterToString Object will return a string to the processor for display.
 * 
 */

public class SearcherByDate implements Commander {
	private ArrayList<Task> originalTaskList = null;
	private GregorianCalendar desiredDay = new GregorianCalendar();
	private ArrayList<Task> taskListWanted = new ArrayList<Task>();

	private boolean isDebugging = false;

	public SearcherByDate(String[] parsedUserInput, ArrayList<Task> TaskList) {
		DateFormat formatter = new SimpleDateFormat("MM dd yyyy");
		Date date = new Date();
		try {
			date = formatter.parse(parsedUserInput[0]); // String to Date object
		} catch (ParseException e) {
			System.out.println("date object is not successfully parsed from its string counterpart" + e.getMessage());
		}
		desiredDay.setTime(date);
		this.originalTaskList = TaskList;
	}

	public SearcherByDate(Date date, ArrayList<Task> TaskList) {
		desiredDay.setTime(date);
		this.originalTaskList = TaskList;
	}

	@Override
	/*
	 * search the list of all tasks for the specified date for external use of
	 * user
	 */
	public String execute() {
		searchAndCompare();
		if (taskListWanted.size() == 0) {
			return "No relevant tasks found.";
		}
		ConverterToString output = new ConverterToString(taskListWanted);
		return output.convert();
	}

	/**
	 * Search for qualified tasks and check whether it's on the specified day
	 */
	private void searchAndCompare() {
		// Search for qualified task with a date of Gregorian Type
		GregorianCalendar dayToCompare = null;
		Task taskInst = null;
		for (int i = 0; i < originalTaskList.size(); i++) {
			taskInst = originalTaskList.get(i);
			if (taskInst.getStartCal() != null) {
				dayToCompare = taskInst.getStartCal();
			} else if (taskInst.getEndCal() != null) {
				dayToCompare = taskInst.getEndCal();
			} else {
				if (isDebugging) {
					String referenceNumber = Integer.toString(i);
					System.out.println(
							"SearcherByDate: both StartCal and EndCal are empty" + "reference num: " + referenceNumber);
				}
				continue;
			}
			// Compare the two tasks to check whether they are on the same day
			int DesiredYear = desiredDay.get(GregorianCalendar.YEAR);
			int TaskYear = dayToCompare.get(GregorianCalendar.YEAR);
			int DesiredDay = desiredDay.get(GregorianCalendar.DAY_OF_YEAR);
			int TaskDay = dayToCompare.get(GregorianCalendar.DAY_OF_YEAR);

			boolean sameDay = ((DesiredYear == TaskYear) && (DesiredDay == TaskDay));
			if (sameDay) {
				taskListWanted.add(originalTaskList.get(i));
			}
		}
	}

	/*
	 * search the list of all tasks for the specified Starting date for internal
	 * use of other class: Displayer class
	 */
	public ArrayList<Task> executeforDisplayOnStartTime() {
		// Search for qualified task with a starting date of Gregorian Type
		GregorianCalendar dayToCompare;
		for (int i = 0; i < originalTaskList.size(); i++) {
			Task taskIns = originalTaskList.get(i);
			if (taskIns.getStartCal() != null) {
				dayToCompare = taskIns.getStartCal();
			} else {
				continue;
			}
			// Compare the two tasks to check whether they are on the same day
			int DesiredYear = desiredDay.get(GregorianCalendar.YEAR);
			int TaskYear = dayToCompare.get(GregorianCalendar.YEAR);
			int DesiredDay = desiredDay.get(GregorianCalendar.DAY_OF_YEAR);
			int TaskDay = dayToCompare.get(GregorianCalendar.DAY_OF_YEAR);

			boolean sameDay = ((DesiredYear == TaskYear) && (DesiredDay == TaskDay));
			if (sameDay) {
				taskListWanted.add(taskIns);
			}
		}
		return taskListWanted;
	}

	/*
	 * search the list of all tasks for the specified ending date for internal
	 * use of other class: Displayer class
	 */
	public ArrayList<Task> executeforDisplayOnEndTime() {
		// Search for qualified task with a ending date of Gregorian Type
		GregorianCalendar dayToCompare;
		for (int i = 0; i < originalTaskList.size(); i++) {
			Task taskIn = originalTaskList.get(i);
			if (taskIn.getStartCal() != null) {
				dayToCompare = taskIn.getEndCal();
			} else {
				continue;
			}
			// Compare the two tasks to check whether they are on the same day
			int DesiredYear = desiredDay.get(GregorianCalendar.YEAR);
			int TaskYear = dayToCompare.get(GregorianCalendar.YEAR);
			int DesiredDay = desiredDay.get(GregorianCalendar.DAY_OF_YEAR);
			int TaskDay = dayToCompare.get(GregorianCalendar.DAY_OF_YEAR);

			boolean sameDay = ((DesiredYear == TaskYear) && (DesiredDay == TaskDay));
			if (sameDay) {
				taskListWanted.add(taskIn);
			}
		}
		return taskListWanted;
	}

	@Override
	public String undo() {
		return "";
	}
}
