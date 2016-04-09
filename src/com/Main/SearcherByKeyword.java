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
		String keyword = keywords[0].trim();
		if (TaskList.size() == 0) {
			return "TodoList is empty";
		}
		
		for (Task currentTask : TaskList){
			if (currentTask.getTaskName().contains(keyword) || currentTask.getLocation().contains(keyword)
					|| currentTask.getNotification().contains(keyword) || currentTask.getTag().contains(keyword)){
				searchResultList.add(currentTask);
			}			
		}
		
		for (int i = 0; i < keywords.length; i++){ 
			keyword = keywords[i];
			for (Task checkTask : searchResultList){
				boolean containsKeyword = false;
				if (checkTask.getTaskName().contains(keyword) || checkTask.getLocation().contains(keyword) 
						|| checkTask.getNotification().contains(keyword) || checkTask.getTag().contains(keyword)){
					containsKeyword = true;
				} 
				if (!containsKeyword){
					toRemove.add(checkTask); 
				}
			}
		}
		searchResultList.removeAll(toRemove);
		
		ConverterToString output = new ConverterToString(searchResultList);
		
		return output.convert();

	}

	@Override
	public String undo() {
		//String feedback = "Your last action is searching, which cannot be undone.";
		return "";
	}
}

// old SearcherByKeyword code by zhiyun
/*
public class SearcherByKeyword implements Commander {
private String keyWord;
private ArrayList<Task> TaskList;
private ArrayList<Task> searchResultList= new ArrayList<Task>();

public SearcherByKeyword(String[] parsedUserInput, ArrayList<Task> TaskList){
	//The 1st element in the string array is the keyword for search
	keyWord = parsedUserInput[0];
	this.TaskList = TaskList;
}

@Override
public String execute() {
	keyWord = keyWord.trim();
	if (TaskList.size() == 0) {
		return "TodoList is empty";
	} else {
		for (Task currentTask : TaskList){
			if (currentTask.getTaskName().contains(keyWord)){
				searchResultList.add(currentTask);
			}
			else {
				if (currentTask.getLocation().contains(keyWord)){
					searchResultList.add(currentTask);
				}
				else {
					if (currentTask.getNotification().contains(keyWord)){
						searchResultList.add(currentTask);
					}
					else {
						if (currentTask.getTag().contains(keyWord)){
							searchResultList.add(currentTask);
						}
					}
				}
			}

		}
		ConverterToString output = new ConverterToString(searchResultList);
		return output.convert();
	}

}
}
*/