package p1;

import java.io.*;

public class ObjectFileProducer implements MessageProducer {

	private Message[] messages;
	private int delay = 0;
	private int times = 0;
	private int size = 0;
	private int currentIndex = -1;
	
	public ObjectFileProducer(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))){
			times=ois.readInt();
			delay=ois.readInt();
			size=ois.readInt();
			messages=new Message[size];
			for(int i=0;i<size;i++) {
				messages[i]=(Message)ois.readObject();
			}
		}
	}
	
	public int delay() {
		return delay;
	}

	public int times() {
		return times;
	}

	public int size() {
		return size;
	}

	public Message nextMessage() {
		if(size()==0) return null;
		currentIndex = (currentIndex+1) % messages.length;
		return messages[currentIndex];
	}

}
