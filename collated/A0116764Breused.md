# A0116764Breused
###### \src\com\Main\SearcherByKeyword.java
``` java
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
		
```
