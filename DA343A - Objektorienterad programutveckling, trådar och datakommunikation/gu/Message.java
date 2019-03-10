package gu;

import java.io.Serializable;
import java.util.LinkedList;
import javax.swing.ImageIcon;

public class Message implements Serializable{
	private String text;
	private ImageIcon image;
	private User sender;
	private LinkedList<User> reciverList;
	private String timeRecived;
	private String timeSent;
	
	public Message(String text, ImageIcon image, User sender, LinkedList<User> reciverList) {
		this.text=text;
		this.image=image;
		this.sender=sender;
		this.reciverList=reciverList;
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
	public LinkedList<User> getReciverList(){
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
	
	public void setTimeRecived(String time) {
		timeRecived=time;
	}
	public void setTimeSent(String time) {
		timeSent=time;
	}
	
}
