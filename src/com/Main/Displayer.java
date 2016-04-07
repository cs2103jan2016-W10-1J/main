package com.Main;
//@@author A0100111R
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.Main.Task.TASK_TYPE;

public class Displayer implements Commander{
	
	private ArrayList<Task> TaskList;
	private static String displayParameter = "today";
	
	private static ArrayList<Task> eventList;
	private static ArrayList<Task> floatList;
	private static ArrayList<Task> deadlineList;
	private static ArrayList<Task> doneTaskList;
	private static ArrayList<Task> undefinedList;

	/*
	 * First constructor is for situations when displayRequirement requires changes
	 * This constructor is also tailored for user command "Display displayParameter"
	 */
	public Displayer(String displayRequirement, ArrayList<Task> TaskList) {
		ArrayList<Task> eventList = new ArrayList<Task>();
		ArrayList<Task> floatList = new ArrayList<Task>();
		ArrayList<Task> deadlineList = new ArrayList<Task>();
		ArrayList<Task> doneTaskList  = new ArrayList<Task>();
		ArrayList<Task> undefinedList  = new ArrayList<Task>();
				
		displayParameter = displayRequirement;	
		this.TaskList = new ArrayList<Task>(TaskList);//Copy the orignal TaskList.
		this.getEventList(TaskList);
		this.getFloatList(TaskList);
		this.getDeadlineList(TaskList);
	}
	
	/*
	 * Second constructor is purely for the purpose of updating the GUI display.
	 * It is only used internally by other classes.
	 */
	public Displayer(ArrayList<Task> TaskList) {
		this.TaskList = new ArrayList<Task>(TaskList);
		this.getEventList(TaskList);
		this.getFloatList(TaskList);
		this.getDeadlineList(TaskList);
	}


	public String execute() {
		//display today/tomorrow/week/all/done/undone
		switch (displayParameter){
		case "done":
			getDoneTasks();
			break;
		case "undone":
			getUndoneTasks();
			break;
		case "today":
			getTodayTasks();
			break;
		case "tomorrow":
			getTmrTasks();
			break;
		//case "week":
		//	getThisWeekTasks();
		//	break;
		case "all":
			//Constructor has done the job.
			break;
			
		default:
			return "Unrecognized display parameter";
		}
		updateThreeLists();	
		Processor.setLastCommanderInst(this);
		return "Please refer to the right-hand side panel for display";
	}

	private void getTmrTasks(){
		Date date = new Date();		
		Calendar calendarInst = Calendar.getInstance(); 
		calendarInst.setTime(date); 
		calendarInst.add(Calendar.DATE, 1);
		date = calendarInst.getTime();
		
		SearcherByDate findTodayEvent = new SearcherByDate(date, eventList);
		eventList = findTodayEvent.executeforDisplayOnStartTime();
		
		SearcherByDate findTodayDeadline = new SearcherByDate(date, deadlineList);
		deadlineList = findTodayDeadline.executeforDisplayOnStartTime();
	}


	private void getTodayTasks() {
		Date date = new Date();
		SearcherByDate findTodayEvent = new SearcherByDate(date, eventList);
		eventList = findTodayEvent.executeforDisplayOnStartTime();
		
		SearcherByDate findTodayDeadline = new SearcherByDate(date, deadlineList);
		deadlineList = findTodayDeadline.executeforDisplayOnStartTime();
	}

/*
 * Complete the sort.
 */
	private void updateThreeLists() {
		Sort sortInst = new Sort(eventList);
		eventList = sortInst.sortThis(); 
		Processor.setEventList(eventList);
		Processor.setFloatList(floatList);
		Processor.setDeadlineList(deadlineList);
		Processor.setDoneTaskList(doneTaskList);
		Processor.setUndefinedList(undefinedList);
	}


	private void getDoneTasks() {
		for (int i = 0; i < eventList.size(); i++){
			Task taskInst = eventList.get(i);
			if (!taskInst.isTaskDone){
				eventList.remove(i);
			}
		}
		for (int i = 0; i < floatList.size(); i++){
			Task taskInst = floatList.get(i);
			if (!taskInst.isTaskDone){
				floatList.remove(i);
			}
		}
		for (int i = 0; i < deadlineList.size(); i++){
			Task taskInst = deadlineList.get(i);
			if (!taskInst.isTaskDone){
				deadlineList.remove(i);
			}
		}
	}
	
	private void getUndoneTasks() {
		for (int i = 0; i < eventList.size(); i++){
			Task taskInst = eventList.get(i);
			if (taskInst.isTaskDone){
				eventList.remove(i);
			}
		}
		for (int i = 0; i < floatList.size(); i++){
			Task taskInst = floatList.get(i);
			if (taskInst.isTaskDone){
				floatList.remove(i);
			}
		}
		for (int i = 0; i < deadlineList.size(); i++){
			Task taskInst = deadlineList.get(i);
			if (taskInst.isTaskDone){
				deadlineList.remove(i);
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


	

	@Override
	public String undo() {
		String feedback = "Your last action is searching, which cannot be undone.";
		return feedback;
	}
	

}
