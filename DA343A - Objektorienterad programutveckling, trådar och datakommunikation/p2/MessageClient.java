package p2;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import p1.Message;

/**
 * Creates a client for connecting to a server 
 * and receiving Message-object from the server.
 * 
 * Date: 28/2 2019
 * @author Mattias Jönsson
 *
 */
public class MessageClient {
	private Start start = new Start(this);
	private Socket socket;
	private Connection connection;
	private String ipAddress;
	private int serverPort;
	private LinkedList<CallbackInterface> list = new LinkedList<CallbackInterface>();

	/**
	 * Constructs a MessageClient-object
	 * 
	 * @param ipAddress The IP-address to the server
	 * @param serverPort The port of the server
	 */
	public MessageClient(String ipAddress, int serverPort) {
		this.ipAddress = ipAddress;
		this.serverPort = serverPort;
		start.connected(false);
		start.connect();
	}

	
	/**
	 * Sets up a connection to the server
	 */
	public void connect() {
		if(connection == null) {
			try {
				socket = new Socket(ipAddress, serverPort);
				connection = new Connection(socket);
				connection.start();
				start.connected(true);
			} catch(IOException e) {
				System.out.println(e.toString());
				disconnect();
			}
		}
	}

	/**
	 * Disconnects from the server
	 */
	public void disconnect() {
		try {
			connection = null;
			socket.close();
		} catch(Exception e) {}
		start.connected(false);
	}

	/**
	 * Sends a Message-object
	 * 
	 * @param result The message
	 */
	private void sendMessage(final Message result) {
		for (int i=0;i<list.size();i++) {
			list.get(i).update(result);
		}
	}
	
	
	/**
	 * Adds a CallbackInterface to the list
	 * 
	 * @param c
	 */
	public void addCallback(CallbackInterface c) {
		list.add(c);
	}

	/**
	 * Create a thread to make a connection to the server
	 * 
	 * Date: 28/2 2019
	 * @author Mattias Jönsson
	 *
	 */
	private class Connection extends Thread {
		private ObjectInputStream ois;

		/**
		 * @param socket
		 * @throws IOException
		 */
		public Connection(Socket socket) throws IOException {
			ois = new ObjectInputStream(socket.getInputStream());
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			try {
				while(true) {
					sendMessage((Message)ois.readObject());
				}
			} catch(IOException | ClassNotFoundException e) {
				System.out.println(e.toString());
			}
			try {
				disconnect();
			} catch(Exception e) {}
		}
	}
}