package com.Main;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;  

public class Processor {
		     
	private TextFileSaver storage;
	//private TaskforUpdateFunction UpdatedTask;
	//private static Logger ProcessorLogger = Logger.getLogger("Log of Processor");
	private Parser parserInst;
	private String feedbackMessage = "";
	public ArrayList<String> messageThread = new ArrayList<String>();
	private static Commander lastCommanderInst = null; // for Undo function.

	public static void setLastCommanderInst(Commander lastCommanderInst) {
		Processor.lastCommanderInst = lastCommanderInst;
	}

	public ArrayList<String> getMessageThread() {
		return messageThread;
	}

	public Processor(){
		parserInst = new Parser();
		storage = new TextFileSaver();
		//Dispatcher.setTaskList(storage.getTaskData());
		setUp();
	}

	/**
	 * Set up the date attribute for every task in the list.
	 * Set up the Task ID for every task in the list.
	 */
	private void setUp() {
		for (int i = 0; i < storage.getTaskData().size(); i++ ){
			ArrayList<Task> tasksArray = storage.getTaskData();
			tasksArray.get(i).setCalendar();
			tasksArray.get(i).setTaskID(i+1);//generation of TaskID, 1-based.
		}
	}
	
	public List<String> executeCommand(String userInput){
		List<String> output = null;
		if (userInput == "undo"){
			if(lastCommanderInst == null){
				feedbackMessage = "Sorry, the last action cannot be undo further.";				
			}
			else{
				feedbackMessage = lastCommanderInst.undo();
			}
		}
		else{
			Commander commanderInst = parserInst.parse(userInput, storage.getTaskData());
			feedbackMessage = commanderInst.execute();
			
		}
		storage.saveFile();
		String[] array = feedbackMessage.split(System.lineSeparator());
		output = Arrays.asList(array);
		return output;
	}
	
	
	public TextFileSaver getStorage(){
		return storage;
	}
	
	public void readFile(){
		storage.readFile();
	}
	
	// Done by Matthew
	public String setStringInGreen(String outputStr){
		outputStr = "<font color=\"green\">" + outputStr + "</font>";
		return outputStr;
	}
	
	public String setStringInRed(String outputStr){
		outputStr = "<font color=\"red\">" + outputStr + "</font>";
		return outputStr;
	}
	
	public String setStringInBlue(String outputStr){
		outputStr = "<font color=\"blue\">" + outputStr + "</font>";
		return outputStr;
	}
	
	public String setStringWithRGB(String outputStr, String r, String g, String b){
		outputStr = "<font color=\"rgb("+r+", "+g+", "+b+")\">" + outputStr + "</font>";
		return outputStr;
	}
	
}
/*
	
	public TaskforUpdateFunction getUpdatedTask(){
		return UpdatedTask;
	}
	
	private void addTask(Parser parser){
		storage.readFile();
		ArrayList<Task> TaskList = storage.getTaskData();
		Task taskTobeAdded = parser.getCommand().getTask();
		assert(taskTobeAdded != null);
		
		try {
			TaskList.add(taskTobeAdded);
		} catch (Exception e) {
			//System.out.println("task not added successfuly" + e.getMessage());
			e.printStackTrace();
			ProcessorLogger.log(Level.WARNING, "task not added successfuly", e);
		}		
		storage.saveFile(TaskList);
	}
	

	private void deleteTask(Parser parser){
		int indexForDeletion = parser.getCommand().getDeleteRow();
		assert(indexForDeletion >= 0);	
		ArrayList<Task> TaskList = storage.getTaskData();
		try {
			TaskList.remove(indexForDeletion);
			ProcessorLogger.log(Level.INFO, "deletion done successfully.");
		} catch (IndexOutOfBoundsException e) {
			ProcessorLogger.log(Level.WARNING, "Task not deleted successfully due to invalid index", e);
			e.printStackTrace();
		}
		storage.saveFile(TaskList);
		ProcessorLogger.log(Level.INFO, "deletion saved.");
	}
		
	
	private TaskforUpdateFunction updateTask(Parser parser) {
		int IndexForUpdate = parser.getCommand().getUpdateRow() - 1;
		String TypeToUpdate = parser.getCommand().getUpdateType();
		String DetailToUpdate = parser.getCommand().getUpdateDetail();
		ArrayList<Task> TaskList = storage.getTaskData();
		//IndexForUpdate = -1;
		assert(IndexForUpdate >= 0);
		assert(TypeToUpdate != null);
		ProcessorLogger.log(Level.INFO, "gonna start to execute the update request");
		try {
			UpdatedTask.setOldTask(TaskList.get(IndexForUpdate));
			TaskList = executeUpdateRequest(IndexForUpdate, TypeToUpdate, DetailToUpdate, TaskList);
			
			UpdatedTask.setNewTask(TaskList.get(IndexForUpdate));
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Invalid Task number detected, re-enter.");
			//e.printStackTrace();
			ProcessorLogger.log(Level.WARNING, "Updater error: invalid Task reference number", e);
		}
		storage.saveFile(TaskList);
		ProcessorLogger.log(Level.INFO, "update done and saved");

		//displayOneTask(IndexForUpdate, TaskList);
		
		return UpdatedTask;
	}

	private String displayOneTask(int indexForUpdate, ArrayList<Task> taskList) {
		StringBuilder TaskToShow = new StringBuilder();
		TaskToShow.append(String.format(DISPLAY_TASK_TITLE_MESSAGE, taskList.get(indexForUpdate).getTaskName()));
		TaskToShow.append(String.format(DISPLAY_TASK_DATE_MESSAGE, taskList.get(indexForUpdate).getDate()));
		TaskToShow.append(String.format(DISPLAY_TASK_LOCATION_MESSAGE, taskList.get(indexForUpdate).getLocation()));
		TaskToShow.append(String.format(DISPLAY_TASK_START_MESSAGE, taskList.get(indexForUpdate).getStart()));
		TaskToShow.append(String.format(DISPLAY_TASK_END_MESSAGE, taskList.get(indexForUpdate).getEnd()));
		TaskToShow.append(String.format(DISPLAY_TASK_TAG_MESSAGE, taskList.get(indexForUpdate).getTag()));
		TaskToShow.append(String.format(DISPLAY_TASK_NOTIFICATION_MESSAGE, taskList.get(indexForUpdate).getNotification()));
						
		return TaskToShow.toString();
		
	}
	
	private static final String DISPLAY_TASK_TITLE_MESSAGE = "Title:%1$s\r\n";
	private static final String DISPLAY_TASK_DATE_MESSAGE = "Date:%1$s\r\n";
	private static final String DISPLAY_TASK_LOCATION_MESSAGE = "Location:%1$s\r\n";
	private static final String DISPLAY_TASK_START_MESSAGE = "Start:%1$s\r\n";
	private static final String DISPLAY_TASK_END_MESSAGE = "End:%1$s\r\n";
	private static final String DISPLAY_TASK_TAG_MESSAGE = "Tag:%1$s\r\n";
	private static final String DISPLAY_TASK_NOTIFICATION_MESSAGE = "Notification:%1$s\r\n";
}
	

	*/