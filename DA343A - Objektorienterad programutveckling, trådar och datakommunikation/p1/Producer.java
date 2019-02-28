package p1;

/**
 * Is used to produces Message-object for a buffer of Message-objects.
 * 
 * Date: 12/2-19
 * @author Mattias Jönsson
 *
 */
public class Producer extends Thread{
	private Buffer<MessageProducer> producerBuffer;
	private Buffer<Message> messageBuffer;
	private Worker thread;

	/**
	 * Constructs a Producer-object
	 * 
	 * @param prodBuffer A buffer containing MessageProducer-objects
	 * @param messageBuffer A buffer containing Message-objects
	 */
	public Producer(Buffer<MessageProducer> prodBuffer, Buffer<Message> messageBuffer) {
		this.messageBuffer=messageBuffer;
		this.producerBuffer=prodBuffer;
	}
	
	/* 
	 * Starts the thread
	 */
	public void start() {
		if(thread==null) {
			thread = new Worker();
			thread.start();
		}
	}
	
	/**
	 * Private class to make a thread
	 * 
	 * @author Mattias Jönsson
	 *
	 */
	private class Worker extends Thread {
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			MessageProducer prod;
			while(!Thread.interrupted()) {
				try {
					prod = producerBuffer.get();
					populate(prod);
				} catch (InterruptedException e) {break;}
			}
		}
		/**
		 * Populates the Buffer<Message> with Message-objects
		 * 
		 * @param producer A MessageProducer-object used to get a Message-object for the Buffer<Message>-object
		 * @throws InterruptedException
		 */
		private void populate(MessageProducer producer) throws InterruptedException {
			Message message;
		    for(int times=0; times<producer.times(); times++) {
		    	for(int index = 0; index<producer.size(); index++) {
		    		message = producer.nextMessage();
		    		messageBuffer.put(message);
		    		Thread.sleep(producer.delay());
		    	}
		    }
		}
	}
}
