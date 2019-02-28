package p1;

public interface MessageProducer {
	/**
	 * Returns how long the delay of the change of message will be.
	 * 
	 * @return An integer of milliseconds the delay will be.
	 */
	public int delay();
	/**
	 * Returns hoe many times producer sequence will be shown.
	 * 
	 * @return An integer of the times the producer sequence will be shown.
	 */
	public int times();
	/**
	 * Returns how many message is in the producer. 
	 * 
	 * @return An integer of the amount of messages.
	 */
	public int size();
	/**
	 * Returns the next message in the sequence.
	 * 
	 * @return The next message.
	 */
	public Message nextMessage();
	
	default void info() {
		System.out.println("times="+times()+", delay="+delay()+", size="+size()+"]");
	}
}
