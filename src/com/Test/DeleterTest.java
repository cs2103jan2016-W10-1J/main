package com.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import com.Main.Deleter;
import com.Main.Task;

public class DeleterTest {
	
	private int indexToDelete;
	private ArrayList<Task> TaskList = new ArrayList<Task>();
	private String taskDeleted;
	String actualOutput;
	Task newTask = new Task();
	String[] parsedUserInput = new String[1];
	
	@Before
	public void setUp(){
		newTask.setTask("TaskName");
		newTask.setLocation("location");
		newTask.setDate("date");
		newTask.setDate("Date");
		newTask.setEnd("end");
		newTask.setTag("tag");
		newTask.setNotification("notification");
		TaskList.add(newTask);
		
		parsedUserInput[0] = Integer.toString(1);
	}
	
	@Test
	public void executeTest() {
		
		Deleter deleterInst = new Deleter(parsedUserInput, TaskList);
		actualOutput = deleterInst.execute();
		assertEquals("", "Task deleted successfully", actualOutput); 
	}
	@Test
	public void executeTestDeleteUnsuccess() {
		
		Deleter deleterInst = new Deleter(parsedUserInput, TaskList);
		actualOutput = deleterInst.execute();
		assertEquals("", "Task deleted successfully", actualOutput); 
	}

}
