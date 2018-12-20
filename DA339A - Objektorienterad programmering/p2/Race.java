package p2;

import java.awt.*;
import java.util.Random;

/**
 * Contains the solution 
 * for "Uppgift 2c".
 * Date: 16/10-2018
 * @author Mattias Jönsson
 **/
public class Race {
	// The instance variables
	private PaintWindow window;
	private Car car1, car2;
	private int finish_line=40; 		
	private Sound sound = Sound.getSound("sound/race.mp3");

	/*
	 * Constructs a Race-object
	 * 
	 * @param window	the PaintWindow used to show the dialog
	 * @param car1		one of the cars used in the race
	 * @param car2		one of the cars used in the race
	 */
	public Race(PaintWindow window, Car car1, Car car2) {
		this.window = window;
		this.car1 = car1;
		this.car2 = car2;
	}
	/*
	 * Starts the program
	 **/
	public void action() {
		// Calling track() to show the race track and setCars() give the cars their x- and y-coordinates
		track(); 
		setCars();
		
		// Give each variable the corresponding value, i.e x1 gets car1's x-coordinate
		int x1=car1.getX(), y1=car1.getY(), x2=car2.getX(), y2=car2.getY();
		
		// Sets a boolean for the while loop to run
		boolean bool = true;
		
		// Plays the sound
		playSound();		
		
		// Runs the while loop for the cars to race
		while(bool) {				
			showCar(car1, x1, y1);
			showCar(car2, x2, y2);	
			
			//Shows the remainder of the race for each car
			window.showImage(showLeft(car1, Color.BLUE), 0, 0);
			window.showImage(showLeft(car2, Color.RED), 72, 0);
			
			// Checks whether or not the race is finished
			if(!finish(car1, car2, finish_line)) {				
				x1-=getDx();
				x2-=getDx();				
			}		
			else {
				stopSound();
				bool = false;
			}
			PaintWindow.pause(10);	
		}
	}

	/*
	 * Generate a random number between 1 and 5, and sets the speed of 
	 * the cars as that number.
	 * 
	 * @return		the speed of the car
	 **/
	private int getDx() {
		Random rnd = new Random();
		int rand = rnd.nextInt(5)+1;
		int dx = rand;
		return dx;
	}
	/*
	 * Sets the car
	 **/
	private void setCars() {
		// Declaring the variables used in the method
		int windowWidth = window.getBackgroundWidth(), car1Height = car1.getImage().getIconHeight(), 
			car1Width = car1.getImage().getIconWidth(), car2Height = car2.getImage().getIconHeight(), 
			car2Width = car2.getImage().getIconWidth();
		
		// Calls for the method moveTo in the class Car to give the cars a startvalue
		car1.moveTo(windowWidth-car1Width-25,95+car1Height);
		car2.moveTo(windowWidth-car2Width-25,215+car2Height);
	}
	/*
	 * Shows the car
	 * 
	 * @param car	what car to be shown
	 * @param x		x-position of the image in the window
	 * @param y		y-position of the image in the window 
	 **/
	private void showCar(Car car, int x, int y) {		
		car.moveTo(x, y);
		window.showImage(car.getImage(), x, y);
	}
	/*
	 * Creates a track with a green background covering the whole screen, two dark grey
	 * rectangles used as roads and a black finish-line
	 **/
	private void track() {
		int windowHeight = window.getBackgroundHeight(), windowWidth = window.getBackgroundWidth(), 
			car1Height = car1.getImage().getIconHeight(), car2Height = car2.getImage().getIconHeight();
		
		// Background
		window.fillRect(0, 0, windowWidth, windowHeight , Color.GREEN);
		
		// Road
		window.fillRect(20, 120, windowWidth-40, car1Height+20, Color.DARK_GRAY);
		window.fillRect(20, 240, windowWidth-40, car2Height+20, Color.DARK_GRAY);		
		
		// Finish line
		window.line(finish_line, 100, finish_line, 220+car2Height+50, Color.BLACK, 5);				
	}
	/*
	 * Checks whether or not one of the cars has reached the goal. In that case a boolean is returned 
	 * to stop the race.
	 * 
	 * @param car1			the blue car
	 * @param car2			the red car
	 * @param finish_line	where the finish line is
	 * @return 				boolean to see if the race is finished
	 **/
	private boolean finish(Car car1, Car car2, int finish_line) {
		boolean bool = false;
		String str="";
		//If car1 is first to finish
		if(car1.getX()<=finish_line) {
			str = "Bil1 vann";
			bool = true;
		}
		// If car2 is first to finish
		if(car2.getX()<=finish_line) {
			str = "Bil2 vann";
			bool = true;
		}
		// If it is a draw
		if( (car1.getX()<=finish_line || car2.getX()<=finish_line) && car1.getX()==car2.getX()) { 
			str= "Lika";
			bool = true;
		}		

		// Create a Text-object of who won
		Text txt = new Text(str, new Font("Monospaced",Font.BOLD,30), Color.BLACK, Color.WHITE); 
		
		// Shows the Text-object
		window.showImage(txt, 0, 52); 
		return bool;
	}
	/*
	 * Calculate how much the car has left to the finish line
	 * 
	 * @param car		the car that is used to measure the distance
	 * @param color		the color of the background
	 * @return 			the text of the winner
	 */
	private Text showLeft(Car car, Color color) {
		int x = car.getX()-finish_line;
		String str = Integer.toString(x);
		Text txt = new Text(str, new Font("Monospaced",Font.BOLD,40), Color.WHITE, color);
		return txt;
	}
	/*
	 * Starts the sound
	 */	
	private void playSound() {		
		if(sound!=null) {
			sound.play();
		}
	}
	/*
	 *  Stops the sound
	 */	
	private void stopSound() {
		if(sound!=null) {
			sound.stop();
		}
	}
}
