//@@author A0116764B-reused
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

	public SearcherByKeyword(){
		
	}
	
	private static int EMPTY = 0;
	private static boolean DOES_NOT_CONTAIN = false;
	private static boolean CONTAIN = true;
	
	public SearcherByKeyword(String[] parsedUserInput, ArrayList<Task> TaskList){
		//The 1st element in the string array is the keyword for search
		keywords = parsedUserInput;
		this.TaskList = TaskList;
	}
	
	@Override
	public String execute() {
		String keyword = keywords[0].trim().toLowerCase();
		if (TaskList.size() == EMPTY) {
			return "TodoList is empty";
		}
		
//@@author A0116764B		
		for (Task currentTask : TaskList){
			keyword = toLowerCase(keyword);
			String taskName = toLowerCase(currentTask.getTaskName());
			String location = toLowerCase(currentTask.getLocation());
			String notification = toLowerCase(currentTask.getNotification());
			String tag = toLowerCase(currentTask.getTag());
			if (taskName.contains(keyword) || location.contains(keyword)
					|| notification.contains(keyword) || tag.contains(keyword)){
				searchResultList.add(currentTask);
			}			
		}
		
		for (int i = 0; i < keywords.length; i++){ 
			keyword = toLowerCase(keywords[i]);
			for (Task checkTask : searchResultList){
				String taskName = toLowerCase(checkTask.getTaskName());
				String location = toLowerCase(checkTask.getLocation());
				String notification = toLowerCase(checkTask.getNotification());
				String tag = toLowerCase(checkTask.getTag());
				boolean containsKeyword = DOES_NOT_CONTAIN;
				if (taskName.contains(keyword) || location.contains(keyword)
						|| notification.contains(keyword) || tag.contains(keyword)){
					containsKeyword = CONTAIN;
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
	
	public String toLowerCase(String string){
		return string.toLowerCase();
	}

	@Override
	public String undo() {
		//String feedback = "Your last action is searching, which cannot be undone.";
		return "";
	}
}