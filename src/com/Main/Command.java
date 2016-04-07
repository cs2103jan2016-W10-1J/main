//@@author a0116764b-unused
package com.Main;

public class Command {
	Task task;
	String commandType;
	int deleteRow;
	int updateRow;
	private String updateType = "";
	private String updateDetail = "";

	public Command() {

	}
	
	public void setUpdateType(String parameter){
		updateType = parameter;
	}
	
	public  void setUpdateDetail(String parameter){
		updateDetail = parameter;
	}
	
	public void setDeleteRow(int indexToDelete){
		deleteRow = indexToDelete;
	}
	public void setUpdateRow(int indexToDelete){
		updateRow = indexToDelete;
	}
	
	public void setCommandType(String cmdType) {
		this.commandType = cmdType;
	}
	
	public void setTask(Task task){
		this.task = task;
	}
	
	public int getDeleteRow(){
		return deleteRow;
	}
	
	public int getUpdateRow(){
		return updateRow;
	}
	
	public String getUpdateType(){
		return updateType;
	}
	
	public String getUpdateDetail(){
		return updateDetail;
	}
	

	public Task getTask() {
		return task;
	}

	public String getCommandType() {
		return commandType;
	}

}