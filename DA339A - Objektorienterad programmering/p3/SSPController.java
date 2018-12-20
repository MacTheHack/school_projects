package p3;

/**
 * Controls what is happening in the game
 * 
 * Date: 26/10-2018
 * @author Mattias Jönsson
 *
 */
public class SSPController {
	private SSPViewer viewer; 
	private SSPPlayer computerPlayer;
	private SSPUserInput userInput;
	private int playerPoints = 0;
	private int computerPoints = 0;
	private String computerChoice = "";

	public SSPController(SSPPlayer computerPlayer, SSPViewer viewer) {
		this.viewer = viewer;
		this.computerPlayer = computerPlayer;			
		setPoints();
	}
	/*
	 * Gets the choice from the user and runs the
	 * methods for the game
	 * 
	 * @param choice	the choice of the user
	 */
	public void userInput(String choise) {
		String winner = winnerOfRound(choise);
		showChoise(choise);
		pointCounter(winner);
		winnerOfGame();			
		setPoints();
	}
	/*
	 * Check if there is a winner of the round
	 * 
	 * @return winner	the "name" of the winner of the round
	 */
	private String winnerOfRound(String pChoise) {
		String winner = "";
		if(playerPoints<3 && computerPoints<3 ) {			
			computerChoice = computerPlayer.computerChoise();
			if(pChoise=="Rock"&&computerChoice=="Scissors"){ winner = "Player";}
			else if(pChoise=="Rock"&&computerChoice=="Paper"){ winner = "Computer";}
			else if(pChoise=="Scissors"&&computerChoice=="Rock"){ winner = "Computer";}
			else if(pChoise=="Scissors"&&computerChoice=="Paper"){ winner = "Player";}
			else if(pChoise=="Paper"&&computerChoice=="Scissors"){ winner = "Computer";}
			else if(pChoise=="Paper"&&computerChoice=="Rock"){ winner = "Player";}							
		}
		return winner;
	} 
	/*
	 * Increases the points of the winner of the round with 1
	 */
	private void pointCounter(String winner) {
		if(winner=="Player") playerPoints++;
		else if(winner=="Computer") computerPoints++;		
	}
	/*
	 * Checks whether or not there is a winner of the game
	 */
	private void winnerOfGame() {		
		if(is3points(playerPoints)) {
			viewer.showWinner("Player");
			userInput.setButtonsEnabled(false);
		}
		if(is3points(computerPoints)) {
			viewer.showWinner("Computer");
			userInput.setButtonsEnabled(false);
		}
		
	}
	/*
	 * Sets the points in "SSPViewer" to what point the player and the computer has
	 */
	private void setPoints(){
		if(playerPoints<=3 && computerPoints<=3 ) {
			this.viewer.setPlayerPoints(playerPoints);
			this.viewer.setComputerPoints(computerPoints);
		}
	}
	/*
	 * Shows the choice of the player in "SSPViewer"
	 */
	private void showChoise(String pChoise) {		
		if(playerPoints<3 && computerPoints<3 ) {
			this.viewer.showChoise(pChoise, computerChoice);
		}
	}
	/*
	 * Checks if the point is 3
	 * 
	 * @param points	the points in which the method checks
	 */
	private boolean is3points(int points) {
		if(points==3) return true;
		else return false;		
	}
	/*
	 * Quits the game
	 */
	public void quit() {
		System.exit(0);
	}	
	/*
	 * Starts a new game
	 */
	public void newGame() {
		this.computerPoints = 0;
		this.playerPoints = 0;
		this.viewer.empty();
		userInput.setButtonsEnabled(true);
	}
	/*
	 * Set the UserInput
	 * 
	 * @param userInput		the class SSPUserInput
	 */
	public void setUserInput(SSPUserInput userInput) {
		this.userInput = userInput;
	}	
}
