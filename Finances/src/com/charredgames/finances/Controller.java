package com.charredgames.finances;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import com.charredgames.finances.user.Transaction;
import com.charredgames.finances.user.User;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class Controller {

	public static ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	public static ArrayList<User> users = new ArrayList<User>();
	
	public static void addTransaction(Transaction trans){
		transactions.add(trans);
	}
	
	public static int getNextTransactionId(){
		return transactions.size();
	}
	
	public static User getUserById(int id){
		for(User user: users){
			if(user.getId() == id) return user;
		}
		return null;
	}
	
	public static void addUser(User user){
		users.add(user);
	}
	
	public static int getNextUserId(){
		return users.size();
		//TODO: Fix this method to account for removed ids (deleting user 0, size therefore 1 less).
	}
	
	public static void loadUsers(){
		XStream xStream = new XStream(new StaxDriver());
		File dir = new File("data");
		BufferedReader br;
		int usersLoaded = 0;
		for(File file : dir.listFiles()){
			usersLoaded ++; //Keeping track.
			ArrayList<String> lines = new ArrayList<String>();
			try {
				br = new BufferedReader(new FileReader(file));
				String line;
				while(((line = br.readLine()) != null)){
					if(!(line.equalsIgnoreCase("") && line.isEmpty())) lines.add(line);
				}
				br.close();
			} catch (Exception e) {e.printStackTrace();}
			
			if(lines.size() < 2) continue; //Make sure file has XML call, a user, and a transaction
			//Now handle loading the stuff.
			
			//Create the user.
			User user = (User) xStream.fromXML(lines.get(1));
			for(Transaction t : user.transactions) {
				transactions.add(t);
				t.toggleEditing(false);
			}
			Collections.sort(user.transactions);

			addUser(user);
			Main.selectedUser = user;

			Main.redraw();
		}
		sort();
		System.out.println(usersLoaded + " user(s) loaded");
	}
	
	public static String getFormattedNumber(double num){
		DecimalFormat format = new DecimalFormat("#,##0.00");
		return format.format(num);
	}
	
	public static int getTotalUsers(){
		return users.size();
	}

	public static double getTotalBalance(){
		double bal = 0.0;
		for(User user : users){
			bal += user.getBalance();
		}
		return bal;
	}
	
	public static double getTodayBalance(){
		double bal = 0.0;
		for(User user : users){
			bal += user.getTodayBalance();
		}
		return bal;
	}

	public static void sort(){
		Collections.sort(transactions);
		for(User user : users) Collections.sort(user.transactions);
	}
}