package com.Main;
import java.util.ArrayList;
import java.util.logging.Level;
//@@author A0100111R

public class Adder implements Commander{
	private Task newTask = new Task();
	ArrayList<Task> taskList = new ArrayList<Task>();
	private static int taskIdMaxLimit = 0;
	

	public static int getTaskIDMax() {
		return taskIdMaxLimit;
	}

	public static void setTaskIDMax(int taskIDMax) {
		Adder.taskIdMaxLimit = taskIDMax;
	}

	public Adder(String[] parsedUserInput, ArrayList<Task> originalTaskList) {
		//The element of the string array will be 
		//0.taskName, 1.location 2.date 3.start 4.end 5.tag 6.notification
		this.taskList = originalTaskList;
		try {
			newTask.setTask(parsedUserInput[0]);
			newTask.setLocation(parsedUserInput[1]);
			newTask.setDate(parsedUserInput[2]);
			newTask.setStart(parsedUserInput[3]);
			newTask.setEnd(parsedUserInput[4]);
			newTask.setTag(parsedUserInput[5]);
			newTask.setNotification(parsedUserInput[6]);
			newTask.setCalendar();
			newTask.setTaskID(taskIdMaxLimit+1);
			newTask.determineTaskType();
		} catch (NullPointerException e) {
			System.out.println("The Task is null. " + e.toString());
		}
	}

	@Override
	public String execute() {
		if (newTask.getTaskName().equals(" ") || newTask.getTaskName().equals(""))
			return "Task NOT added successfully due to empty Task title!";
		
		if (newTask.getDate().equals("invalid date")){
			return "Task NOT added successfully due to invalid date!";
		}
		
			taskList.add(newTask);
			updateAfterSuccessfulExecution();
			
			String taskType = newTask.getTaskType().toString();
			return "Task added successfully"+ "with type being: "+ taskType;
			
			//ProcessorLogger.log(Level.WARNING, "task not added successfully", e);
		
	}

	/**
	 * Update the following:
	 * task ID limit, GUI display panel and undo track.
	 */
	private void updateAfterSuccessfulExecution() {
		taskIdMaxLimit++;
		updateGui();			
		Processor.setLastCommanderInst(this);
	}

	@Override
	public String undo() {
		ConverterToString taskConverter = new ConverterToString(newTask);
		String taskFeedBackString = taskConverter.convert();
		
		if(taskList.remove(newTask)){
			//Undoing adding is designed for no chance of redoing.
			Processor.setLastCommanderInst(null);
			updateGui();
			return "The following task is removed:"
			+System.lineSeparator()+taskFeedBackString;		
		}
		else
			return "The undo to remove last task added is unsuccessful.";
	}

	/**
	 * 
	 */
	private void updateGui() {
		Displayer guiUpdater = new Displayer(taskList);
		guiUpdater.execute();
	}

}
