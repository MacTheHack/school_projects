package p2;
import java.util.Calendar;

/**
 * Contains the solution 
 * for "Uppgift 2b".
 * Date: 16/10-2018
 * @author Mattias Jönsson
 **/
public class Time {
	// Instance variable
	private Calendar cal;
	private int hour;
	private int min;
	private int sec;
	/*
	 * Constructs a Time-object
	 */
	public Time() {
		update();
	} 
	
	/*
	 * Gets the hour of the day
	 * 
	 * @return	hours
	 **/
	public int getHour() {
		return this.hour;
	}
	
	/*
	 * Gets the minute of the hour
	 * 
	 * @return	minutes
	 **/
	public int getMinute() {
		return this.min;
	}
	
	/*
	 * Gets the second of the minute
	 * 
	 * @return	seconds
	 **/
	public int getSecond() {	
		return this.sec;
	}
	
	/*
	 * Returns a string in the format "HH:MM:SS"
	 * 
	 * @return a string in a specific format
	 **/
	public String toString() {
		return addZero(this.hour)+":"+addZero(this.min)+":"+addZero(this.sec);
	}
	
	/*
	 * Adds a 0 if it is a one-digit number
	 * 
	 * @return	the two-digit number
	 **/
	private String addZero(int t) {
		String str=Integer.toString(t); // Convert int to String
		if( str.length()==1) {str = "0"+t;} 	
		return str;
	}
	/*
	 * Updates
	 **/
	public void update() {
		this.cal = Calendar.getInstance();		
		this.hour = cal.get(Calendar.HOUR_OF_DAY);
		this.min = cal.get(Calendar.MINUTE);
		this.sec = cal.get(Calendar.SECOND);
	}
}
