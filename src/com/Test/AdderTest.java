package com.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import com.Main.Adder;
import com.Main.Commander;
import com.Main.ConverterToString;
import com.Main.Parser;
import com.Main.Task;


public class AdderTest {

	ArrayList<Task> TaskList = new ArrayList<Task>();
	int testIndex = 0;
	String actualOutput;
	String[] parsedUserInput = new String[7];
	@Test
	public void executeTest() {
		
		testIndex++;
		
		parsedUserInput[0] = "TaskName "+ Integer.toString(testIndex);
		parsedUserInput[1] = "Location "+ Integer.toString(testIndex);
		parsedUserInput[2] = "Date "+ Integer.toString(testIndex);
		parsedUserInput[3] = "Start "+ Integer.toString(testIndex);
		parsedUserInput[4] = "End "+ Integer.toString(testIndex);
		parsedUserInput[5] = "Tag "+ Integer.toString(testIndex);
		parsedUserInput[6] = "Memo "+ Integer.toString(testIndex);
			
		Adder adderInst1 = new Adder(parsedUserInput, TaskList);
		actualOutput = adderInst1.execute();
		assertEquals("", "Task added successfully", actualOutput); 
		//Testing under the condition that when TaskList is empty.	
	}
	
	@Test
	public void executeTest2() {
		
		testIndex++;
		
		parsedUserInput[0] = "TaskName "+ Integer.toString(testIndex);
		parsedUserInput[1] = "Location "+ Integer.toString(testIndex);
		parsedUserInput[2] = "Date "+ Integer.toString(testIndex);
		parsedUserInput[3] = "Start "+ Integer.toString(testIndex);
		parsedUserInput[4] = "End "+ Integer.toString(testIndex);
		parsedUserInput[5] = "Tag "+ Integer.toString(testIndex);
		parsedUserInput[6] = "Memo "+ Integer.toString(testIndex);
			
		Adder adderInst1 = new Adder(parsedUserInput, TaskList);
		actualOutput = adderInst1.execute();
		assertEquals("", "Task added successfully", actualOutput); 
		//Testing under the condition that when TaskList is NOT empty.
		
	}
	
	@Test
	public void executeTest3() {
		testIndex++;
		String[] parsedUserInputNull = null;
					
		Adder adderInst1 = new Adder(parsedUserInputNull, TaskList);
		actualOutput = adderInst1.execute();
		assertEquals("", "Task not added successfully", actualOutput); 
		//Testing under the condition that when it throws Null Pointer Exception
		
	}
	
	@Test// Integration Testing with Parser with no flexibility of User input
	public void excuteTestWithParser(){
		Parser parserInst = new Parser();
		String userInput = "add taskName @ location on date from start~end #tag -notification";
		Commander commanderInst = parserInst.parse(userInput, TaskList);
		actualOutput = commanderInst.execute();
		assertEquals("", "Task added successfully", actualOutput); 
	}
	
	@Test// Integration Testing with Parser with no Location
	public void excuteTestWithParserNoLocation(){
		Parser parserInst = new Parser();
		String userInput = "add taskName on date from start~end #tag -notification";
		Commander commanderInst = parserInst.parse(userInput, TaskList);
		actualOutput = commanderInst.execute();
		assertEquals("", "Task added successfully", actualOutput); 
	}
	
	@Test// Integration Testing with Parser with no tag and notification
	public void excuteTestWithParserNoTagNoNotice(){
		Parser parserInst = new Parser();
		String userInput = "add taskName @ location on date from start~end";
		Commander commanderInst = parserInst.parse(userInput, TaskList);
		actualOutput = commanderInst.execute();
		assertEquals("", "Task added successfully", actualOutput); 
	}
	
	@Test// Integration Testing with Parser with no Starting time
	public void excuteTestWithParserDeadline(){
		Parser parserInst = new Parser();
		String userInput = "add taskName on date ~ end #tag -notification";
		Commander commanderInst = parserInst.parse(userInput, TaskList);
		actualOutput = commanderInst.execute();
		assertEquals("", "Task added successfully", actualOutput); 
	}
	
	@Test// Integration Testing with Parser with no Task Name
	public void excuteTestWithParserNoTaskName(){
		Parser parserInst = new Parser();
		String userInput = "add  on date from start~end #tag -notification";
		Commander commanderInst = parserInst.parse(userInput, TaskList);
		actualOutput = commanderInst.execute();
		
		assertEquals("", "Task added successfully", actualOutput); 
	}

	
}
