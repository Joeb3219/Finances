package com.charredgames.finances.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.charredgames.finances.Main;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Dec 21, 2013
 */
public class Deals extends JFrame{

	public static final int _WIDTH = 700;
	public static final int _HEIGHT = 600;
	
	public Deals(){
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
		add(new JLabel("f"));
		pack();
		setTitle(Main.baseTitle + ": Deals");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	
	
}