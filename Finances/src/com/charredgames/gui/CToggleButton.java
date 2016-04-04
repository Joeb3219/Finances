package com.charredgames.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Dec 27, 2013
 */
public class CToggleButton extends CComponent{

	private static final long serialVersionUID = 4845556414398146378L;
	private ArrayList<?> options;
	private String label = null;
	private DisplayScheme displayScheme = DisplayScheme.DEFAULT;
	
	public CToggleButton(String label, ArrayList<?> opt){
		this.label = label;
		options = opt;
		mouseListener();
	}
	
	public CToggleButton(ArrayList<?> opt){
		options = opt;
		mouseListener();
	}
	
	public void setDisplayScheme(DisplayScheme scheme){
		this.displayScheme = scheme;
	}
	
	public DisplayScheme getDisplayScheme(){
		return displayScheme;
	}
	
	public Object getValue(){
		return options.get(currentIndex);
	}
	
	private void mouseListener(){
		addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				System.out.println(options.size() + " " + currentIndex);
				if(options.size() <= (currentIndex + 1)) currentIndex = 0;
				else currentIndex += 1;
				repaint();
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
	}
	
	public int getValueIndex(){
		return currentIndex;
	}
	
	public void paint(Graphics g){
		g.setFont(displayScheme.getFont());
		int width = getWidth();
		int height = getHeight();
		int textYPos = (height + g.getFontMetrics().getHeight() / 2) / 2;
		String displayVal = "";
		if(label != null) displayVal += label + ": ";
		displayVal += (options.get(currentIndex) + ""); // This may work, probably be borked.
		g.setColor(displayScheme.getBackgroundColour());
		g.fillRect(0, 0, width, height);
		g.setColor(displayScheme.getPrimaryColour());
		g.fillRect(2, 2, width - 4, height - 4);
		g.setColor(displayScheme.getTextColour());
		
		g.drawString(displayVal, (width - g.getFontMetrics().stringWidth(displayVal)) / 2, textYPos);
		
	}
	
}
