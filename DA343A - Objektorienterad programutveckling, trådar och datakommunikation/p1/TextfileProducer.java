package p1;

import java.io.*;
import javax.swing.*;

/**
 * Is used to make a producer for input from text files.
 * 
 * Date: 12/2-19
 * @author Mattias Jönsson
 *
 */
public class TextfileProducer implements MessageProducer {
	private Message[] messages;
	private int delay = 0;
	private int times = 0;
	private int currentIndex = -1;
	
	/**
	 * Constructs a TextfileProducer-object from data in a text file.
	 * 
	 * @param filename The file from which the data will be taken from.
	 * @throws UnsupportedEncodingException Exception thrown if the character encoding is not supported.
	 * @throws FileNotFoundException Exception thrown if the the file the program is trying to open do not exists.
	 * @throws IOException Exception thrown if failed or interrupted I/O operations has occurred.
	 */
	public TextfileProducer(String filename) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		try( BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8")) ) {
			times=Integer.parseInt(br.readLine());
			delay=Integer.parseInt(br.readLine());
			int size=Integer.parseInt(br.readLine());
			messages=new Message[size];
			String txt = null;
			Icon icon = null;
			for(int i=0;i<size;i++) {
				txt=br.readLine();
				icon = new ImageIcon(br.readLine());
				messages[i]= new Message(txt,icon);
			}
		}
	}

	/* (non-Javadoc)
	 * @see p1.MessageProducer#delay()
	 */
	public int delay() {
		return delay;
	}

	/* (non-Javadoc)
	 * @see p1.MessageProducer#times()
	 */
	public int times() {
		return times;
	}

	/* (non-Javadoc)
	 * @see p1.MessageProducer#size()
	 */
	public int size() {
		return (messages==null) ? 0 : messages.length;
	}

	/* (non-Javadoc)
	 * @see p1.MessageProducer#nextMessage()
	 */
	public Message nextMessage() {
		if(size()==0) return null;
		currentIndex = (currentIndex+1) % messages.length;
		return messages[currentIndex];
	}

}
