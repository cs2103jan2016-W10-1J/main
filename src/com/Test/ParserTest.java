package com.Test;
import org.junit.Test;

import com.Main.Parser;

import static org.junit.Assert.*;

public class ParserTest {
	
	@Test
	public void addTest(){
		Parser parser= new Parser();
		String string = "task @ loc on date from start~end #tag -notification";
		String task = parser.getTaskName(string);
		assertEquals(task, "task");
		String loc = parser.getLocation(string);
		assertEquals(loc, "loc");
		String date = parser.getDate(string);
		assertEquals(date,"date");
		String start = parser.getStart(string);
		assertEquals(start, "start");
		String end = parser.getEnd(string);
		assertEquals(end, "end");
		String tag = parser.getTag(string);
		assertEquals(tag,"tag");
		String notification = parser.getNotification(string);
		assertEquals(notification, "notification");
	}
	@Test
	public void updateTest(){
		Parser parser = new Parser();
		String string = "1 task newName";
		String updateRow = parser.getNextWord(string);
		assertEquals(updateRow, "1");
		string = parser.removeFirstWord(string);
		assertEquals(string,"task newName");
		String updateType = parser.getNextWord(string);
		assertEquals(updateType, "task");
		string = parser.removeFirstWord(string);
		String updateDetail = string;
		assertEquals(updateDetail, "newName");
		
		String string2 = "2 location there";
		String updateRow2 = parser.getNextWord(string2);
		assertEquals(updateRow2, "2");
		string2 = parser.removeFirstWord(string2);
		assertEquals(string2,"location there");
		String updateType2 = parser.getNextWord(string2);
		assertEquals(updateType2, "location");
		string2 = parser.removeFirstWord(string2);
		String updateDetail2 = string2;
		assertEquals(updateDetail2, "there");
	}
	
	@Test
	public void deleteTest(){
		Parser parser = new Parser();
		String string = "2";
		String deleteRow = parser.getDeleteRow(string);
		assertEquals(deleteRow, "2");
	}
}

