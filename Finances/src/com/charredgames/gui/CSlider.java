package com.charredgames.gui;

import java.awt.Graphics;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Dec 28, 2013
 */
public class CSlider extends CComponent{

	
	private static final long serialVersionUID = 6638757119582087742L;

	private int value, minimum, maximum, interval;
	private DisplayScheme displayScheme = DisplayScheme.DEFAULT;
	
	public CSlider(int value, int minimum, int maximum, int interval){
		//TODO: Make this work for other than integers.
		this.value = value;
		this.minimum = minimum;
		this.maximum = maximum;
		this.interval = interval;
		if(maximum < minimum || value < minimum || value > maximum) throw new NullPointerException();
	}
	
	public void setDisplayScheme(DisplayScheme scheme){
		displayScheme = scheme;
	}
	
	public DisplayScheme getDisplayScheme(){
		return displayScheme;
	}
	
	public int getValue(){
		return value;
	}

	public void paint(Graphics g){
		g.setFont(displayScheme.getFont());
		int width = getWidth();
		int height = getHeight();
		int segments = maximum - minimum;
		int segmentSize = width / segments;
		int selectedSegment = segmentSize * value;
		String displayVal = value + "";
		
		g.setColor(displayScheme.getBackgroundColour());
		g.fillRect(0, 0, width, height);
		g.setColor(displayScheme.getPrimaryColour());
		g.fillRect((selectedSegment - segmentSize), 0, segmentSize, height);
		
		g.setColor(displayScheme.getTextColour());
		g.drawString(displayVal, (width - g.getFontMetrics().stringWidth(displayVal)) / 2, (height + g.getFontMetrics().getHeight() / 2) / 2);
	}
}
