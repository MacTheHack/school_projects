package p1;

import java.util.*;

public class MessageManager extends Observable{
	private Buffer<Message> messageBuffer;
	private Thread thread;
	

	public MessageManager(Buffer<Message> messageBuffer){
		this.messageBuffer=messageBuffer;
		
	}

	public void start() {
		if(thread==null) {
			thread = new MT();
			thread.start();	
		}
	}

	private class MT extends Thread {
		public void run() {
			Message message;
			while(!Thread.interrupted()) {
				try {
					message = messageBuffer.get();
					setChanged();
					notifyObservers(message);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}

}
