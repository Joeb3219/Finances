package com.charredgames.finances.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import com.charredgames.finances.Controller;
import com.charredgames.finances.Main;
import com.charredgames.finances.user.Transaction;
import com.charredgames.finances.user.User;

public class DisplayPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 5404296765846237543L;

	private static JPanel leftCluster;
	private static JScrollPane transactions;
	private static JSpinner amount, date, recurringFrequency;
	private static JTextField description;
	private static JButton submit;
	
	public DisplayPanel(){
		transactions = getTransactionsPanel();
		leftCluster = getLeftCluster();
		
		setPreferredSize(new Dimension(Main._WIDTH, Main._HEIGHT));
		setLayout(new GridLayout(1, 2, 15, 1)); //Split the screen in half (vertically)
		add(leftCluster);
		add(transactions);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("submit")){
			boolean recurring = false;
			if((Integer) recurringFrequency.getValue() > 0) recurring = true;
			Transaction transaction = new Transaction((Double) amount.getValue(), description.getText(), recurring, ((Date) date.getValue()));
			Main.selectedUser.addTransaction(transaction);
			redraw();
		}
	}
	
	public void redraw(){
		removeAll();
		leftCluster = getLeftCluster();
		transactions = getTransactionsPanel();
		add(leftCluster);
		add(transactions);
		revalidate();
	}
	
	private JPanel getLeftCluster(){
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(2, 1, 0, 15));
		
		//Create the info panel
		JPanel infoSection = new JPanel(new GridLayout(6, 1));
		TitledBorder border = new TitledBorder("User Info");
		infoSection.setBorder(border);
		
		
		User user = Main.selectedUser;
		if(user != null){
			String name = "Name: " + user.getFirstName() + " " + user.getLastName() + " (id " + user.getId() + ")";
			infoSection.add(new JLabel(name));
			String balance = "Balance: $" + Controller.getFormattedNumber(user.getBalance());
			infoSection.add(new JLabel(balance));
			String today = "Today: $" + Controller.getFormattedNumber(user.getTodayBalance());
			infoSection.add(new JLabel(today));
			infoSection.add(new EmptyComponent());
		}else if(Controller.getTotalUsers() > 0){
			String name = "All users (combined, " + Controller.getTotalUsers() + " users)";
			infoSection.add(new JLabel(name));
			String balance = "Balance: $" + Controller.getFormattedNumber(Controller.getTotalBalance());
			infoSection.add(new JLabel(balance));
			String today = "Today: $" + Controller.getFormattedNumber(Controller.getTodayBalance());
			infoSection.add(new JLabel(today));
			infoSection.add(new EmptyComponent());
		}else{
			infoSection.add(new JLabel("Please select or create a user."));
		}
		panel.add(infoSection);
		
		//Create the New Transaction panel
		JPanel transactionPanel = new JPanel(new GridLayout(6, 2, 0, 5));
		border = new TitledBorder("New Transaction");
		transactionPanel.setBorder(border);
		
		if(user != null){
			amount = new JSpinner(new SpinnerNumberModel(0.00, -100000000, 100000000, 1));
			recurringFrequency = new JSpinner(new SpinnerNumberModel(0, 0, 1024, 1));
			Calendar cal = Calendar.getInstance();
			Date time = cal.getTime();
			cal.add(Calendar.YEAR, -5);
			Date earliest = cal.getTime();
			date = new JSpinner(new SpinnerDateModel(time, earliest, time, Calendar.MINUTE));
			description = new JTextField(50);
			submit = new JButton("Submit");
			submit.setActionCommand("submit");
			submit.addActionListener(this);
			
			transactionPanel.add(new JLabel("Amount: "));
			transactionPanel.add(amount);
			transactionPanel.add(new JLabel("Date: "));
			transactionPanel.add(date);
			transactionPanel.add(new JLabel("Description: " ));
			transactionPanel.add(description);
			transactionPanel.add(new JLabel("Frequency (days, 0 for once): "));
			transactionPanel.add(recurringFrequency);
			transactionPanel.add(new EmptyComponent());
			transactionPanel.add(new EmptyComponent());
			transactionPanel.add(submit);
		}else if(Controller.getTotalUsers() > 0){
			transactionPanel.add(new JLabel("Please select a user to create a transaction"));
		}else{
			transactionPanel.add(new JLabel("Please select or create a user."));
		}
		
		panel.add(transactionPanel);
		
		return panel;
	}
	
	private JScrollPane getTransactionsPanel(){
		JScrollPane panel = new JScrollPane();
		
		TitledBorder border = new TitledBorder("Transactions");
		panel.setBorder(border);
		
		JPanel pane = new JPanel(new GridLayout(0, 1, 8, 1));
		User user = Main.selectedUser;
		if(user != null){
			boolean even = false;
			for(final Transaction transaction : user.transactions){
				JPanel tPanel = new JPanel(new GridLayout(4, 1, 5, 0));
				//tPanel.setBorder(new LineBorder(Color.BLACK));
				if(even) tPanel.setBackground(new Color(204, 255, 249));
				
				tPanel.add(new JLabel("Amount: $" + Controller.getFormattedNumber(transaction.getValue())));
				tPanel.add(new JLabel("Description: " + transaction.getDescription()));
				tPanel.add(new JLabel("Reccuring: " + transaction.isRecurring()));
				tPanel.add(new JLabel("Occured " + transaction.getDaysSince() + " days ago (" + transaction.getDate() + ")"));
				
				tPanel.addMouseListener(new MouseListener(){

					public void mouseClicked(MouseEvent arg0) {
						if(!transaction.isEditing()) new EditTransaction(transaction);
					}

					public void mouseEntered(MouseEvent arg0) {
					}

					public void mouseExited(MouseEvent arg0) {
					}

					public void mousePressed(MouseEvent arg0) {
					}

					public void mouseReleased(MouseEvent arg0) {
					}
					
				});
				
				even = !even;
				pane.add(tPanel);
			}
		}else if(Controller.getTotalUsers() > 0){
			boolean even = false;
			//for(User usr : Controller.users){
				//for(final Transaction transaction : usr.transactions){
				for(final Transaction transaction : Controller.transactions){
					JPanel tPanel = new JPanel(new GridLayout(4, 1, 5, 0));
					//tPanel.setBorder(new LineBorder(Color.BLACK));
					if(even) tPanel.setBackground(new Color(204, 255, 249));
					
					tPanel.add(new JLabel("Amount: $" + Controller.getFormattedNumber(transaction.getValue())));
					tPanel.add(new JLabel("Description: " + transaction.getDescription()));
					tPanel.add(new JLabel("Reccuring: " + transaction.isRecurring()));
					tPanel.add(new JLabel("Occured " + transaction.getDaysSince() + " days ago (" + transaction.getDate() + ")"));
					
					even = !even;
					pane.add(tPanel);
				}
			//}
		}else{
			pane.add(new JLabel("Please select or create a user."));
		}
		
		panel.setViewportView(pane);
		
		return panel;
	}

}
