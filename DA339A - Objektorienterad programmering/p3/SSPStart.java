package p3;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Is used to start up the game 
 * 
 * Date: 24/10-2018
 * @author Mattias Jönsson
 *
 */
public class SSPStart {
	public static void main( String[] args ) {
		SSPPlayer player = new SSPPlayer();
		SSPViewer viewer = new SSPViewer();
		SSPController controller = new SSPController(player, viewer);		
		SSPUserInput userInput = new SSPUserInput(controller);
		
		JFrame frame1 = new JFrame( "SSPViewer" );
		frame1.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame1.add( viewer );
		frame1.pack();
		frame1.setVisible( true );
		
		JFrame frame2 = new JFrame( "SSPUserInput" );
		frame2.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame2.add( userInput );
		frame2.pack();
		frame2.setVisible( true );			
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)Math.round((screenSize.getWidth()/2)-(frame1.getWidth()*1.5));
		int y = (int)Math.round((screenSize.getHeight()/2)-(frame1.getHeight()/1.5));
		frame1.setLocation(x, y);
		frame2.setLocation(frame1.getX()+frame1.getWidth(), frame1.getY());
	}
}
