package p3;

import java.util.Random;
/**
 * Create a choice for the computer
 * 
 * Date: 26/10-2018
 * @author Mattias Jönsson
 *
 */
public class SSPPlayer {	
	public String computerChoise() {
		Random rnd = new Random();
		String str = "";
		int rand = rnd.nextInt(3)+1;		
		switch(rand) {
		case 1: str = "Rock"; break;
		case 2: str = "Scissors"; break;
		case 3: str = "Paper"; break;
		}
		return str;
	}
}
