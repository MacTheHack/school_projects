package p5;

import java.util.Random;

/**
 * Creates a player with the advantage of 
 * getting one point added to the dice's 
 * number if the number is lower than maximum,
 * 50% of the time.
 * 
 * Date: 30/11-21018
 * @author Mattias Jönsson 
 * @version 1.0
 *
 */
public class Cheater extends Player {
	private Dice dice;  
	
	/**
	 * Construct a Cheater-object 
	 * 
	 * @param name the name of the player 
	 */
	public Cheater(String name) {
		super(name); 
		this.dice = new SimpleDice(6);
	}
	/**
	 * Construct a Cheater-object
	 * 
	 * @param name the name of the player
	 * @param dice the dice used
	 */
	public Cheater(String name, Dice dice) {
		super(name);
		this.dice=dice;
	}
	
	
	/**
	 * Gets a value of the dice
	 * 
	 * @return the number of the dice
	 */
	@Override
	public int throwDice() {
		int n = getDice().throwDice();
		boolean b = new Random().nextBoolean();
		if(n<dice.getSides()&&b)n=n+1;
		return n;
	}

	/**
	 * Gets the dice-object
	 *  
	 * @return a Dice-object
	 */
	public Dice getDice() {
		return dice;
	}

	/**
	 * Sets the dice-object to a SimpleDice-object
	 * 
	 * @param simpleDice the dice used
	 */
	public void setDice(SimpleDice simpleDice) {
		this.dice=simpleDice;
	}

}
