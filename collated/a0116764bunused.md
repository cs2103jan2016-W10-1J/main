# A0116764Bunused
###### \src\com\Main\Command.java
``` java
// unused due to change in architecture
package com.Main;

public class Command {
	Task task;
	String commandType;
	int deleteRow;
	int updateRow;
	private String updateType = "";
	private String updateDetail = "";

	public Command() {

	}
	
	public void setUpdateType(String parameter){
		updateType = parameter;
	}
	
	public  void setUpdateDetail(String parameter){
		updateDetail = parameter;
	}
	
	public void setDeleteRow(int indexToDelete){
		deleteRow = indexToDelete;
	}
	public void setUpdateRow(int indexToDelete){
		updateRow = indexToDelete;
	}
	
	public void setCommandType(String cmdType) {
		this.commandType = cmdType;
	}
	
	public void setTask(Task task){
		this.task = task;
	}
	
	public int getDeleteRow(){
		return deleteRow;
	}
	
	public int getUpdateRow(){
		return updateRow;
	}
	
	public String getUpdateType(){
		return updateType;
	}
	
	public String getUpdateDetail(){
		return updateDetail;
	}
	

	public Task getTask() {
		return task;
	}

	public String getCommandType() {
		return commandType;
	}

}
```
###### \src\com\Main\Parser.java
``` java
	public String getTaskName(String parameters) {
		String taskName = "";
		taskName = parameters.substring(0, parameters.indexOf("@") - 1);
		return taskName;
	}

	public String getLocation(String parameters) {
		String location = "";
		location = parameters.substring(parameters.indexOf("@") + 1, parameters.indexOf("on") - 1);
		return location;
	}

	public String getDate(String parameters) {
		String date = "";
		date = parameters.substring(parameters.indexOf("on") + 3, parameters.indexOf("from") - 1);
		return date;
	}

	public String getStart(String parameters) {
		String start = "";
		start = parameters.substring(parameters.indexOf("from") + 5, parameters.indexOf("~"));
		return start;
	}

	public String getEnd(String parameters) {
		String end = "";
		end = parameters.substring(parameters.indexOf("~") + 1, parameters.indexOf("#") - 1);
		return end;
	}

	public String getTag(String parameters) {
		String tag = "";
		tag = parameters.substring(parameters.indexOf("#") + 1, parameters.indexOf("-") - 1);
		return tag;
	}

	public String getNotification(String parameters) {
		String notification = "";
		notification = parameters.substring(parameters.indexOf("-") + 1);
		return notification;
	}
}	
```
###### \src\com\Main\Parser.java
``` java
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
```
###### \src\com\Test\ParserTest.java
``` java
//Test belonging to old parser class
//	@Test
/*	public void addTest(){
		Parser parser= new Parser();
		String string = "task @ loc on date from start~end #tag -notification";
		String task = parser.getTaskName(string);
		assertEquals(task, "task");
		String loc = parser.getLocation(string);
		assertEquals(loc, "loc");
		String date = parser.getDate(string);
		assertEquals(date,"date");
		String start = parser.getStart(string);
		assertEquals(start, "start");
		String end = parser.getEnd(string);
		assertEquals(end, "end");
		String tag = parser.getTag(string);
		assertEquals(tag,"tag");
		String notification = parser.getNotification(string);
		assertEquals(notification, "notification");
	}
*/
}

```
