package p5;
import java.util.Random;

/**
 * Creates a simple dice.
 * 
 * Date: 30/11-21018
 * @author Mattias Jönsson 
 * @version 1.0
 */
public class SimpleDice implements Dice {
	private int sides; 
	
	/**
	 * Construct a SimpleDice-object 
	 * Throws a NegativeSidesException if the 
	 * parameter is lower than 1
	 * 
	 * @param sides Number of sides on the dice
	 */
	public SimpleDice(int sides) { 
		if(sides<=0) throw new NegativeSidesException("Tärningen måste ha minst 1 sida: "+sides);
		else this.sides=sides;
	}
	
	/**
	 * If the parameter is empty SimpleDice-object
	 * call for the other construct with 6 as parameter
	 */
	public SimpleDice() {
		this(6); 
	}
	
	/**
	 * Gets a value of the dice
	 * 
	 * @return the number of the dice
	 */
	@Override
	public int throwDice() {
		return new Random().nextInt(this.sides)+1;
	}
	
	/**
	 * Gets a number of sides of the dice
	 * 
	 * @return the number of sides
	 */
	@Override
	public int getSides() {
		return this.sides;
	}

}
