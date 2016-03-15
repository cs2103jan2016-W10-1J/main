package com.Main;

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

import java.awt.*;

public class TaskPanel extends JPanel {

	Object titleBarStr[] = { "No.", "Task", "Location", "Date", "Start", "End", "Tag", "Notification" };
	Object rowData[][] = {};
	private JTable table;
	private DefaultTableModel model;
	
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
        table.setGridColor(new Color(255, 255, 255, 125));
        table.setFillsViewportHeight( true );
    	table.setOpaque(false);
    	table.setBackground(new Color(255, 255, 255, 125));
    	table.setFont(new Font("Calibri", Font.BOLD, 15));
    	
    	JScrollPane scrollPane = new JScrollPane(table);
    	scrollPane.setOpaque(false);
    	scrollPane.getViewport().setOpaque(false);
    	
    	
        add(scrollPane);
    }
    
    public void upDateTaskList(Processor processor){
    	DefaultTableModel dm = (DefaultTableModel)table.getModel();
    	for (int i = dm.getRowCount()-1; i >= 0; i--) {
    		dm.removeRow(i);
    	}
    	processor.readFile();
    	ArrayList<Task> fileData = processor.getStorage().getTaskData();
    	//DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (int i=0; i<fileData.size(); i++){
    		Task currentTask = fileData.get(i);
        	dm.addRow(new Object[]{ String.valueOf(i+1), currentTask.getTaskName(), currentTask.getLocation(), currentTask.getDate(),currentTask.getStart(), currentTask.getEnd(), currentTask.getTag(), currentTask.getNotification()});
    	}
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
		try {
	        g.drawImage(ImageIO.read(this.getClass().getResource("background.png")), 0, 0, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

