package com.charredgames.finances.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import com.charredgames.finances.Controller;
import com.charredgames.finances.Main;
import com.charredgames.finances.user.Transaction;
import com.charredgames.gui.CSlider;
import com.charredgames.gui.CToggleButton;

public class EditTransaction extends JFrame implements ActionListener{

	private static final long serialVersionUID = -7763155694466990470L;
	public static int _WIDTH = 600;
	public static int _HEIGHT = 200;
	private Transaction t;
	private static JSpinner amount, date, recurringFrequency;
	private static JTextField description;
	private static JButton submit, delete;
	private static CToggleButton btn;
	private static CSlider slider;
	
	public EditTransaction(final Transaction t){
		this.t = t;
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
		add(getEditor());
		pack();
		setTitle(Main.baseTitle + ": Edit Transaction");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		t.toggleEditing(true);
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				dispose();
				t.toggleEditing(false);
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println(btn.getValue());
		if(e.getActionCommand().equalsIgnoreCase("delete")) {
			if(Main.selectedUser == null) return;
			int deleteTrans = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this transaction?", "Delete Transaction " + t.getId(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(deleteTrans == JOptionPane.YES_OPTION) {
				Controller.transactions.remove(t);
				Main.selectedUser.transactions.remove(t);
				Main.redraw();
				dispose();
			}
		}
		else if(e.getActionCommand().equalsIgnoreCase("submit")){
			boolean recurring = false;
			if((Integer) recurringFrequency.getValue() > 0) recurring = true;
			t.resetValues((Double) amount.getValue(), description.getText(), recurring, ((Date) date.getValue()));
			Main.redraw();
			dispose();
		}
		t.toggleEditing(false);
	}
	
	private JPanel getEditor(){
		JPanel panel = new JPanel(new GridLayout(6, 2, 0, 5));
		TitledBorder border = new TitledBorder("Edit Transaction");
		panel.setBorder(border);
		amount = new JSpinner(new SpinnerNumberModel(t.getValue(), -100000000, 100000000, 1));
		recurringFrequency = new JSpinner(new SpinnerNumberModel(0, 0, 1024, 1));
		Calendar cal = Calendar.getInstance();
		Date time = t.getDate();
		cal.add(Calendar.YEAR, -5);
		Date earliest = cal.getTime();
		date = new JSpinner(new SpinnerDateModel(time, earliest, time, Calendar.MINUTE));
		description = new JTextField(t.getDescription(), 50);
		submit = new JButton("Submit");
		submit.setActionCommand("submit");
		submit.addActionListener(this);
		delete = new JButton("Delete");
		delete.setActionCommand("delete");
		delete.addActionListener(this);
		
		panel.add(new JLabel("Amount: "));
		panel.add(amount);
		panel.add(new JLabel("Date: "));
		panel.add(date);
		panel.add(new JLabel("Description: " ));
		panel.add(description);
		panel.add(new JLabel("Frequency (days, 0 for once): "));
		panel.add(recurringFrequency);
		ArrayList<String> list = new ArrayList<String>();
		list.add("On");
		list.add("Off");
		btn = new CToggleButton("Sound", list);
		slider = new CSlider(10, 0, 10, 1);
		panel.add(slider);
		panel.add(btn);
		//panel.add(new EmptyComponent());
		panel.add(submit);
		panel.add(delete);
		
		return panel;
	}

}
