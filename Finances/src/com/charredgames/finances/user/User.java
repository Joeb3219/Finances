package com.charredgames.finances.user;

import java.io.PrintWriter;
import java.util.ArrayList;

import com.charredgames.finances.Controller;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class User {

	private String firstName, lastName, gender;
	private int id, age;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	private XStream xStream;
	private String fileName = null;
	private PrintWriter writer;
	
	public User(String firstName, String lastName, int age, String gender){
		this.id = Controller.getNextUserId();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		Controller.addUser(this);
		xStream = new XStream(new StaxDriver());
		fileName = "data/" + id + ".cgf";
	}
	
	public void addTransaction(Transaction transaction){
		transactions.add(0, transaction);
		save();
	}
	
	public void save(){
		Controller.sort();
		try {
			if(fileName == null) fileName = "data/" + id + ".cgf";
			if(xStream == null) xStream = new XStream(new StaxDriver());
			writer = new PrintWriter(fileName);
			writer.println("<?xml version=\"1.0\" ?>");
			
			//Print user's class
			xStream.omitField(User.class, "xStream");
			xStream.omitField(User.class, "writer");
			String userXML = xStream.toXML(this);
			userXML = userXML.replace("<?xml version=\"1.0\" ?>", "");
			writer.println(userXML);
			
			writer.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getGender(){
		return gender;
	}
	
	public int getId(){
		return id;
	}
	
	public int getAge(){
		return age;
	}
	
	public double getTodayBalance(){
		if(transactions.size() == 0) return 0.00;
		double bal = 0.00;
		for(Transaction t : transactions){
			if(t.getDaysSince() == 0) bal += t.getValue();
		}
		return bal;
	}
	
	public double getBalance(){
		if(transactions.size() == 0) return 0.00;
		double balance = 0;
		for(Transaction trans : transactions){
			balance += trans.getValue();
		}
		return balance;
	}
}
