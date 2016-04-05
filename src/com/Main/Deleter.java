package com.Main;

import java.util.ArrayList;

public class Deleter implements Commander{
	private int indexToDelete;
	private ArrayList<Task> TaskList;
	private Task taskDeleted = null;
	
	public Deleter(String[] parsedUserInput, ArrayList<Task> TaskList){
		//The first element in the array is the index(TaskID) to be deleted
		indexToDelete = Integer.parseInt(parsedUserInput[0]);
		this.TaskList = TaskList;
	}
	@Override
	public String execute() {
		try{
		//assert(indexToDelete >=0);
		//taskDeleted = TaskList.get(indexToDelete-1).getTaskName() + " on " + TaskList.get(indexToDelete-1).getDate();
		taskDeleted = TaskList.remove(indexToDelete-1);
		Processor.setLastCommanderInst(this);
		return "Task deleted successfully";
		} catch (Exception e){
			//e.printStackTrace();
			return "Task deleted unsuccessfully";
		}
		
		//return "Task " + indexToDelete + ": " + taskDeleted + " deleted successfully";
	}
	@Override
	public String undo() {
		ConverterToString taskConverter = new ConverterToString(taskDeleted);
		String feedback = taskConverter.convert();
		TaskList.add(taskDeleted);
		Processor.setLastCommanderInst(null);//Undoing delete is designed for no chance of redoing.
		return "The following task you deleted is added in again: "+System.lineSeparator()+feedback;
	}

}
