package com.charredgames.finances;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.charredgames.finances.gui.Deals;
import com.charredgames.finances.gui.DisplayPanel;
import com.charredgames.finances.gui.NewUser;
import com.charredgames.finances.user.User;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Dec 19, 2013
 */
public class Main implements ActionListener{

	public static final int _WIDTH = 750;
	public static final int _HEIGHT = 600;
	public static final String baseTitle = "CharredGames Finances";
	
	public static JFrame window;
	public static JMenuBar menuBar;
	public static User selectedUser = null;
	public static DisplayPanel displayPanel;
	private static Main main;
	
	public static void redraw(){
		if(selectedUser != null) selectedUser.save();
		Controller.sort();
		displayPanel.redraw();
		window.invalidate();
		menuBar = main.getMenuBar();
		window.setJMenuBar(menuBar);
	}
	
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equalsIgnoreCase("new_user")) new NewUser();
		else if(actionCommand.contains("user_id_")){
			if(actionCommand.contains("user_id_combined")){
				selectedUser = null;
				redraw();
				return;
			}
			int userId = Integer.parseInt(actionCommand.replace("user_id_", ""));
			selectedUser = Controller.getUserById(userId);
			redraw();
		}
		else if(actionCommand.equalsIgnoreCase("tool_deals")) new Deals();
	}
	
	private JMenuBar getMenuBar(){
		JMenuBar menu = new JMenuBar();
		JMenu m;
		JMenuItem item;
		
		m = new JMenu("File");
		item = new JMenuItem("New User");
		item.setActionCommand("new_user");
		item.addActionListener(this);
		m.add(item);
		menu.add(m);
		
		m = new JMenu("Users");
		for(User user : Controller.users){
			item = new JMenuItem(user.getFirstName() + " " + user.getLastName());
			item.setActionCommand("user_id_" + user.getId());
			item.addActionListener(this);
			m.add(item);
		}
		
		//Create a button for a "combined" user.
		item = new JMenuItem("Combined");
		item.setActionCommand("user_id_combined");
		item.addActionListener(this);
		m.add(item);
		
		menu.add(m);
		
		m = new JMenu("Tools");
		
		item = new JMenuItem("Deals");
		item.setActionCommand("tool_deals");
		item.addActionListener(this);
		m.add(item);
		
		menu.add(m);
		
		return menu;
	}
	
	public static void main(String[] args){
		main = new Main();
		displayPanel = new DisplayPanel();
		window = new JFrame();
		Controller.loadUsers(); //Load this first so menuBar can be set up.
		
		menuBar = main.getMenuBar();
		
		window.setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
		window.add(displayPanel);
		window.pack();
		window.setTitle(baseTitle);
		window.setJMenuBar(menuBar);
		window.setVisible(true);
		window.setResizable(false);
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		
		window.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent event){
				if(selectedUser == null) System.exit(0);
				int saveFirst = JOptionPane.showConfirmDialog(window, "Would you like to save before exiting?", "Closing Program", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(saveFirst == JOptionPane.YES_OPTION) selectedUser.save();
				System.exit(0);
			}
		});
		
	}
}
