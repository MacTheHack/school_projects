package p2;

/**
 * Contains the solution 
 * for "Uppgift 2b".
 * Date: 16/10-2018
 * @author Mattias Jönsson 
 **/
public class Painting {
	// Instance variables
	private String title, painter;
	private int year;
	/*
	 * CConstructs a Painting-object
	 * 
	 * @param title		the painting's name
	 * @param painter	the name of the painter
	 * @param year		year the painting was made
	 */
	public Painting(String title, String painter, int year) {
		this.title = title;
		this.painter = painter;
		this.year = year;
	}
	/*
	 * Gets the title
	 * 
	 * @return 	the title of the painting
	 **/
	public String getTitle() {
		return title;
	}
	/*
	 * Gets the painter
	 * 
	 * @return	the name of the painter
	 **/
	public String getPainter() {
		return painter;
	}
	/*
	 * Gets the year
	 * 
	 * @return	the year the painting was made
	 **/
	public int getYear() {
		return year;
	}
	/*
	 * Returns a string in the format of "TITLE av PAINTER, YEAR"
	 * 
	 * @return	a string in a specific format
	 **/
	public String toString() {		
		return title+" av "+painter+", "+year;
	}
	
}
