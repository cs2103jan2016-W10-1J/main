package com.Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

public class InputBox extends JDialog{

	JDialog jDialog;
	JPanel jPanel;
	
	
	public InputBox(JFrame homeFrame){
		super(homeFrame, Dialog.ModalityType.DOCUMENT_MODAL);
		jDialog = this;
		this.setTitle("Adder");
		//jDialog.setSize(300, 200);
		jDialog.setResizable(false);
		jPanel = new JPanel(new GridBagLayout());
		jDialog.add(jPanel);
		/*addLabel("Type something");
		addCheckBox();
		addInputBox();
		addInputBox();
		pack();
		jDialog.setVisible(true);*/
	}

	// Call this when you need a CheckBox
	public void addCheckBox(){
		JCheckBox jcheck = new JCheckBox("Anchovies");
		JPanel panel = new JPanel();
        panel.add(jcheck);
        panel.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        //gbc.weightx = 1;
        //gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(panel, gbc);
	}

	// Call this when you need a InputBox
	public JTextField addTextBox(){
		JPanel panel = new JPanel();
		JTextField textField = new JTextField(20);
        panel.add(textField);
        panel.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(panel, gbc);
        
        return textField;
        
	}
	
	// Call this when you need a label
	public void addLabel(String name){
		JLabel label = new JLabel(name);
		JPanel panel = new JPanel();
        panel.add(label);
        panel.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jPanel.add(panel, gbc);
	}
	
	// Call this when you need a label
	public JButton addButton(String name){
		JButton button = new JButton(name);
		JPanel panel = new JPanel();
	    panel.add(button);
	    panel.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    //gbc.weightx = 1;
	    //gbc.weighty = 1;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    jPanel.add(panel, gbc);
	    
	    return button;
	}
	
}
