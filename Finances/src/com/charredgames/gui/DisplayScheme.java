package com.charredgames.gui;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Dec 27, 2013
 */
public class DisplayScheme {

	private Color primaryColour, backgroundColour, textColour;
	private Font font;
	
	public static DisplayScheme DEFAULT = new DisplayScheme(0xFF626262, 0xFF737373, 0xFFFFFFFF, new Font(Font.DIALOG, Font.PLAIN, 12));
	
	public DisplayScheme(int primaryColour, int backgroundColour, int textColour, Font font){
		this.primaryColour = new Color(primaryColour); //Hex value
		this.backgroundColour = new Color(backgroundColour); //Hex value
		this.textColour = new Color(textColour); //Hex value
		this.font = font;
	}
	
	public DisplayScheme(int primaryColour, int backgroundColour, int textColour){
		this.primaryColour = new Color(primaryColour); //Hex value
		this.backgroundColour = new Color(backgroundColour); //Hex value
		this.textColour = new Color(textColour); //Hex value
	}
	
	public Color getPrimaryColour(){
		return primaryColour;
	}
	
	public Color getBackgroundColour(){
		return backgroundColour;
	}
	
	public Color getTextColour(){
		return textColour;
	}
	
	public Font getFont(){
		return font;
	}
	
}
