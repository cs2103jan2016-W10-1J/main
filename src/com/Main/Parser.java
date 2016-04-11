//@@author A0116764B
package com.Main;

import java.util.ArrayList;
 
public class Parser {

	public Parser() {

	}
	private static String DATE_INVALID = "invalid date";
	private static int START_OF_STRING = 0;
	private static int INVALID = -1; // to check for invalid tokens
	private static int NUMBER_OF_DATE_PARAMETERS = 3; // MM dd yyyy
	private static int MAX_MONTH = 12;
	private static int MAX_DATE = 31;
	private static int MIN_MONTH = 1;
	private static int MIN_DATE = 1;
	private static String EMPTY_STRING = "";
	private static String SINGLE_SPACE = " ";
	private static boolean BOOL_DATE_VALID = true;
	private static boolean BOOL_DATE_INVALID = false;
	
	public Commander parse(String input, ArrayList<Task> TaskList) {
		
		String command = getCommandFromInput(input);
		input = input.substring(command.length() + 1);
		switch (command) {
		// add <String taskName> @ <String location> on <Date date> from <String
		// start> ~ <String end> # <String tag> - <String notification>
		// taskName is a compulsory parameter, all other parameters are optional
		case "add":
			String[] tokens = { "@", "on", "from", "~", "#", "-" };
			int[] tokenLoc = new int[8];
			tokenLoc[0] = START_OF_STRING;

			for (int i = 0; i < tokens.length; i++) {
				tokenLoc[i + 1] = getTokenLoc(input, tokens[i]);
			}
			tokenLoc[7] = input.length(); // end of the string

			String[] addParameters = new String[7]; // 0 = task name, 1 = loc, 2 = date, 3 = start, 
													//4 = end, 5 = tag, 6 = notification

			for (int i = 0; i < addParameters.length; i++) {
				int startIndex = tokenLoc[i];
				if (startIndex == INVALID) { // token not used
					continue;
				}
				int j = i + 1;
				while (tokenLoc[j] == INVALID) {
					j++;
				}
				int endIndex = tokenLoc[j];
				addParameters[i] = input.substring(startIndex, endIndex);
			}

			for (int i = 1; i < 7; i++) {
				if (addParameters[i] != null) {
					addParameters[i] = addParameters[i].substring(tokens[i - 1].length() + 1);
				} else {
					addParameters[i] = SINGLE_SPACE;
				}
			}
			if (addParameters[2] != null && addParameters[2] != SINGLE_SPACE) {
				addParameters[2] = addParameters[2].trim();
			}
			
			// check for valid date format
			if (addParameters[2] != SINGLE_SPACE){
			String[] checkDate = addParameters[2].split(SINGLE_SPACE);
			
			boolean valid = BOOL_DATE_VALID;
			int numberOfParameters = 0;
			for (String dateParameter : checkDate){
				try{
					Integer.parseInt(dateParameter);
				}catch(Exception e){
					valid = BOOL_DATE_INVALID;
				}
				numberOfParameters ++;
			}
			if (valid && numberOfParameters == NUMBER_OF_DATE_PARAMETERS){
				int month = Integer.parseInt(checkDate[0]);
				int day = Integer.parseInt(checkDate[1]);
				if (month < MIN_MONTH || month > MAX_MONTH){
					valid = BOOL_DATE_INVALID;
				}
				else if (day < MIN_DATE || day > MAX_DATE){
					valid = BOOL_DATE_INVALID;
				}
			}
			else{
				addParameters[2] = DATE_INVALID;
			}
			if (!valid){
				addParameters[2] = DATE_INVALID;
			}
			}
			return new Adder(addParameters, TaskList);


		// delete <int taskNumber>
		case "delete":
			String deleteParameters[] = new String[1];
			deleteParameters[0] = getDeleteRow(input);
			return new Deleter(deleteParameters, TaskList);

		// update <int taskNumber> <String detailType> <String newDetail>
		case "update":
			String updateParameters[] = new String[3];
			updateParameters[0] = getUpdateRow(input);
			input = removeFirstWord(input);
			updateParameters[1] = getNextWord(input).trim();
			input = removeFirstWord(input);
			updateParameters[2] = input.trim();
			if (updateParameters[2].equals(EMPTY_STRING)){ 
				updateParameters[2] = SINGLE_SPACE;
			}
			
			switch(updateParameters[1]){ 
				
			case "date":
			
				// check for valid date format
				String[] checkUpdateDate = updateParameters[2].split(SINGLE_SPACE);
				boolean validDate = true;
				int numberOfParametersForDate = 0;
				for (String dateParameter : checkUpdateDate){
					try{
						Integer.parseInt(dateParameter);
					}catch(Exception e){
						validDate = false;
					}
					numberOfParametersForDate ++;
				}
				if (validDate && numberOfParametersForDate == NUMBER_OF_DATE_PARAMETERS){
					int month = Integer.parseInt(checkUpdateDate[0]);
					int day = Integer.parseInt(checkUpdateDate[1]);
					if (month < MIN_MONTH || month > MAX_MONTH){
						validDate = false;
					}
					else if (day < MIN_DATE || day > MAX_DATE){
						validDate = false;
					}
				}
				else{
					updateParameters[2] = DATE_INVALID;
				}
				if (!validDate){
					updateParameters[2] = DATE_INVALID;
				}
			default:
				break;
			}
			return new Updater(updateParameters, TaskList);

		// search <String keyword>
		// search d <Date date>
		case "search":
			String[] searchType = input.split(SINGLE_SPACE);
			switch (searchType[0]) {
			case "d": // by date
				String searchDateParameters[] = new String[1];
				input = removeFirstWord(input);
				searchDateParameters[0] = input;
				return new SearcherByDate(searchDateParameters, TaskList);
			
			  case "f": //by free slot 
				  String searchFreeParameters[] = new String[1]; 
				  input = removeFirstWord(input);
				  searchFreeParameters[0] = input;
				  return new SearcherForFreeTimeSlot(searchFreeParameters, TaskList);

			default: // by keyword
				String[] searchParameters = input.split(SINGLE_SPACE);
				return new SearcherByKeyword(searchParameters, TaskList);
			}
		
		// done <task number>
		case "done":
			String done[] = new String[3];
			done[0] = removeFirstWord(input);
			done[1] = "Done";
			done[2] = "'Done'";
			return new Updater(done, TaskList);
		
		// undone <task number>
		case "undone":
			String undone[] = new String[3];
			undone[0] = removeFirstWord(input);
			undone[1] = "Undone";
			undone[2] = "'Undone'";
			return new Updater(undone, TaskList);

		// display today/tomorrow/week/all/done/undone
			
		case "display": String displayParameter;
		displayParameter = input; 
		return new Displayer(displayParameter, TaskList);
		}
		return null;
	}

	public int getTokenLoc(String input, String token) {
		int loc = INVALID;
		if (input.contains(token)) {
			loc = input.indexOf(token);
		}
		return loc;
	}

	public String getNextWord(String string) {
		String result = string.substring(0, string.indexOf(SINGLE_SPACE));
		return result;
	}

	public String removeFirstWord(String string) {
		String newString = string.substring(string.indexOf(SINGLE_SPACE) + 1);
		return newString;
	}

	public String getCommandFromInput(String string) {
		String command = string.substring(0, string.indexOf(SINGLE_SPACE));
		return command;
	}

	public String getUpdateRow(String parameters) {
		String updateRow;
		updateRow = parameters.substring(0, parameters.indexOf(SINGLE_SPACE));
		return updateRow;
	}

	public String getDeleteRow(String parameters) {
		String deleteRow = parameters;
		return deleteRow;
	}

	public String getDoRow(String parameters) {
		String doRow = parameters;
		return doRow;
	}

	// @@author A0116764B-unused
	// for old parser and old parser testing
	// unused after change in architecture
	public String getTaskName(String parameters) {
		String taskName = EMPTY_STRING;
		taskName = parameters.substring(0, parameters.indexOf("@") - 1);
		return taskName;
	}

	public String getLocation(String parameters) {
		String location = EMPTY_STRING;
		location = parameters.substring(parameters.indexOf("@") + 1, parameters.indexOf("on") - 1);
		return location;
	}

	public String getDate(String parameters) {
		String date = EMPTY_STRING;
		date = parameters.substring(parameters.indexOf("on") + 3, parameters.indexOf("from") - 1);
		return date;
	}

	public String getStart(String parameters) {
		String start = EMPTY_STRING;
		start = parameters.substring(parameters.indexOf("from") + 5, parameters.indexOf("~"));
		return start;
	}

	public String getEnd(String parameters) {
		String end = EMPTY_STRING;
		end = parameters.substring(parameters.indexOf("~") + 1, parameters.indexOf("#") - 1);
		return end;
	}

	public String getTag(String parameters) {
		String tag = EMPTY_STRING;
		tag = parameters.substring(parameters.indexOf("#") + 1, parameters.indexOf("-") - 1);
		return tag;
	}

	public String getNotification(String parameters) {
		String notification = EMPTY_STRING;
		notification = parameters.substring(parameters.indexOf("-") + 1);
		return notification;
	}
}	
//@@author A0116764B-unused
// Old parser code, before overhaul in architecture
/*
	private Command command;
	private String taskName = "";
	private String location = "";
	private String date = "";
	private String start = "";
	private String end = "";
	private String tag = "";
	private String notification = "";

	public Parser(String cmd) {
		command = new Command();
		String commandType = cmd.substring(0, cmd.indexOf(" "));
		String parameters = cmd.substring(cmd.indexOf(" ") + 1);
		command.setCommandType(commandType);

		switch (commandType) {

		// add task @location on date from 1230~1300 #tag -notification
		case "add":
			createTaskWithParameters(parameters);
			break;

		// delete taskNumber
		case "delete":
			processDelete(parameters);
			break;

		// update taskNumber detailType newValue
		case "update":
			processUpdate(parameters);
			break;

		default:
			System.out.println("Invalid command input");
		}
	}

	public Command getCommand() {
		return command;
	}

	private void processUpdate(String parameters) {
		int updateRow = getUpdateRow(parameters);
		command.setUpdateRow(updateRow);
		parameters = parameters.substring(parameters.indexOf(" ") + 1);
		System.out.println(parameters.substring(0, parameters.indexOf(" ")));
		String updateType = parameters.substring(0, parameters.indexOf(" "));
		command.setUpdateType(updateType);
		parameters = parameters.substring(parameters.indexOf(" "));
		String updateDetail = parameters.substring(parameters.indexOf(" ") + 1);
		command.setUpdateDetail(updateDetail);
	}

	private void processDelete(String parameters) {
		int deleteRow = getDeleteRow(parameters);
		command.setDeleteRow(deleteRow);
	}

	public void

	createTaskWithParameters(String parameters) {
		Task task = new Task();
		taskName = getTaskName(parameters);
		task.setTask(taskName);
		location = getLocation(parameters);
		task.setLocation(location);
		date = getDate(parameters);
		task.setDate(date);
		start = getStart(parameters);
		task.setStart(start);
		end = getEnd(parameters);
		task.setEnd(end);
		tag = getTag(parameters);
		task.setTag(tag);
		notification = getNotification(parameters);
		task.setNotification(notification);
		command.setTask(task);
	}

	public String getTaskName() {
		return taskName;
	}

	public String getLocation() {
		return location;
	}

	public String getDate() {
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

	public int getDeleteRow() {
		return command.getDeleteRow();
	}

	public int getUpdateRow() {
		return command.getUpdateRow();
	}

	public String getUpdateType() {
		return command.getUpdateType();
	}

	public String getUpdateDetail() {
		return command.getUpdateDetail();
	}
}
*/