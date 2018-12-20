package p3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Design the interface of a JPanel in which the
 * player interact with. Also controls which button
 * is clicked.
 * 
 * Date: 24/10-2018
 * @author Mattias Jönsson
 *
 */
public class SSPUserInput extends JPanel {
	private SSPController controller;
	private JButton btnRock = new JButton("Rock");
	private JButton btnScissors = new JButton("Scissors");
	private JButton btnPaper = new JButton("Paper");
	private JButton btnNewGame = new JButton("New Game");
	private JButton btnQuit = new JButton("Quit");
	private JPanel panelChoiceButtons = new JPanel();
	
	public SSPUserInput(SSPController controller) {
		this.controller = controller;
		this.controller.setUserInput(this);
		
		setPreferredSize(new Dimension(300,200));
		setLayout(new GridLayout(3,1));
		setButtonSizes();				
		gameButtonSettings();
		
		add(panelChoiceButtons);
		add(btnNewGame);
		add(btnQuit);
		
		isClicked();
	}
	/*
	 * Sets the sizes of each button
	 */
	private void setButtonSizes() {
		btnRock.setPreferredSize(new Dimension(93,60));
		btnScissors.setPreferredSize(new Dimension(93,60));
		btnPaper.setPreferredSize(new Dimension(93,60));
		btnNewGame.setPreferredSize(new Dimension(290,60));
		btnQuit.setPreferredSize(new Dimension(290,60));
	}
	/*
	 * Sets the settings for the game buttons
	 */
	private void gameButtonSettings() {
		panelChoiceButtons.setLayout(new GridLayout(1,3));
		panelChoiceButtons.add(btnRock);
		panelChoiceButtons.add(btnScissors);
		panelChoiceButtons.add(btnPaper);
	}
	/*
	 * For when a button is clicked	
	 */
	private void isClicked() {
		isClicked clicked = new isClicked();
		btnRock.addActionListener( clicked );
		btnScissors.addActionListener( clicked );
		btnPaper.addActionListener( clicked );
		btnNewGame.addActionListener( clicked );
		btnQuit.addActionListener( clicked );
	}
	private class isClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {				
			if( e.getSource() == btnRock ) {
				controller.userInput("Rock");
			}
			if( e.getSource() == btnScissors ) {
				controller.userInput("Scissors");
			}
			if( e.getSource() == btnPaper ) {
				controller.userInput("Paper");
			}
			if( e.getSource() == btnNewGame ) {
				controller.newGame();
			}
			if( e.getSource() == btnQuit ) {				
				controller.quit();
			}			
		}
	}
	public void setButtonsEnabled(boolean b) {
		btnRock.setEnabled(b);
		btnScissors.setEnabled(b);
		btnPaper.setEnabled(b);
	}	
}
