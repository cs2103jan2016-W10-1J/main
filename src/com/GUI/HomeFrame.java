//@@author A0149484R
package com.GUI;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.junit.Test;

import com.Main.Displayer;
import com.Main.Processor;
import com.Main.Task;

public class HomeFrame extends JFrame{
	
	// Instance attributes used in this example
	//private	JPanel mainPanel;

	private	LogPanel logPanel;
	private TaskPanel eventTaskList;
	private TaskPanel floatingTaskList;
	private TaskPanel deadlineTaskList;
	private TaskPanel undefinedTaskList;
	private TaskPanel doneTaskList;
	private JTextField userInputBox;
	private JSplitPane mainPanel;
	private Processor processor;
	private JButton enterButton;
	private JLabel settingLabel;
	
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command.";
	private static final String MESSAGE_COMMAND = "Command: ";
	private static final String MESSAGE_Currently_Display = "Current display setting: ";
	private static final String HTML_HEAD = "<HTML>";
	private static final String HTML_TAIL = "</HTML>";

	JTabbedPane taskPanel;
	
	// Constructor of main frame
	public HomeFrame()
	{		
		processor = new Processor();
		setTitle( "TODO List Application" );
		setSize( 1200, 500 );
		setBackground( Color.WHITE );
 
		mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainPanel.setDividerSize(2);
		getContentPane().add(mainPanel);
		logPanel = new LogPanel();		

		//scrollPaneForLog = new JScrollPane(logPanel);
		//scrollPanel.setViewportView(logPanel);
		mainPanel.setLeftComponent(logPanel);	

		taskPanel= new JTabbedPane();
		
		eventTaskList = new TaskPanel();
		taskPanel.add("Event", eventTaskList);
		floatingTaskList = new TaskPanel();
		taskPanel.add("Floating Task", floatingTaskList);
		deadlineTaskList = new TaskPanel();
		taskPanel.add("Deadline", deadlineTaskList);
		doneTaskList = new TaskPanel();
		taskPanel.add("Done Task", doneTaskList);
		
        JPanel panel = userInputBar();
		updateTaskLists();
		
		mainPanel.setRightComponent(taskPanel);	
		
        add(panel, BorderLayout.SOUTH);
                
			
	}
	

	
	
	public static void main(String[] args){
		HomeFrame home = new HomeFrame();
		home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		home.setVisible(true);
	}
	
	// The Input bar initialization 
	private JPanel userInputBar(){
    	JPanel panel = new JPanel();
        JLabel textInputLabel = new JLabel("User Command: ");
        panel.add(textInputLabel);
        userInputBox = new JTextField(30);
        panel.add(userInputBox);
        enterButton = new JButton("Enter");
        panel.add(enterButton);
        settingLabel = new JLabel(MESSAGE_Currently_Display + Displayer.getDisplayParameter());
        panel.add(settingLabel);
        
        getRootPane().setDefaultButton(enterButton);
        
        enterButton.addActionListener(new ActionListener() {        	
			@Override
			public void actionPerformed(ActionEvent e) {
				String userCommand = userInputBox.getText();
				passingCommand(userCommand);
				updateTaskLists();
			}			
    	});  
        
        return panel;
        
    }   	
	
	public List<String> passingCommand(String userCommand){
		String commandType = "";
		if (userCommand.contains(" "))
			commandType = userCommand.substring(0, userCommand.indexOf(" "));
		else 
			commandType = userCommand;
		List<String> strToDisplay = new ArrayList<String>();
		if (!userCommand.equals("") && !commandType.equals("sw") && !commandType.equals("exit")) {
			strToDisplay = processor.executeCommand(userCommand);
			//String commandType = processor.processCommand(userCommand); 
			
			logPanel.clearLog();
			logPanel.recordToLog(HTML_HEAD+LogColor.setStringInRed(MESSAGE_COMMAND) + LogColor.setStringInBlue(userCommand)+HTML_TAIL);		
			userInputBox.setText("");
		} else if (commandType.equals("sw") && userCommand.contains(" ")){
			String panelName = userCommand.substring(userCommand.indexOf(" ")+1);

			logPanel.clearLog();
			logPanel.recordToLog(HTML_HEAD+LogColor.setStringInRed(MESSAGE_COMMAND) + userCommand+HTML_TAIL);
			switch (panelName.toLowerCase()) {
				case "ev":
					taskPanel.setSelectedIndex(0);
					strToDisplay.add("Task tab switched to display Event");
					break;
				case "ft":
					taskPanel.setSelectedIndex(1);
					strToDisplay.add("Task tab switched to display Floating Task");
					break;
				case "dl":
					taskPanel.setSelectedIndex(2);
					strToDisplay.add("Task tab switched to display Deadline");
					break;
				case "dt":
					taskPanel.setSelectedIndex(3);
					strToDisplay.add("Task tab switched to display Done Task");
					break;
				default:
										
			}
			userInputBox.setText("");
		} else if (commandType.equals("exit")){
			dispose();
		}
		for (int i=0; i<strToDisplay.size(); i++) {				
			logPanel.recordToLog(HTML_HEAD+strToDisplay.get(i)+HTML_TAIL);
		} 	
		
		return strToDisplay;
	}

	
	private void updateTaskLists(){
		eventTaskList.upDateTaskList(Processor.getEventList());
		floatingTaskList.upDateTaskList(Processor.getFloatList());
		deadlineTaskList.upDateTaskList(Processor.getDeadlineList());
		doneTaskList.upDateTaskList(Processor.getDoneTaskList());
		settingLabel.setText(MESSAGE_Currently_Display + Displayer.getDisplayParameter());
	}
	
	

}
