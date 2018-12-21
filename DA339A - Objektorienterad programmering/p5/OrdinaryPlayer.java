package p5;
/**
 * Creates a player who is able to throw the dice.
 * 
 * Date: 30/11-21018
 * @author Mattias Jönsson 
 * @version 1.0 
 *
 */ 
public class OrdinaryPlayer extends Player {
	private Dice dice;
	
	/**
	 * Constructs a OrdinaryPlayer-object 
	 * Gives the player a 6-sided dice
	 * 
	 * @param name the name of the player
	 */
	public OrdinaryPlayer(String name) {
		super(name); 
		this.dice = new SimpleDice(6);
	}

	/**
	 * Constructs a OrdinaryPlayer-object
	 * 
	 * @param name the name of the player
	 * @param dice the dice used
	 */
	public OrdinaryPlayer(String name, Dice dice) {
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
		return getDice().throwDice();
	}

	/**
	 * Gets the dice-object
	 * 
	 * @return the dice
	 */
	public Dice getDice() {
		return dice;
	}

	/**
	 * Sets the dice-variable to a Dice-object
	 * 
	 * @param dice the dice to set
	 */
	public void setDice(Dice dice) {
		this.dice = dice;
	}
}
