package p3;

import java.awt.*;
import javax.swing.*;

/**
 * Design the interface of a JPanel in which the
 * player sees the scores of both the player and the
 * computer. Also shows what the player and the
 * computer has chosen.  
 * 
 * Date: 26/10-2018
 * @author Mattias Jönsson
 *
 */
public class SSPViewer extends JPanel{
	private JLabel lblInfo = new JLabel("",JLabel.CENTER);
	private JLabel lblPlayerName = new JLabel("",JLabel.CENTER);
	private JLabel lblComputerName = new JLabel("",JLabel.CENTER);
	private JLabel lblPlayerPoints = new JLabel("",JLabel.CENTER);
	private JLabel lblComputerPoints = new JLabel("",JLabel.CENTER);
	private JLabel lblPlayerChoice = new JLabel("",JLabel.CENTER);
	private JLabel lblComputerChoice = new JLabel("",JLabel.CENTER);
	
	public SSPViewer() {
		setPreferredSize(new Dimension(200,200));	

		labelSetSizes();
		
		lblInfo.setText("First to 3 wins!");
		lblPlayerName.setText("Player");
		lblComputerName.setText("Computer");
	
		
		setFontSize(lblInfo, 20);
		setFontSize(lblPlayerName, 20);
		setFontSize(lblComputerName, 20);
		setFontSize(lblPlayerPoints, 20);
		setFontSize(lblComputerPoints, 20);
		setFontSize(lblPlayerChoice, 20);
		setFontSize(lblComputerChoice, 20);
		
		add(lblInfo);
		add(lblPlayerName);
		add(lblComputerName);
		add(lblPlayerPoints);
		add(lblComputerPoints);
		add(lblPlayerChoice);
		add(lblComputerChoice);
	}
	/*
	 * Sets the sizes of the labels
	 */
	public void labelSetSizes() {
		lblInfo.setPreferredSize(new Dimension(190,40));
		lblPlayerName.setPreferredSize(new Dimension(90,40));
		lblComputerName.setPreferredSize(new Dimension(90,40));
		lblPlayerPoints.setPreferredSize(new Dimension(90,40));
		lblComputerPoints.setPreferredSize(new Dimension(90,40));
		lblPlayerChoice.setPreferredSize(new Dimension(90,40));
		lblComputerChoice.setPreferredSize(new Dimension(90,40));
	}
	/*
	 * Changes the label of the player's points
	 * 
	 * @param playerPoints		the points the player has
	 */
	public void setPlayerPoints(int playerPoints) {
		lblPlayerPoints.setText(Integer.toString(playerPoints));
	}
	/*
	 * Changes the label of the computer's points
	 * 
	 * @param computerPoints	the points the computer has
	 */
	public void setComputerPoints(int computerPoints) {
		lblComputerPoints.setText(Integer.toString(computerPoints));
	}
	/*
	 * Sets the font size
	 * 
	 * @param label		the label which font will be changed
	 * @param size		the fonts size the label will be change to
	 */
	private void setFontSize(JLabel label, int size) {
		label.setFont(new Font("SanSerif", Font.PLAIN, size));
	}
	/*
	 * Shows the winner of the game
	 * 
	 * @param winner	the "name" of the winner of the game  
	 */
	public void showWinner(String winner) {		
		lblInfo.setText(winner+" wins this game!");
		setFontSize(lblInfo,15);		
	}
	/*
	 * Shows the player's and the computer's choice
	 * 
	 * @param pChoice	the choice of the player
	 * @param cChoice	the choice of the computer
	 */
	public void showChoise(String pChoice, String cChoice) {
		lblPlayerChoice.setText(pChoice);
		lblComputerChoice.setText(cChoice);
	}
	/*
	 * Empties the points and the choices for both
	 * the player and computer
	 */
	public void empty() {
		lblInfo.setText("First to 3 wins!");
		setFontSize(lblInfo,20);
		lblPlayerPoints.setText("0");
		lblComputerPoints.setText("0");
		lblPlayerChoice.setText("");
		lblComputerChoice.setText("");
	}
}
