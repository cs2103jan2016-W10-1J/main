package com.Main;

import java.util.ArrayList;

//@@author A0100111R
public class Deleter implements Commander {
	private int taskID;
	private int taskIndex;
	private ArrayList<Task> taskList = null;
	private Task taskDeleted = null;

	public Deleter(String[] parsedUserInput, ArrayList<Task> TaskList) {
		// The first element in the array is the taskID to be deleted
		taskID = Integer.parseInt(parsedUserInput[0]);
		this.taskList = TaskList;
	}

	@Override
	public String execute() {
		taskIndex = SearchTaskByID(taskID);
		if (taskIndex < 0) {
			return "The taskID entered is invalid, please re-enter.";
		} else {
			taskDeleted = taskList.remove(taskIndex);
			updateGui();
			updateUndoTracker();

			String taskDeletedString = convertTaskToStringForFeedback();
			String feedback = "Task deleted successfully:" 
			+ System.lineSeparator() + taskDeletedString;
			return feedback;
		}
	}

	/**
	 * @return a string
	 */
	private String convertTaskToStringForFeedback() {
		ConverterToString taskDeletedConversion = new ConverterToString(taskDeleted);
		String taskDeletedString = taskDeletedConversion.convert();
		return taskDeletedString;
	}

	/**
	 * Update the user action tracker for future undo action
	 */
	private void updateUndoTracker() {
		Processor.setLastCommanderInst(this);
	}

	// Search the task with specified ID
	private int SearchTaskByID(int DesiredTaskID) {
		for (int i = 0; i < taskList.size(); i++) {
			if (DesiredTaskID == taskList.get(i).getTaskID()) {
				return i;
			}
		}
		// -1 for the case of invalid TaskId which can't be found 
		return -1; 					
	}

	@Override
	public String undo() {
		String feedback = convertTaskToStringForFeedback();
		taskList.add(taskDeleted);
		updateGui();
		// Undoing delete is designed for no chance of redoing.
		Processor.setLastCommanderInst(null);		
		return "The following task you deleted is added in again: " 
				+ System.lineSeparator() + feedback;
	}

	/**
	 * update GUI display panel
	 */
	private void updateGui() {
		Displayer updateGUI = new Displayer(taskList);
		updateGUI.execute();
	}

}
