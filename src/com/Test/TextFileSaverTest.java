package com.Test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.Main.Task;
import com.Main.TextFileSaver;

public class TextFileSaverTest {
	
	//Test processIntoSingleStringForSaving
	@Test
	public void processIntoSingleStringForSavingTest() {
		TextFileSaver storage = new TextFileSaver();
		String result = new String();
		String[] toBeProcessed = new String[11];
		
		toBeProcessed[0]="Task Name";
		toBeProcessed[1]="Location";
		toBeProcessed[2]="Date";
		toBeProcessed[3]="Start";
		toBeProcessed[4]="End";
		toBeProcessed[5]="Tag";
		toBeProcessed[6]="Notification";
		toBeProcessed[7]="TaskDone";
		toBeProcessed[8]="StartCal";
		toBeProcessed[9]="EndCal";
		toBeProcessed[10]="TaskID";
		
		result = storage.processIntoSingleStringForSaving(result, toBeProcessed);
		assertEquals(result, "Task Name~~Location~~Date~~Start~~End~~Tag~~Notification~~TaskDone~~StartCal~~EndCal~~TaskID\n");
	}

	//Test convertTaskToString
	@Test
	public void convertTaskToStringTest() {
		TextFileSaver storage = new TextFileSaver();
		Task toBeProcessed = new Task();
		String[] temp = new String[11];
		
		SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy HHmm");
		Date date;
		try {
			date = df.parse("11 11 2222 1111");
			GregorianCalendar result = new GregorianCalendar();
			result.setTime(date);
			
			toBeProcessed.setTask("Task Name");
			toBeProcessed.setLocation("Location");
			toBeProcessed.setDate("Date");
			toBeProcessed.setStart("Start");
			toBeProcessed.setEnd("End");
			toBeProcessed.setTag("Tag");
			toBeProcessed.setNotification("Notification");
			toBeProcessed.setTaskAsDone();
			toBeProcessed.setStartCal(result);
			toBeProcessed.setEndCal(result);;
			toBeProcessed.setTaskID(11);;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		storage.convertTaskToString(toBeProcessed, temp);
		assertEquals(temp[0], "Task Name");
		assertEquals(temp[1], "Location");
		assertEquals(temp[2], "Date");
		assertEquals(temp[3], "Start");
		assertEquals(temp[4], "End");
		assertEquals(temp[5], "Tag");
		assertEquals(temp[6], "Notification");
		assertEquals(temp[7], "true");
		assertEquals(temp[8], "11");
		assertEquals(temp[9], "11 11 2222 1111");
		assertEquals(temp[10], "11 11 2222 1111");
	}

	
	//Test addToTaskList
	@Test
	public void addToTaskListTest() {
		TextFileSaver storage = new TextFileSaver();
		Task toBeFilled = new Task();
		String _toBeAdded = "Task Name~~Location~~Date~~Start~~End~~Tag~~Notification~~true~~11~~11 11 2222 1111~~11 11 2222 1111\n";
		int sizeOfTaskList = storage.getTaskData().size();
		
		storage.addToTaskList(_toBeAdded, toBeFilled, 0);
		sizeOfTaskList++;
		
		assertEquals(storage.getTaskData().size(), sizeOfTaskList);
	}

}
