package test;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.ImageIcon;
/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server. 
 * When talking from a Java Client to a Java Server a lot easier to pass Java objects, no 
 * need to count bytes or to wait for a line feed at the end of the frame
 */
public class Message implements Serializable {

	protected static final long serialVersionUID = 1112122200L;
	static final int LIST = 0, MESSAGE = 1, LOGOUT = 2;
	private int type;
	private String text;
	private ImageIcon image;
	private User sender;
	private ArrayList<User> reciverList;
	private String timeRecived;
	private String timeSent;

	public Message(int type, String text, ImageIcon image, User sender, ArrayList<User> reciverList) {
		this.text=text;
		this.image=image;
		this.sender=sender;
		this.reciverList=reciverList;
		this.type = type;
	}
	public Message(String text, ImageIcon image) {
		this.text=text;
		this.image=image;
	}
	public String getText() {
		return text;
	}
	public User getSender() {
		return sender;
	}
	public ArrayList<User> getReciverList(){
		return reciverList;
	}
	public ImageIcon getIcon() {
		return image;
	}
	public String getTimeRecived() {
		return timeRecived;
	}
	public String getTimeSent() {
		return timeSent;
	}
	public int getType() {
		return type;
	}

	public void setTimeRecived(String time) {
		timeRecived=time;
	}
	public void setTimeSent(String time) {
		timeSent=time;
	}
	public boolean imageExists() {
		if(image==null) return false;
		return true;		
	}
	public void setReciver(User user) {
		reciverList.add(user);
	}
}

