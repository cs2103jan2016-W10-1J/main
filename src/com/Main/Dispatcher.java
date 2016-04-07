package com.Main;

import java.util.ArrayList;

public abstract class Dispatcher implements Commander {

	public static ArrayList<Task> TaskList;

	public static ArrayList<Task> getTaskList() {
		return TaskList;
	}

	public static void setTaskList(ArrayList<Task> taskList) {
		TaskList = taskList;
	}
	
	
}
