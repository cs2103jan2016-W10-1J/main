package com.Main;

import java.util.ArrayList;
import java.util.logging.Level;

public class Adder implements Commander{
	private Task newTask = new Task();
	ArrayList<Task> TaskList;
	

	public Adder(String[] parsedUserInput, ArrayList<Task> TaskList) {
		//The element of the string array will be in 
		//0.taskName, 1.location 2.date 3.start 4.end 5.tag 6.notification
		int taskListSize = TaskList.size();
		this.TaskList = TaskList;
		try {
			newTask.setTask(parsedUserInput[0]);
			newTask.setLocation(parsedUserInput[1]);
			newTask.setDate(parsedUserInput[2]);
			newTask.setStart(parsedUserInput[3]);
			newTask.setEnd(parsedUserInput[4]);
			newTask.setTag(parsedUserInput[5]);
			newTask.setNotification(parsedUserInput[6]);
			newTask.setCalendar();
			newTask.setTaskID(taskListSize+1);
			
		} catch (NullPointerException e) {
			
			//e.printStackTrace();
			System.out.println("The Task is null. " + e.toString());
		}
	}

	@Override
	public String execute() {
		if (newTask.getTaskName() == "" || newTask.getTaskName() == null || newTask.getTaskName() == " ")
			return "Task not added successfully";
		try {
			TaskList.add(newTask);
			Processor.setLastCommanderInst(this);
			return "Task added successfully" ;
		} catch (Exception e) {
			e.printStackTrace();
			return "Task not added successfully" ;
			//ProcessorLogger.log(Level.WARNING, "task not added successfully", e);
		}	
		
	}

	@Override
	public String undo() {
		ConverterToString taskConverter = new ConverterToString(newTask);
		String feedback = taskConverter.convert();
		
		if(TaskList.remove(newTask)){
			Processor.setLastCommanderInst(null);//Undoing add is designed for no chance of redoing.
			return "The following task is removed:"+System.lineSeparator() + feedback;		
		}
		else
			return "The undo to remove last task added is unsuccessful.";
	}

}
