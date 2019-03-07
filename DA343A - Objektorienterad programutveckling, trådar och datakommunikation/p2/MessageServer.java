package p2;

import java.io.*;
import java.net.*;
import java.util.*;

import p1.*;

/**
 * Creates a server to send Message-objects 
 * to a client. The server receives Message-objects
 * from a MessageManger through an Observer.
 * 
 * Date: 28/2 2019
 * @author Mattias Jönsson
 *
 */
public class MessageServer implements Runnable{
	private Thread server = new Thread(this);
	private ServerSocket serverSocket;
	private LinkedList<Message> msgList = new LinkedList<Message>();

	/**
	 * Constructs a MessageServer-object
	 * 
	 * @param messageManager 
	 * @param port The port of the server
	 * @throws IOException Exception thrown if failed or interrupted I/O operations has occurred.
	 */
	public MessageServer(MessageManager messageManager, int port) throws IOException {
		serverSocket = new ServerSocket(port);
		server.start();
		messageManager.addObserver(new MsgObserver());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while(true) {
			try  {
				Socket socket = serverSocket.accept();
				new ClientHandler(socket).start();
			} catch(IOException e) { 
				System.err.println(e);
			}
		}
	}

	/**
	 * Creates a Observer
	 * 
	 * Date: 28/2 2019
	 * @author Mattias Jönsson
	 *
	 */
	private class MsgObserver implements Observer{
		/* (non-Javadoc)
		 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
		 */
		public void update(Observable o, Object arg) {
			msgList.add((Message)arg);
		}
	}

	/**
	 * Create a thread to handle the client.
	 * 
	 * Date: 28/2 2019
	 * @author Mattias Jönsson
	 *
	 */
	private class ClientHandler extends Thread {
		private Socket socket;
		/**
		 * Sets the private socket to the socket of the client
		 * 
		 * @param socket The socket of the client
		 */
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			int i=0;
			try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
				while(!Thread.interrupted()) {
					if(!msgList.isEmpty()) {
						if(i<msgList.size()) {
							oos.writeObject(msgList.get(i++));
							oos.flush();
						}
					}
				}
			} catch(IOException e) {
				System.out.println(e.toString());
			}
			try {
				socket.close();
			} catch(IOException e) {
				System.out.println(e.toString());
			}
		}
	}
}
