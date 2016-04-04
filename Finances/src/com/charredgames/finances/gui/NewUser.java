package com.charredgames.finances.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import com.charredgames.finances.Main;
import com.charredgames.finances.user.Transaction;
import com.charredgames.finances.user.User;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Dec 19, 2013
 */
public class NewUser extends JFrame implements ActionListener{

	private static final long serialVersionUID = 3861942501269789735L;

	private static final int _WIDTH = 500;
	private static final int _HEIGHT = 400;
	
	private JPanel namePanel, infoPanel, submitPanel;
	private JTextField firstName, lastName;
	private JComboBox gender;
	private JSpinner age, startingBal;
	
	public NewUser(){
		namePanel = getNamePanel(); //Get First, Last name.
		infoPanel = getInfoPanel(); //Get age, gender, starting balance.
		submitPanel = getSubmitPanel(); //Submit button.
		
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
		setLayout(new GridLayout(6, 0)); //Create a 6-layer (vertically) grid.
		add(namePanel);
		add(infoPanel);
		add(new EmptyComponent()); //Add two empty components to add space between panels and submit.
		add(new EmptyComponent());
		add(submitPanel);
		pack();
		setTitle(Main.baseTitle + ": New User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("submit")){
			User user = new User(firstName.getText(),lastName.getText(), (Integer) age.getValue(), gender.getSelectedItem().toString());
			int startingBalance = (Integer) startingBal.getValue();
			if(startingBalance > 0){
				Transaction transaction = new Transaction(startingBalance, "Initial Balance", false);
				user.addTransaction(transaction);
			}
			Main.selectedUser = user;
			Main.redraw();
			dispose();
		}
	}
	
	private JPanel getNamePanel(){
		JPanel panel = new JPanel();
		TitledBorder border = new TitledBorder("Name");
		
		panel.setLayout(new GridLayout(1, 2));
		
		JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		//Row 1
		row.add(new JLabel("First:"));
		firstName = new JTextField(21);
		row.add(firstName);
		panel.add(row);
		row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		//Row 2
		row.add(new JLabel("Last:"));
		lastName = new JTextField(21);
		row.add(lastName);
		panel.add(row);
		//Finished
		
		panel.setBorder(border);
		
		return panel;
	}

	private JPanel getInfoPanel(){
		JPanel panel = new JPanel();
		age = new JSpinner(new SpinnerNumberModel(21, 0, 144, 1));
		startingBal = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 1));
		String[] genders = {"Male", "Female", "Robot"};
		gender = new JComboBox(genders);
		TitledBorder border = new TitledBorder("Info");
		
		panel.setLayout(new GridLayout(1, 5));
		
		JPanel row = new JPanel(new GridLayout(2, 1));
		
		//Row 1
		row.add(new JLabel("Gender:"));
		row.add(gender);
		panel.add(row);
		row = new JPanel(new GridLayout(2, 1));
		
		panel.add(new EmptyComponent());
		
		row.add(new JLabel("Starting Bal:"));
		row.add(startingBal);
		panel.add(row);
		row = new JPanel(new GridLayout(2, 1));
		
		panel.add(new EmptyComponent());
		
		//Row 3
		row.add(new JLabel("Age:"));
		row.add(age);
		panel.add(row);
		//Finished
		
		panel.setBorder(border);
		
		return panel;
	}

	private JPanel getSubmitPanel(){
		JPanel panel = new JPanel();
		JButton button;
		
		panel.setLayout(new GridLayout(2, 1));

		panel.add(new EmptyComponent());

		JPanel row = new JPanel(new GridLayout(1, 5));
		row.add(new EmptyComponent());
		row.add(new EmptyComponent());
		
		button = new JButton("Submit");
		button.setActionCommand("Submit");
		button.addActionListener(this);
		row.add(button);

		row.add(new EmptyComponent());
		row.add(new EmptyComponent());

		panel.add(row);
		
		//panel.add(new EmptyComponent());
		
		return panel;
	}

}