//@@author A0115449A
package com.Main;

import java.util.ArrayList;
/*
 * This class will take in a string of Keyword.
 * and it will search from the TaskList for desired Tasks.
 * Then it will get an arrayList of Task objects fulfilling the requirement.
 * Before return, it will call the ConverterToString Class with the arrayList as the parameter.
 * The ConverterToString Object will return a string to the processor for display.
 */
public class SearcherByKeyword implements Commander {
	private String[] keywords;
	private ArrayList<Task> TaskList;
	private ArrayList<Task> searchResultList = new ArrayList<Task>();
	private ArrayList<Task> toRemove = new ArrayList<Task>();


	public SearcherByKeyword(String[] parsedUserInput, ArrayList<Task> TaskList){
		//The 1st element in the string array is the keyword for search
		keywords = parsedUserInput;
		this.TaskList = TaskList;
	}
	
	//@@author A0116764B
	@Override
	public String execute() {
		String keyword = keywords[0].trim().toLowerCase();
		if (TaskList.size() == 0) {
			return "TodoList is empty";
		}
		
		for (Task currentTask : TaskList){
			String taskName = currentTask.getTaskName();
			String location = currentTask.getLocation();
			String tag = currentTask.getTag();
			String notification = currentTask.getNotification();
			if (taskName.toLowerCase().contains(keyword) || location.toLowerCase().contains(keyword) 
					|| tag.toLowerCase().contains(keyword) || notification.toLowerCase().contains(keyword)){
				searchResultList.add(currentTask);
			}			
		}
		
		for (int i = 0; i < keywords.length; i++){ 
			keyword = keywords[i];
			keyword = keyword.toLowerCase();
			for (Task checkTask : searchResultList){
				boolean containsKeyword = false;
				String taskName = checkTask.getTaskName();
				String location = checkTask.getLocation();
				String tag = checkTask.getTag();
				String notification = checkTask.getNotification();
				if (taskName.toLowerCase().contains(keyword) || location.toLowerCase().contains(keyword) 
						|| tag.toLowerCase().contains(keyword) || notification.toLowerCase().contains(keyword)){
					containsKeyword = true;
				} 
				if (!containsKeyword){
					toRemove.add(checkTask); 
				}
			}
		}
		searchResultList.removeAll(toRemove);
		Processor.setLastCommanderInst(this);
		ConverterToString output = new ConverterToString(searchResultList);
		
		return output.convert();

	}

	public String toLowerCase(String str){
		str = str.toLowerCase();
		return str;
	}
	
	@Override
	public String undo() {
		String feedback = "Your last action is searching, which cannot be undone.";
		return feedback;
	}
}

