package p5;
import java.util.Random;

/**
 * Creates an dice which can give 
 * completely different outcomes 
 * for different numbers of dots.
 * 
 * Date: 30/11-21018
 * @author Mattias Jönsson 
 * @version 1.0
 *
 */
public class ProbabilityDice implements Dice {
	private int[] probl, newArr = new int[100];
	private int sum=0;	

	/**
	 * Constructs a ProbabilityDice-object
	 * 
	 * @param probl an array with the probability for each dot
	 */
	public ProbabilityDice(int[] probl) {
		this.probl=probl;		
		if(getSides()==0)throw new BadProbabilityException("Antal sidor = "+probl.length);
		for(int i:probl)sum+=i;
		if(sum!=100)throw new BadProbabilityException("Total sannolikhet måste vara 100: "+sum);
		problArray();
	}
	
	/**
	 * Creates an array with the percentage of dots 
	 * specified by the instanced array
	 */
	private void problArray() {
		int index=0;
		for(int i=0;i<getSides();i++)
			for(int j=0;j<probl[i];j++) {
				newArr[index]=i+1;
				index++;
			}
	}
	
	/**
	 * Gets a value of the dice
	 * 
	 * @return the number of the dice
	 */
	@Override
	public int throwDice() {
		return newArr[new Random().nextInt(newArr.length)]; 	
	}
	
	/**
	 * Gets a number of sides of the dice
	 * 
	 * @return the number of sides
	 */
	@Override
	public int getSides() {
		return probl.length;
	}
}
