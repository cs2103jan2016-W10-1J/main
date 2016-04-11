//@@author A0100111R
package com.Main;
import java.util.ArrayList;

/*
 * This class can take in either a single Task object or an arrayList of them.
 * Then an object of ConverterToString will convert the Task object(s) into a formatted string.
 * Such string will be returned to the caller.
 */
public class ConverterToString {
	private Task taskIns = null;
	private ArrayList<Task> taskList = null;
	//Constructor
	public ConverterToString() {
	}
	
	public ConverterToString(Task TaskToConvert){
		if(TaskToConvert == null){
			System.out.println("Empty task");
		}else{
			taskIns = TaskToConvert;
		}	
	}
	
	public ConverterToString(ArrayList<Task> SomeTasksToConvert){
		if(SomeTasksToConvert == null){
			System.out.println("Empty taskList");
		}else{
			taskList = SomeTasksToConvert;
		}
		
	}
	public String convert(){
		if (taskIns != null){
			return convertSingleTask();
		}
		else {
			return convertSomeTasks();
		}
	}
	private String convertSingleTask(){
		StringBuilder taskToShow = new StringBuilder();
		taskToShow.append("Task ID: " + Integer.toString(taskIns.getTaskID()) + System.lineSeparator());
		taskToShow.append(String.format(DISPLAY_TASK_TITLE_MESSAGE, taskIns.getTaskName())+System.lineSeparator());
		taskToShow.append(String.format(DISPLAY_TASK_DATE_MESSAGE, taskIns.getDate())+System.lineSeparator());
		taskToShow.append(String.format(DISPLAY_TASK_LOCATION_MESSAGE, taskIns.getLocation())+System.lineSeparator());
		taskToShow.append(String.format(DISPLAY_TASK_START_MESSAGE, taskIns.getStart())+System.lineSeparator());
		taskToShow.append(String.format(DISPLAY_TASK_END_MESSAGE, taskIns.getEnd())+System.lineSeparator());
		taskToShow.append(String.format(DISPLAY_TASK_TAG_MESSAGE, taskIns.getTag())+System.lineSeparator());
		taskToShow.append(String.format(DISPLAY_TASK_NOTIFICATION_MESSAGE, taskIns.getNotification())+System.lineSeparator());
		taskToShow.append(System.lineSeparator());
		return taskToShow.toString(); 
	}
	private String convertSomeTasks(){
		StringBuilder tasksToShow = new StringBuilder();
		for(int i = 0; i < taskList.size(); i++){
			taskIns = taskList.get(i);
			String singleTask = convertSingleTask();
			tasksToShow.append(singleTask+System.lineSeparator());
		}
		return tasksToShow.toString();		
	}
	
	
	private static final String DISPLAY_TASK_TITLE_MESSAGE = "Title: %1$s";
	private static final String DISPLAY_TASK_DATE_MESSAGE = "Date: %1$s";
	private static final String DISPLAY_TASK_LOCATION_MESSAGE = "Location: %1$s";
	private static final String DISPLAY_TASK_START_MESSAGE = "Start: %1$s";
	private static final String DISPLAY_TASK_END_MESSAGE = "End: %1$s";
	private static final String DISPLAY_TASK_TAG_MESSAGE = "Tag: %1$s";
	private static final String DISPLAY_TASK_NOTIFICATION_MESSAGE = "Notification: %1$s";
}
