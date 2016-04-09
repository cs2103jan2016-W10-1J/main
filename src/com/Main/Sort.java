package com.Main;

import java.util.ArrayList;
import java.util.Collections;

//@@author A0097119X
public class Sort {
/*
 * This class is for internal use only, not open for user.
 * It will get a copy from TaskList as an input.
 * It will return an arrayList that contained sorted Tasks.
 * Based on Tasks' starting time, the order is from present to future.
 * This class functions also to check whether there is overlapping of time span,
 * because we do NOT allow such cases of overlapping.
 * If sorting successfully, it will return an arrayList 
 * Specifically for Class SearcherForFreeTimeSlot;
 * Otherwise, it will return an error message to Adder Class,
 * and prevent the adding action to be successful.
 * In the later case, the Adder class needs to return a message 
 * telling user where the overlapping occurs.
 */
	ArrayList<Task> toBeSorted;
	
	public Sort(ArrayList<Task> toBeSorted) {
		this.toBeSorted = toBeSorted;
	}
	
	public ArrayList<Task> sortThis(){
		Collections.sort(toBeSorted, Task.COMPARE_BY_NAME);
		Collections.sort(toBeSorted, Task.COMPARE_BY_TIME);
		Collections.sort(toBeSorted, Task.COMPARE_BY_DATE);
		
		return toBeSorted;
	}	
}
