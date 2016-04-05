package com.Main;

import java.util.ArrayList;

public class Updater implements Commander{
	
	private int indexToUpdate;
	private String sectionToUpdate;
	private String detailToUpdate;
	private ArrayList<Task> TaskList;
	private String detailChanged;
	private Task taskToUpdate;
	
	public Updater(String[] parsedUserInput, ArrayList<Task> TaskList){
		//The element of the string array will be in the following format
		//0: indexToUpdate(TaskID), 1: sectionToUpdate, 2: detailToUpdate
		indexToUpdate = Integer.parseInt(parsedUserInput[0])-1; //-1 to make it usable to the user, as array start from 0 but task id starts from 1
		sectionToUpdate = parsedUserInput[1];
		detailToUpdate = parsedUserInput[2];
		this.TaskList = TaskList;
	}
	
	
	@Override
	public String execute() {
		
		switch(sectionToUpdate.toLowerCase()){
		case "task":
			detailChanged = TaskList.get(indexToUpdate).getTaskName();
			taskToUpdate =TaskList.get(indexToUpdate);
			taskToUpdate.setTask(detailToUpdate);
			TaskList.set(indexToUpdate, taskToUpdate);
			break;
		case "location":
			detailChanged = TaskList.get(indexToUpdate).getLocation();
			taskToUpdate =TaskList.get(indexToUpdate);
			taskToUpdate.setLocation(detailToUpdate);
			TaskList.set(indexToUpdate, taskToUpdate);
			break;
		case "date":
			detailChanged = TaskList.get(indexToUpdate).getDate();
			taskToUpdate =TaskList.get(indexToUpdate);
			taskToUpdate.setDate(detailToUpdate);
			taskToUpdate.setCalendar();
			TaskList.set(indexToUpdate, taskToUpdate);
			
			break;
		case "start":
			detailChanged = TaskList.get(indexToUpdate).getStart();
			taskToUpdate =TaskList.get(indexToUpdate);
			taskToUpdate.setStart(detailToUpdate);
			taskToUpdate.setCalendar();
			TaskList.set(indexToUpdate, taskToUpdate);
			break;
		case "end":
			detailChanged = TaskList.get(indexToUpdate).getEnd();
			taskToUpdate =TaskList.get(indexToUpdate);
			taskToUpdate.setEnd(detailToUpdate);
			taskToUpdate.setCalendar();
			TaskList.set(indexToUpdate, taskToUpdate);
			break;
		case "tag":
			detailChanged = TaskList.get(indexToUpdate).getTag();
			taskToUpdate =TaskList.get(indexToUpdate);
			taskToUpdate.setTag(detailToUpdate);
			TaskList.set(indexToUpdate, taskToUpdate);
			break;
		case "notification":
			detailChanged = TaskList.get(indexToUpdate).getNotification();
			taskToUpdate =TaskList.get(indexToUpdate);
			taskToUpdate.setNotification(detailToUpdate);
			TaskList.set(indexToUpdate, taskToUpdate);
			break;
		case "taskid":
			return "TaskID should not be edited, please update other details.";
		default:
			return "Keyword for the type of detail is invalid.";
		}
		Processor.setLastCommanderInst(this);		
		return "Task " + (indexToUpdate+1) +"'s " + sectionToUpdate.toLowerCase() + " was updated successfully from " + detailChanged + " to " + detailToUpdate;
	}

	@Override
	public String undo() {
		String[] inputForUndo = new String[3];
		int sameIndex = this.indexToUpdate + 1;
		//current this.indexToUpdate is the index of array, +1 to change it back to user's form.
		inputForUndo[0] = Integer.toString(sameIndex);
		inputForUndo[1] = this.sectionToUpdate;
		inputForUndo[2] = this.detailChanged;
		Updater undoUpdate = new Updater(inputForUndo, this.TaskList);
		return "The following is re-updated as undo: "+ System.lineSeparator()+undoUpdate.execute();
	}

}
