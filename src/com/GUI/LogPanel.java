package com.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.Main.Task;
import com.Main.TaskforUpdateFunction;

public class LogPanel extends JPanel{

	private GridBagConstraints tasksGBC;
    private GridBagConstraints mainGBC;
    private int counter = 0;
    private String MESSAGE_WELCOME = "Welcome to TODO List!!";
    private JPanel logPanel;
    private JScrollPane scrollPanel; 
    
    private ArrayList<Component> logList= new ArrayList<Component>();
	
  //The constructor for log panel
	public LogPanel(){
        setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600, 300));
		logPanel = new JPanel();
		logPanel.setBackground(Color.WHITE);
		logPanel.setLayout(new GridBagLayout());
		tasksGBCInit(); 
		mainGBCInit();
	
		JPanel dummyPanel = new JPanel();
        dummyPanel.setBackground(new Color(0, 0, 0, 0));
        logPanel.add(dummyPanel, mainGBC);					//The dummy panel is for a trick
		 
		JLabel logItem = new JLabel("Welcome to TODO List!!");  
		logItem.setBorder(new EmptyBorder(1, 10, 1, 10));                               
		logPanel.add(logItem, tasksGBC, 0);
		logList.add(logItem);
		
		scrollPanel = new JScrollPane(logPanel);
		counter++;
		
        add(scrollPanel, BorderLayout.CENTER);
	}
	
	public void clearLog(){
		for (int i=0; i<logList.size(); i++){
			logPanel.remove(logList.get(i));
		}
		counter=0;
	}
	
	public void recordToLog(String userCommand){
		JLabel logItem = new JLabel(userCommand); 
		logItem.setBorder(new EmptyBorder(1, 10, 1, 10));
		logPanel.add(logItem, tasksGBC, counter++);
		logList.add(logItem);
		validate();
		repaint();
	}
	
	
	//console output for update command
	public void displayUpdatedTask(TaskforUpdateFunction updatedTask){
		Task oldTask = updatedTask.getOldTask();
		Task newTask = updatedTask.getNewTask();

	    String taskUpdateLabel =  "Task:               "+ oldTask.getTaskName() +"     =>     " + newTask.getTaskName();
	    String locatUpdateLabel = "Location:        "+ oldTask.getLocation() +"     =>     " + newTask.getLocation();
	    String startUpdateLabel = "Start:              "+ oldTask.getStart() +"     =>     " + newTask.getStart();
	    String endUpdateLabel =   "End:                 "+ oldTask.getEnd() +"     =>     " + newTask.getEnd();
	    String tagUpdateLabel =   "Tag:                 "+ oldTask.getTag() +"     =>     " + newTask.getTag();
	    String notiUpdateLabel =  "Notification:   "+ oldTask.getNotification() +"     =>     " + newTask.getNotification();
		
	    recordToLog(taskUpdateLabel);
	    recordToLog(locatUpdateLabel);
	    recordToLog(startUpdateLabel);
	    recordToLog(endUpdateLabel);
	    recordToLog(tagUpdateLabel);
	    recordToLog(notiUpdateLabel);
	   
	}
	
	private void mainGBCInit(){
		mainGBC = new GridBagConstraints();
        mainGBC.anchor = GridBagConstraints.NORTH;
        mainGBC.gridwidth = GridBagConstraints.REMAINDER;
        mainGBC.weightx = 1;
        mainGBC.weighty = 1; 
    }

	private void tasksGBCInit(){
		tasksGBC = new GridBagConstraints();		
		tasksGBC.gridwidth = GridBagConstraints.REMAINDER;
		tasksGBC.weightx = 1;
		tasksGBC.fill = GridBagConstraints.HORIZONTAL;
	}
	
}
