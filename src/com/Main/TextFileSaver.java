package com.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

//@@author A0097119X
public class TextFileSaver {
	
	private File fileDirectory;
	private File file;
	private File completedFile;
	private String lastOpenedDirectory = "Last_Accessed.txt";
	private String fileName;
	private String completedFileName;
	private ArrayList<Task> taskData;
	
	public File getFile() {
		return file;
	}
	
	public File getCompletedFile(){
		return completedFile;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void setCompletedFile(File completedFile){
		this.completedFile = completedFile;
	}

	public String getFileName() {
		return fileName;
	}
	
	public String getCompletedFileName(){
		return completedFileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setCompletedFileName(String completedFileName){
		this.completedFileName = completedFileName;
	}
	
	public ArrayList<Task> getTaskData(){
		return taskData;
	}
		
	public void setTaskData(ArrayList<Task> taskData){
		this.taskData = taskData;
	}
	

	/* Constructor for when program is launched
	 * Checks for existence of Last_Accessed.txt and the relevant files.
	 * Creates them if it does not exist.
	 */
	public TextFileSaver(){
		taskData = new ArrayList<Task>();
		fileDirectory = new File(lastOpenedDirectory);
		
		//Check for existence of Last_Accessed.txt
		//If it does not exist, create the relevant files
		if(!fileDirectory.exists()){
		fileName = "Record.txt";
		completedFileName = "Record_Archive.txt";
		try {
			createLastAccessed(); 		//Creates Last_Accessed.txt
			checkSaveFile(fileName); 	//Checks for existence of Record.txt. Creates it if it doesn't exist.
			checkArchiveFile();			//Checks for existence of Record_Archive.txt. Creates it if it doesn't exist.	
			readFile();					//Read from the files
		} catch (IOException e) {
			e.printStackTrace();
			}
		}
		
		//If Last_Accessed.txt exists. Read it and read from the last accessed directory.
		else{
			try {
				readLastAccessed();		//Read from Last_Accessed.txt
				} catch (IOException e) {
					e.printStackTrace();
				}	
			updateArchiveFileName();	//Update archive filename
			readFile();					//Read from the files
		}
	}

	
	
	/* Constructor for changing directory
	 * Check for whether file exists. Creates it if it doesn't.
	 * Record the most recently accessed file into Last_Accessed.txt
	 */
	public TextFileSaver(String fileName){
		taskData = new ArrayList<Task>();
		
		//Check that the filename is a valid input and update relevant variables if it is.
		if(fileName.length()>0){
		try {
			this.fileName = fileName;
			completedFileName = fileName.substring(0, fileName.indexOf(".")) + "_Archive.txt";
		} catch (Exception e1) {
			System.out.println("Ensure that your filename ends with .txt");
		}
		
		try {
			checkSaveFile(fileName);	//Attempt to locate fileName.txt. Create new file if file does not exist
			checkArchiveFile();			//Attempt to locate fileName_Archive.txt. Create new file if file does not exist.
			readFile();					//Read the files
			updateLastAccessedFile(fileName);//Update Last_Access.txt
		} catch (IOException e) {
			System.out.println("Ensure that your filename ends with .txt");
			}	
		}
	}
	
	/* Function call for saving files
	 */
	public void saveFile(){
		FileWriter savefile;
		FileWriter completedSaveFile;
		try {
			String tempSave = "";
			String completedTempSave = "";
			Task tempTaskForSaving = new Task();
			String[] taskToString = new String[11];
			savefile = new FileWriter(fileName);
			completedSaveFile = new FileWriter(completedFileName);
			
			
			sortList();	//Sort list before saving
			
			//Prepare ArrayList for saving
			for(int i=0; i<taskData.size(); i++){            
				tempTaskForSaving = taskData.get(i);
				convertTaskToString(tempTaskForSaving, taskToString);
				
				if(tempTaskForSaving.isTaskDone){
					completedTempSave = processIntoSingleStringForSaving(completedTempSave,
							taskToString);
				}
				else{
					tempSave = processIntoSingleStringForSaving(tempSave, taskToString);
				}
			}
			
			//Write to the file
			savefile.write(tempSave);                           
			savefile.close();
			completedSaveFile.write(completedTempSave);
			completedSaveFile.close();
		} catch (IOException e) {
			System.out.println("Save failed");
			e.printStackTrace();
		}
		
	}
	
	public void readFile(){
		String temp;
		String readSuccess = "";
		file = new File(fileName);
		taskData = new ArrayList<Task>();
		try {
			//Read from file and copy into ArrayList
			BufferedReader br = new BufferedReader(new FileReader(file));
			int lineReading = 1;
			while((temp = br.readLine()) != null){
				Task tempTask = new Task();
				readSuccess = addToTaskList(temp, tempTask, lineReading);
				lineReading++;
			}	
			br.close();
			
			//Read from archive and copy into ArrayList
			BufferedReader completedBR = new BufferedReader(new FileReader(completedFile));
			lineReading = 1;
			while((temp = completedBR.readLine()) != null){
				Task tempTask = new Task();
				readSuccess = addToTaskList(temp, tempTask, lineReading);
				lineReading++;
			}	
			completedBR.close();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(fileName + " and " + completedFileName + " successfully read");
	}
	
	private void sortList() {
		Sort sort = new Sort(taskData);
		taskData = sort.sortThis();
	}
	
	private void updateArchiveFileName() {
		completedFileName = fileName.substring(0, fileName.indexOf(".")) + "_Archive.txt";
		System.out.println(completedFileName);
		completedFile = new File(completedFileName);
	}

	private void readLastAccessed() throws FileNotFoundException, IOException {
		String temp;
		BufferedReader br = new BufferedReader(new FileReader(fileDirectory));
			while((temp = br.readLine()) != null){
				fileName = temp;
			}
		br.close();
	}

	private void createLastAccessed() throws IOException {
		FileWriter createDirectory = new FileWriter(lastOpenedDirectory);
		createDirectory.write(fileName);
		createDirectory.flush();
		createDirectory.close();
	}
	
	private void checkSaveFile(String fileName) throws IOException {
		file = new File(fileName);	
		if(!file.exists()) { 
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.flush();
			fileWriter.close();
			System.out.println(fileName + " does not exists. New Record.txt file has been created");
		}
	}

	private void checkArchiveFile() throws IOException {
		completedFile = new File(completedFileName);	
		if(!completedFile.exists()){
			FileWriter completedFileWriter = new FileWriter(completedFile);
			completedFileWriter.flush();
			completedFileWriter.close();
			System.out.println(completedFileName + " does not exists. New Record.txt file has been created");
		}
	}

	private void updateLastAccessedFile(String fileName) throws IOException {
		FileWriter updateLastAccessed = new FileWriter(lastOpenedDirectory);
		updateLastAccessed.write(fileName);                       
		updateLastAccessed.close();
	}

	/*Split the each line to 11 different segments. Each segment is separated in the .txt file by ~~. Adder each
	 * segment to tempTask for create a proper Task and add it to the Task ArrayList (taskData)*/
	public String addToTaskList(String temp, Task tempTask, int lineReading) {
		try {
			String[] _temp = new String[11];
			GregorianCalendar tempCal = new GregorianCalendar();
			_temp = temp.split("~~", -1);
			tempTask.setTask(_temp[0]);
			tempTask.setLocation(_temp[1]);
			tempTask.setDate(_temp[2]);
			tempTask.setStart(_temp[3]);
			tempTask.setEnd(_temp[4]);
			tempTask.setTag(_temp[5]);
			tempTask.setNotification(_temp[6]);
			if(_temp[7].equals("false")){
				tempTask.setTaskAsUndone();
			}
			else{
				tempTask.setTaskAsDone();
			}
			
			tempTask.setTaskID(Integer.parseInt(_temp[8]));
			
			if(_temp[9].length()>0){
				tempCal = convertStringToCalendar(_temp[9]);
				tempTask.setStartCal(tempCal);
			}
			
			if(_temp[10].length()>0){
				tempCal = convertStringToCalendar(_temp[10]);
				tempTask.setEndCal(tempCal);
			}
			
			taskData.add(tempTask);
			_temp = null;
			return "success";
		} catch (Exception e) {
			System.out.println("Incorrect data format in line " + lineReading + ": " + temp 
					           + " in Record.txt and is ignored by software. Please rectify before "
				               + "using to prevent loss of data.");
			e.printStackTrace();
			return "failed";
		}
	}
	
	

	/*Convert the string arrays into a single string with proper formatting before saving*/
	public String processIntoSingleStringForSaving(String tempSave,
			String[] taskToString) {
		tempSave = tempSave + taskToString[0] + "~~" + taskToString[1] + "~~" + taskToString[2] + "~~"+ taskToString[3] + "~~"+ taskToString[4] + "~~"+ taskToString[5]+ "~~" + taskToString[6] + "~~" +taskToString[7] + "~~" + taskToString[8]+ "~~" + taskToString[9]+"~~" + taskToString[10]+ "\n";
		return tempSave;
	}
	
	/*Convert the task into a string array*/
	public void convertTaskToString(Task tempTaskForSaving,
			String[] taskToString) {
		taskToString[0] = tempTaskForSaving.getTaskName().trim();
		taskToString[1] = tempTaskForSaving.getLocation().trim();
		
		if(tempTaskForSaving.getDate().trim().length()>0){
		taskToString[2] = tempTaskForSaving.getDate().trim();
		}
		else{
			taskToString[2] = " ";
		}
		
		if(tempTaskForSaving.getStart().trim().length()>0){
		taskToString[3] = tempTaskForSaving.getStart().trim();
		}
		else{
			taskToString[3] = " ";
		}
		
		if(tempTaskForSaving.getEnd().trim().length()>0){
		taskToString[4] = tempTaskForSaving.getEnd().trim();
		}
		else{
			taskToString[4] = " ";
		}
		
		taskToString[5] = tempTaskForSaving.getTag().trim();
		taskToString[6] = tempTaskForSaving.getNotification().trim();
		taskToString[7] = String.valueOf(tempTaskForSaving.isTaskDone).trim();
		taskToString[8] = String.valueOf(tempTaskForSaving.getTaskID()).trim();
		
		if(tempTaskForSaving.getStartCal() != null){
			taskToString[9] = convertCalendarToString(tempTaskForSaving.getStartCal());
		}
		else{
			taskToString[9] = "";
		}
		
		if(tempTaskForSaving.getEndCal() != null){
			taskToString[10] = convertCalendarToString(tempTaskForSaving.getEndCal());
		}
		else{
			taskToString[10] = "";
		}
	}
	
	public String convertCalendarToString(GregorianCalendar toBeConverted){
		String result = new String();
		SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy HHmm");
		result = df.format(toBeConverted.getTime());
		
		//debugging
		System.out.println(toBeConverted + " was converted to " + result);
		
		return result;
	}
	
	public GregorianCalendar convertStringToCalendar(String toBeConverted) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy HHmm");
		Date date = df.parse(toBeConverted);
		GregorianCalendar result = new GregorianCalendar();
		result.setTime(date);
		
		//debugging
		System.out.println(toBeConverted + " was converted to " + result);
		
		return result;
	}
}
