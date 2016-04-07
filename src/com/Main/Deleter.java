package com.Main;

import java.util.ArrayList;

//@@author A0100111R
public class Deleter implements Commander{
	private int taskID;
	private int taskIndex;
	private ArrayList<Task> TaskList;
	private Task taskDeleted = null;
	
	public Deleter(String[] parsedUserInput, ArrayList<Task> TaskList){
		//The first element in the array is the taskID to be deleted
		taskID = Integer.parseInt(parsedUserInput[0]);
		this.TaskList = TaskList;
	}
	@Override
	public String execute() {
		taskIndex = SearchTaskByID(taskID);
		if(taskIndex < 0){
			return "The taskID entered is invalid, please re-enter.";
		}else{
			taskDeleted = TaskList.remove(taskIndex);
			Processor.setLastCommanderInst(this);
			ConverterToString taskDeletedConversion = new ConverterToString(taskDeleted);
			String taskDeletedString = taskDeletedConversion.convert();
			String feedback = "Task deleted successfully:"+System.lineSeparator()+taskDeletedString;
			return feedback;
		}
	}	
/*		
		try{
		//assert(taskID >=0);
		//taskDeleted = TaskList.get(taskID-1).getTaskName() + " on " + TaskList.get(taskID-1).getDate();
			
		} catch (Exception e){
			//e.printStackTrace();
			return "Task deleted unsuccessfully";
		}
*/		

	
	private int SearchTaskByID(int DesiredTaskID) {
		for (int i = 0; i < TaskList.size(); i++){
			if(DesiredTaskID == TaskList.get(i).getTaskID()){
				return i;
			}
		}
		return -1; //-1 for the case of invalid TaskId which can't be found the match.
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
