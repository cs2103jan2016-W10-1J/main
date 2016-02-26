package com.Main;

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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class HomeFrame extends JFrame{
	
	// Instance attributes used in this example
	private	JPanel mainPanel;
	private	LogPanel logPanel;
	private TaskPanel taskList;
	private JTextField userInputBox;

	// Constructor of main frame
	public HomeFrame()
	{		
		setTitle( "TODO List Application" );
		setSize( 1200, 500 );
		setBackground( Color.WHITE );

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		getContentPane().add(mainPanel);

		JScrollPane scrollPanel = new JScrollPane();
		logPanel = new LogPanel();
		scrollPanel.setViewportView(logPanel);
		//mainPanel.add( scrollPanel, BorderLayout.WEST );
		mainPanel.add(scrollPanel);
		

        JPanel panel = userInputBar();
        add(panel, BorderLayout.SOUTH);
                
			
	}
	

	public void taskListInitialize(){		
		taskList = new TaskPanel();
		mainPanel.add( taskList );		
	}
	
	
	public static void main(String[] args){
		HomeFrame home = new HomeFrame();
		
		home.taskListInitialize();
		home.setVisible(true);
	}
	
	
	private JPanel userInputBar(){
    	JPanel panel = new JPanel();
        userInputBox = new JTextField(30);
        panel.add(userInputBox);
        JButton enterButton = new JButton("Enter");
        panel.add(enterButton);
        
        enterButton.addActionListener(new ActionListener() {        	
			@Override
			public void actionPerformed(ActionEvent e) {
				String userCommand = userInputBox.getText();
				String command = commandParser(userCommand);  
				switch (command.toLowerCase()){
					case "add":
						Task task = new Task(userCommand);
						taskList.addTask(task);
						logPanel.recordToLog(userCommand);
						userInputBox.setText("");
						break;
					default:
						
				}
			}			
    	});  
        
        return panel;
        
    }
    
    private String commandParser(String userCommand){  
    	if(userCommand.contains(" "))
    		return userCommand.substring(0, userCommand.indexOf(" "));    
    	else 
    		return userCommand;
    }
    
    
	
	
}