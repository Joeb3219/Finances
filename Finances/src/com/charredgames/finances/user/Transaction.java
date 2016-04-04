package com.charredgames.finances.user;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.charredgames.finances.Controller;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Dec 19, 2013
 */
public class Transaction implements Comparable<Transaction>{

	private int id;
	private double value;
	private String description;
	private Date date;
	private boolean recurring, isEditing = false;

	public Transaction(double value, String description, boolean recurring){
		this.id = Controller.getNextTransactionId();
		this.value = value;
		this.description = description;
		this.recurring = recurring;
		date = new Date();
	}
	
	public Transaction(double value, String description, boolean recurring, Date date){
		this.id = Controller.getNextTransactionId();
		this.value = value;
		this.description = description;
		this.recurring = recurring;
		this.date = date;
	}
	
	public void resetValues(double value, String description, boolean recurring, Date date){
		this.value = value;
		this.description = description;
		this.recurring = recurring;
		this.date = date;
	}
	
	public int getId(){
		return id;
	}
	
	public double getValue(){
		return value;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Date getDate(){
		return date;
	}
	
	public int getDaysSince(){
		Date now = new Date();
		long miliseconds = now.getTime() - date.getTime();
		return (int) TimeUnit.MILLISECONDS.toDays(miliseconds);
	}
	
	public boolean isRecurring(){
		return recurring;
	}
	
	public String getDisplayValue(){
		return "" + value;
	}
	
	public boolean isEditing(){
		return isEditing;
	}
	
	public void toggleEditing(boolean val){
		isEditing = val;
	}


	public int compareTo(Transaction t) {
		if (getDate() == null || t.getDate() == null) return 0;
		return t.getDate().compareTo(getDate());
	}
	
}
