package p2;


public class Start {
	private MessageClient client;
	private boolean connected = false;

	public Start(MessageClient client) {
		this.client = client;
	}
	public void connected(boolean connected) {
		this.connected = connected;
	}
	
	public void connect() {
		new Connect();
	}
	
	private class Connect {
		public Connect() {
			if(!connected) {
				client.connect();
			} else {
				client.disconnect();
			}
		}
	}

	
}

