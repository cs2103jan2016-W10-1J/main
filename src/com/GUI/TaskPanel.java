//@@author A0149484R
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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import com.Main.Processor;
import com.Main.Task;

import java.awt.*;

public class TaskPanel extends JPanel {

	Object titleBarStr[] = { "No.", "Task", "Location", "Date", "Start", "End", "Tag", "Notification", "Done?" };
	Object rowData[][] = {};
	private JTable table;
	private DefaultTableModel model;
	private String imgLink = "";
	
	//The constructor for task table
    public TaskPanel() {
        setLayout(new BorderLayout());
        setSize(new Dimension(600, 300));
    	//table = new JTable(rowData, titleBarStr);
        model = new DefaultTableModel(titleBarStr,0);
        table = new JTable(model){
        	private static final long serialVersionUID = 1L;

            @Override
                public boolean isCellEditable(int row, int column) {
                   return false;
                }
        };
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
    	//table.setShowGrid(false);
        table.setGridColor(Color.WHITE);
        table.setFillsViewportHeight( true );
    	table.setOpaque(false);
    	table.setBackground(Color.WHITE);
    	table.setFont(new Font("Calibri", Font.BOLD, 15));
    	
    	JScrollPane scrollPane = new JScrollPane(table);
    	scrollPane.setOpaque(false);
    	scrollPane.getViewport().setBackground(Color.WHITE);
    	
    	
        add(scrollPane);
    }
    
    public void upDateTaskList(ArrayList<Task> fileData){
    	DefaultTableModel dm = (DefaultTableModel)table.getModel();
    	for (int i = dm.getRowCount()-1; i >= 0; i--) {
    		dm.removeRow(i);
    	}
    	//DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (int i=0; i<fileData.size(); i++){
    		Task currentTask = fileData.get(i);    
    		String isDoneStr = "";
    		if (currentTask.isTaskDone())
    			isDoneStr = "DONE";
        	dm.addRow(new Object[]{ String.valueOf(currentTask.getTaskID()), currentTask.getTaskName(), currentTask.getLocation(), currentTask.getDate(),currentTask.getStart(), currentTask.getEnd(), currentTask.getTag(), currentTask.getNotification(), isDoneStr});
    	}
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
		try {
	        g.drawImage(ImageIO.read(this.getClass().getResource(imgLink)), 0, 0, null);  //background.png
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

