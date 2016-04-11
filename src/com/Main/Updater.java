package com.Main;

import java.util.ArrayList;

//@@author A0097119X
public class Updater implements Commander{
	private int taskIDinput;
	private int indexToUpdate;
	private String sectionToUpdate;
	private String detailToUpdate;
	private ArrayList<Task> TaskList;
	private String oldDetail;
	private Task oldTask;
	private Task updatedTask;
	
	private boolean debug = true;
	
	public Updater(String[] parsedUserInput, ArrayList<Task> TaskList){
		//The element of the string array will be in the following format
		//0: (TaskID), 1: sectionToUpdate, 2: detailToUpdate
		taskIDinput = Integer.parseInt(parsedUserInput[0]); 
		sectionToUpdate = parsedUserInput[1];
		detailToUpdate = parsedUserInput[2];
		this.TaskList = TaskList;		
	}

	@Override
	public String execute() {
		indexToUpdate = SearchTaskByID(taskIDinput);
		if (indexToUpdate < 0){
			return "The taskID entered is invalid, please re-enter.";
		}else{
			
			if (detailToUpdate.equals("invalid date")){
				return "Task NOT updated successfully due to invalid date!";
			}
			
			switch(sectionToUpdate.toLowerCase()){
			case "task":
				oldDetail = TaskList.get(indexToUpdate).getTaskName();
				oldTask =TaskList.get(indexToUpdate);
				updatedTask = TaskList.get(indexToUpdate);
				updatedTask.setTask(detailToUpdate);
				TaskList.set(indexToUpdate, updatedTask);
				break;
			case "location":
				oldDetail = TaskList.get(indexToUpdate).getLocation();
				oldTask =TaskList.get(indexToUpdate);
				updatedTask =TaskList.get(indexToUpdate);
				updatedTask.setLocation(detailToUpdate);
				TaskList.set(indexToUpdate, updatedTask);
				break;
			case "date":
				oldDetail = TaskList.get(indexToUpdate).getDate();
				oldTask =TaskList.get(indexToUpdate);
				updatedTask =TaskList.get(indexToUpdate);
				
				if(debug) System.out.println("Updater:execute, updated date is: "+ "<"+detailToUpdate.toString()+">");
				
				updatedTask.setDate(detailToUpdate);
				updatedTask.setCalendar();
				TaskList.set(indexToUpdate, updatedTask);
				break;
			case "start":
				oldDetail = TaskList.get(indexToUpdate).getStart();
				oldTask =TaskList.get(indexToUpdate);
				updatedTask =TaskList.get(indexToUpdate);
				
				if(debug) System.out.println("Updater:execute, updated start is: "+ "<"+detailToUpdate.toString()+">");
				
				updatedTask.setStart(detailToUpdate);
				updatedTask.setCalendar();
				TaskList.set(indexToUpdate, updatedTask);
				break;
			case "end":
				oldDetail = TaskList.get(indexToUpdate).getEnd();
				oldTask =TaskList.get(indexToUpdate);
				updatedTask =TaskList.get(indexToUpdate);
				
				if(debug) System.out.println("Updater:execute, updated end is: "+"<"+ detailToUpdate.toString()+">");
				
				updatedTask.setEnd(detailToUpdate);
				updatedTask.setCalendar();
				TaskList.set(indexToUpdate, updatedTask);
				break;
			case "tag":
				oldDetail = TaskList.get(indexToUpdate).getTag();
				oldTask =TaskList.get(indexToUpdate);
				updatedTask =TaskList.get(indexToUpdate);
				updatedTask.setTag(detailToUpdate);
				TaskList.set(indexToUpdate, updatedTask);
				break;
			case "notification":
				oldDetail = TaskList.get(indexToUpdate).getNotification();
				oldTask =TaskList.get(indexToUpdate);
				updatedTask =TaskList.get(indexToUpdate);
				updatedTask.setNotification(detailToUpdate);
				TaskList.set(indexToUpdate, updatedTask);
				break;
			case "taskid":
				return "TaskID should not be edited, please update other details.";
			case "done":
				if(TaskList.get(indexToUpdate).isTaskDone()){
					oldDetail = "'Done'";
				}
				else{
					oldDetail = "'Undone'";
				}
				oldTask =TaskList.get(indexToUpdate);
				updatedTask = TaskList.get(indexToUpdate);
				updatedTask.setTaskAsDone();
				TaskList.set(indexToUpdate, updatedTask);
				break;
				
			case "undone":
				if(TaskList.get(indexToUpdate).isTaskDone()){
					oldDetail = "'Done'";
				}
				else{
					oldDetail = "'Undone'";
				}
				oldTask =TaskList.get(indexToUpdate);
				updatedTask = TaskList.get(indexToUpdate);
				updatedTask.setTaskAsUndone();;
				TaskList.set(indexToUpdate, updatedTask);
				break;
			default:
				return "Keyword for the type of detail is invalid.";
			}
			//@@author A0100111R			
			
			updatedTask.determineTaskType();
			Processor.setLastCommanderInst(this);
			
			ConverterToString taskUpdatedConversion = new ConverterToString(updatedTask);
			String feedBackBody = taskUpdatedConversion.convert();
			String feedBackTitle = "Task "+Integer.toString(taskIDinput)+"'s "+sectionToUpdate+" was updated successfully FROM "+oldDetail+" to "+":"+detailToUpdate+":"+System.lineSeparator();

			Displayer updateGUI = new Displayer(TaskList);
			updateGUI.execute();
			
			String taskType = updatedTask.getTaskType().toString();
			return feedBackTitle+System.lineSeparator()+"The updated task now:"+System.lineSeparator()+feedBackBody+System.lineSeparator()+taskType;
			
		}
	}

	//@@author A0100111R
	@Override
	public String undo() {
		String[] inputForUndo = new String[3];
		int sameIndex = this.indexToUpdate + 1;
		//current this.indexToUpdate is the index of array, +1 to change it back to user's form.
		inputForUndo[0] = Integer.toString(sameIndex);
		inputForUndo[1] = this.sectionToUpdate;
		inputForUndo[2] = this.oldDetail;
		Updater undoUpdate = new Updater(inputForUndo, this.TaskList);
		
		Displayer updateGUI = new Displayer(TaskList);
		updateGUI.execute();
		
		return "The following is re-updated as undo: "+ System.lineSeparator()+undoUpdate.execute();
	}

	
	
	private int SearchTaskByID(int DesiredTaskID) {
		for (int i = 0; i < TaskList.size(); i++){
			if(DesiredTaskID == TaskList.get(i).getTaskID()){
				return i;
			}
		}
		return -1; //-1 for the case of invalid TaskId which can't be found the match.
	}
}
