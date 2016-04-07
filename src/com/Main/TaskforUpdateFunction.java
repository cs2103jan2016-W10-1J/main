package com.Main;

public class TaskforUpdateFunction {
	private Task newTask;
	private Task oldTask;
	
	public TaskforUpdateFunction(){
		newTask = null;
		oldTask = null;
		
	}
	
	public Task getOldTask() {
		return oldTask;
	}
	public void setOldTask(Task oldTask) {
		this.oldTask = oldTask;
	}
	public Task getNewTask() {
		return newTask;
	}
	public void setNewTask(Task newTask) {
		this.newTask = newTask;
	}
	
}
