//@@author A0100111R
package com.Main;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.Main.Task.TASK_TYPE;

public class Displayer implements Commander{
	
	private boolean debug = false;
	
	private ArrayList<Task> taskList = null;
	private static String displayParameter = "today";
	
	private static ArrayList<Task> eventList = null;
	private static ArrayList<Task> floatList = null;
	private static ArrayList<Task> deadlineList = null;
	private static ArrayList<Task> doneTaskList = null;
	private static ArrayList<Task> unDoneTaskList = null;
	
	/*
	 * First constructor is for situations when displayRequirement requires changes
	 * This constructor is also tailored for user command "Display displayParameter"
	 */
	public Displayer(String displayRequirement, ArrayList<Task> originalTaskList) {
		eventList = new ArrayList<Task>();
		floatList = new ArrayList<Task>();
		deadlineList = new ArrayList<Task>();
		doneTaskList  = new ArrayList<Task>();
		unDoneTaskList  = new ArrayList<Task>();
				
		displayParameter = displayRequirement;	
		this.taskList = new ArrayList<Task>(originalTaskList);//Copy the original taskList.
		
		this.getDoneTasks(originalTaskList);
		this.getEventList(unDoneTaskList);
		this.getFloatList(unDoneTaskList);
		this.getDeadlineList(unDoneTaskList);
	}
	
	/*
	 * Second constructor is purely for the purpose of updating the GUI display.
	 * It is only used internally by other classes.
	 */
	public Displayer(ArrayList<Task> originalTaskList) {
		eventList = new ArrayList<Task>();
		floatList = new ArrayList<Task>();
		deadlineList = new ArrayList<Task>();
		doneTaskList  = new ArrayList<Task>();
		unDoneTaskList  = new ArrayList<Task>();
		
		this.taskList = new ArrayList<Task>(originalTaskList);
		
		this.getDoneTasks(taskList);
		this.getEventList(unDoneTaskList);
		this.getFloatList(unDoneTaskList);
		this.getDeadlineList(unDoneTaskList);
	
		if(debug){
			int x = Processor.getFloatList().size();
			System.out.println(Integer.toString(x));
		}
	}


	public String execute() {
		//display today/tomorrow/all/
		switch (displayParameter){
		case "today":
			getTodayTasks();
			break;
		case "tomorrow":
			getTmrTasks();
			break;
		case "all":
			//Constructor has done the job.
			break;
			
		default:
			return "Unrecognized display parameter";
		}
		updateThreeLists();			
		return "Please refer to the right-hand side panel for display";
	}


	private void getTmrTasks(){
		Date date = new Date();		
		Calendar calendarInst = Calendar.getInstance(); 
		calendarInst.setTime(date); 
		calendarInst.add(Calendar.DATE, 1);
		date = calendarInst.getTime();
		
		SearcherByDate findTmrEvent = new SearcherByDate(date, eventList);
		eventList = findTmrEvent.executeforDisplayOnStartTime();

		
		SearcherByDate findTmrDeadline = new SearcherByDate(date, deadlineList);
		deadlineList = findTmrDeadline.executeforDisplayOnEndTime();
	}


	private void getTodayTasks() {
		Date date = new Date();
		SearcherByDate findTodayEvent = new SearcherByDate(date, eventList);
		eventList = findTodayEvent.executeforDisplayOnStartTime();
				
		SearcherByDate findTodayDeadline = new SearcherByDate(date, deadlineList);
		deadlineList = findTodayDeadline.executeforDisplayOnEndTime();
		
	}
	
	private void sortTaskLists(){
		if(eventList.size() != 0){
			Sorter sortEventTaskList = new Sorter (eventList);
			eventList = sortEventTaskList.sortThis();
		}
		if(deadlineList.size() != 0){
			Sorter sortDeadLineTaskList = new Sorter (deadlineList);
			deadlineList = sortDeadLineTaskList.sortThis();;
		}
		if(floatList.size() != 0){
			Sorter sortFloatList = new Sorter (floatList);
			floatList = sortFloatList.sortThis();
		}
		if(doneTaskList.size() != 0){
			Sorter sortDoneTaskList = new Sorter (doneTaskList);
			doneTaskList = sortDoneTaskList.sortThis();
		}
		
	}

	private void updateThreeLists() {
		if(debug){
		int x = Processor.getFloatList().size();
		System.out.println(Integer.toString(x));
		}
		sortTaskLists();
		Processor.setEventList(eventList);
		Processor.setFloatList(floatList);
		Processor.setDeadlineList(deadlineList);
		Processor.setDoneTaskList(doneTaskList);
	}

	
	private void getDoneTasks(ArrayList<Task> TaskList) {
		
		for (int i = 0; i < TaskList.size(); i++ ){
			Task taskInst = TaskList.get(i);
			if(taskInst.isTaskDone){
				doneTaskList.add(taskInst);
			}
			else{
				unDoneTaskList.add(taskInst);
			}
		}

	}
	

	public void getEventList(ArrayList<Task> TaskList){
		Task taskInst;
		for (int i = 0; i < TaskList.size(); i++){
			taskInst = TaskList.get(i);
			if ( taskInst.getTaskType() == TASK_TYPE.EVENT ){
				eventList.add(taskInst);
			}
		} 
	}
	public void getFloatList(ArrayList<Task> TaskList){
		Task taskInst;
		for (int i = 0; i < TaskList.size(); i++){
			taskInst = TaskList.get(i);
			if ( taskInst.getTaskType() == TASK_TYPE.FLOAT ){
				floatList.add(taskInst);
			}
		} 
	}
	public void getDeadlineList(ArrayList<Task> TaskList){
		Task taskInst;
		for (int i = 0; i < TaskList.size(); i++){
			taskInst = TaskList.get(i);
			if ( taskInst.getTaskType() == TASK_TYPE.DEADLINE ){
				deadlineList.add(taskInst);
			}
		} 
	}
	public static String getDisplayParameter() {
		return displayParameter;
	}

	@Override
	public String undo() {
		return "";
	}
	

}
