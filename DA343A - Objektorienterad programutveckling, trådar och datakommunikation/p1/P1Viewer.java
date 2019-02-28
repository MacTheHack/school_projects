package p1;

import java.util.*;

/**
 * Is used to make a Viewer with a MessageManger to be 
 * able to display the Message-objects by observing 
 * MassageManger.
 * 
 * @author Mattias Jönsson
 *
 */
public class P1Viewer extends Viewer{

	/**
	 * Constructs a P1Viewer object
	 * 
	 * @param msgman The MessageManger used to observe
	 * @param height The height of the viewer
	 * @param width	 The width of the viewer
	 */
	public P1Viewer(MessageManager msgman, int height, int width) {
		super(width, width);
		msgman.addObserver(new MsgObserver());
	}
	
	/**
	 * Private class used to observe 
	 * 
	 * Date: 8/2-19
	 * @author Mattias Jönsson
	 *
	 */
	private class MsgObserver implements Observer{
		public void update(Observable o, Object arg) {
			setMessage((Message) arg);
		}
	}
}
