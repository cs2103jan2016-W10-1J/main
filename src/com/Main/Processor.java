package com.Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;  


public class Processor {
	// @@author A0100111R
	private boolean isDebuging = false;

	private TextFileSaver storage = null;
	// private static Logger ProcessorLogger = Logger.getLogger("Log of
	// Processor");
	private Parser parserInst = null;
	private String feedbackMessage = "";
	public ArrayList<String> messageThread = new ArrayList<String>();

	private static Commander lastCommanderInst = null; // for Undo function.

	private static ArrayList<Task> eventList = new ArrayList<Task>();
	private static ArrayList<Task> floatList = new ArrayList<Task>();
	private static ArrayList<Task> deadlineList = new ArrayList<Task>();
	private static ArrayList<Task> doneTaskList = new ArrayList<Task>();

	public Processor() {
		parserInst = new Parser();
		storage = new TextFileSaver();
		setUp();
	}

	/**
	 * Set up the date attribute for every task in the list thru iteration.
	 * Set up the Task ID  for every task in the list thru iteration.
	 * 
	 */
	private void setUp() {
		ArrayList<Task> tasksArray = storage.getTaskData();
		for (int i = 0; i < storage.getTaskData().size(); i++) {
			Task taskInst = tasksArray.get(i);
			taskInst.setTaskID(i + 1);// generation of TaskID, 1-based.
			Adder.setTaskIDMax(i + 1);
			taskInst.determineTaskType();
			taskInst.setCalendar();
			if (isDebuging) {
				showCurrentTasksInfo(taskInst);
			}
		}
		//updating GUI display panel
		Displayer defaultDisplay = new Displayer(tasksArray);
		defaultDisplay.execute();
	}

	public List<String> executeCommand(String userInput) {
		List<String> output = null;
		executeByRequests(userInput);
		storage.saveFile();
		String[] array = feedbackMessage.split(System.lineSeparator());
		output = Arrays.asList(array);
		return output;
	}

	/**
	 * @param userInput
	 * Handle switch, undo and the rest of the user requests
	 * by calling respective objects.
	 */
	private void executeByRequests(String userInput) {
		if (userInput.startsWith("cd", 0)) {
			storage = new TextFileSaver(userInput.substring(3));
			feedbackMessage = "Opened " + userInput.substring(3);
		}
		else if (userInput.equals("undo")) {
			if (lastCommanderInst == null || lastCommanderInst.equals(null)) {
				feedbackMessage = "Sorry, the last action cannot be undo further.";
			} else {
				feedbackMessage = lastCommanderInst.undo();
			}
		} else {
			Commander commanderInst = parserInst.parse(userInput, storage.getTaskData());
			feedbackMessage = commanderInst.execute();
		}
	}

	public TextFileSaver getStorage() {
		return storage;
	}

	public void readFile() {
		storage.readFile();
	}

	// @@author A0100111R
	public static ArrayList<Task> getEventList() {
		return eventList;
	}

	public static void setEventList(ArrayList<Task> eventList) {
		Processor.eventList = eventList;
	}

	public static ArrayList<Task> getFloatList() {
		return floatList;
	}

	public static void setFloatList(ArrayList<Task> floatList) {
		Processor.floatList = floatList;
	}

	public static ArrayList<Task> getDeadlineList() {
		return deadlineList;
	}

	public static void setDeadlineList(ArrayList<Task> deadlineList) {
		Processor.deadlineList = deadlineList;
	}

	public static ArrayList<Task> getDoneTaskList() {
		return doneTaskList;
	}

	public static void setDoneTaskList(ArrayList<Task> doneTaskList) {
		Processor.doneTaskList = doneTaskList;
	}

	public static void setLastCommanderInst(Commander lastCommanderInst) {
		Processor.lastCommanderInst = lastCommanderInst;
	}

	public ArrayList<String> getMessageThread() {
		return messageThread;
	}

	/**
	 * @param taskInst
	 * If isDebugging = false, not being executed
	 * Display the existing tasks to console for debugging purpose
	 */
	private void showCurrentTasksInfo(Task taskInst) {
		String taskId = Integer.toString(taskInst.getTaskID());
		System.out.println(taskId);

		String titleDetail = taskInst.getTaskName();
		System.out.println("The detail of titleDetailString is" + "<" + titleDetail + ">");

		String dateDetail = taskInst.getDate();
		System.out.println("The detail of dateString is" + "<" + dateDetail + ">"
				+ " should at least have a single space < >");

		String startDetail = taskInst.getStart();
		System.out.println("The detail of startDetailString is" + "<" + startDetail + ">");
		String endDetail = taskInst.getEnd();
		System.out.println("The detail of endDetailString is" + "<" + endDetail + ">");

		String taskType = taskInst.getTaskType().toString();
		System.out.println(taskType);
		System.lineSeparator();
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