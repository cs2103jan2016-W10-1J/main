package com.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.Main.Adder;
import com.Main.Deleter;
import com.Main.Task;
import com.Main.Updater;

public class LogicTest {
	ArrayList<Task> TaskList;
	String[] userInputDelete1;
	String[] userInputDelete2;
	String[] userInputAdd1;
	String[] userInputAdd2;
	String[] userInputUpdate;
	String[] userInputSearch;

	@Before
	public void initialize(){
		TaskList = new ArrayList();
		Task Task1 = new Task();
		Task Task2 = new Task();
		Task1.setTask("xxx");
		Task1.setLocation("com1");
		Task1.setDate("wednesday");
		Task1.setStart("1000");
		Task1.setEnd("1100");
		Task1.setTag("tag");
		Task1.setNotification("memo");
		Task2.setTask("laundry");
		Task2.setLocation("pgp");
		Task2.setDate("tonite");
		Task2.setStart("2200");
		Task2.setEnd("2300");
		Task2.setTag("tag");
		Task2.setNotification("memo");
		TaskList.add(Task1);
		TaskList.add(Task2);
	}

	@Test
	public void testDelete1() {
		//try delete task No.1, equivalence partitioning
		Deleter testDelete = new Deleter(userInputDelete1, TaskList);
		userInputDelete1 = new String[2];
		userInputDelete1[0] = "delete";
		userInputDelete1[1] = "1";
		String output = testDelete.execute();
		assertEquals(output,"Task 1: xxx on wednesday deleted successfully");
	}

	@Test
	public void testDelete2() {
		//try delete task No.-99, equivalence partitioning
		Deleter testDelete = new Deleter(userInputDelete2, TaskList);
		userInputDelete2 = new String[2];
		userInputDelete2[0] = "delete";
		userInputDelete2[1] = "-99";
		String output = testDelete.execute();
		assertEquals(output,"java.lang.ArrayIndexOutOfBoundsException");
	}

	@Test
	public void testAdd1(){
		//equivalence partitioning
		Adder testAdd = new Adder(userInputAdd1, TaskList);
		userInputAdd1 = new String[7];
		userInputAdd1[0] = "cs2103lecture";
		userInputAdd1[1] = "icube";
		userInputAdd1[2] = "friday";
		userInputAdd1[3] = "1600";
		userInputAdd1[4] = "1800";
		userInputAdd1[5] = "tag";
		userInputAdd1[6] = "memo";
		String output = testAdd.execute();
		assertEquals(output,"Task added successfully");
	}
	
	@Test
	public void testAdd2(){
		//test on single  element
		Adder testAdd = new Adder(userInputAdd2, TaskList);
		userInputAdd2 = new String[7];
		userInputAdd2[0] = "cs2103lecture";
		userInputAdd2[1] = "icube";
		userInputAdd2[2] = "friday";
		userInputAdd2[3] = "1600";
		userInputAdd2[4] = "1800";
		userInputAdd2[5] = "tag";
		userInputAdd2[6] = "memo";
		String output = (TaskList.get(2)).getLocation();
		assertEquals(output,"icube");
	}

	@Test
	public void testUpdate1(){
		//equivalence partitioning
		Updater testUpdate = new Updater(userInputUpdate, TaskList);
		userInputUpdate = new String[3];
		userInputUpdate[0] = "1";
		userInputUpdate[1] = "date";
		userInputUpdate[2] = "tuesday";
		String output = testUpdate.execute();
		assertEquals(output,"Task 1's date was updated successfully from wednesday to tuesday.");
	}
	
	@Test
	public void testUpdate2(){
		//test for out of bound
		Updater testUpdate = new Updater(userInputUpdate, TaskList);
		userInputUpdate = new String[3];
		userInputUpdate[0] = "-1";
		userInputUpdate[1] = "bullshit";
		userInputUpdate[2] = "tuesday";
		String output = testUpdate.execute();
		assertEquals(output,"Keyword for the type of detail is invalid.");
	}

	//@Test
	public void testSearch(){
		//SearcherByKeyword  testSearch = 
		userInputSearch = new String [1];
		userInputSearch[0] = "laptop";
	}

}
