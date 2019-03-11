package gu;

import java.net.*;
import java.io.*;
import java.util.*;


public class Client  {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;		
	private Socket socket;
	private ClientUI clientUI;
	private String server;
	private User user;
	private int port;
	
	Client(String server, int port, User user) {
		this(server, port, user, null);
	}

	Client(String server, int port, User user, ClientUI clientUI) {
		this.server = server;
		this.port = port;
		this.user = user;
		this.clientUI = clientUI;
	}

	public boolean start() {
		try {
			socket = new Socket(server, port);
		} 
		catch(Exception e) {
			display("Error connectiong to server:" + e);
			return false;
		}
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
		try{
			ois  = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException e) {
			display("Exception creating new Input/output Streams: " + e);
			return false;
		}
		new ListenFromServer().start();
		try{
			oos.writeObject(user);
		}
		catch (IOException e) {
			display("Exception doing login : " + e);
			disconnect();
			return false;
		}
		return true;
	}
	private void display(String msg) {
		if(clientUI == null)
			System.out.println(msg);  
		else
			clientUI.append(msg + "\n");
	}
	void sendMessage(Message msg) {
		try {
			oos.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}
	private void disconnect() {
		try { 
			if(ois != null) ois.close();
			if(oos != null) oos.close();
			if(socket != null) socket.close();
		}
		catch(Exception e) {}
		if(clientUI != null) clientUI.connectionFailed();	
	}
	public void logout() {
		sendMessage(new Message(Message.LOGOUT));
	}
	public void showUserList() {
		sendMessage(new Message(Message.LIST));
	}
	
	private class ListenFromServer extends Thread {
		public void run() {
			while(true) {
				try {
					Object msg = ois.readObject();
//					if(clientUI == null) {
//						System.out.println(msg);
//						System.out.print("> ");
//					}
//					else {
//						clientUI.append(msg);
//					}
					clientUI.append(msg);
				}
				catch(IOException e) {
					display("Server has close the connection: " + e);
					if(clientUI != null) 
						clientUI.connectionFailed();
					break;
				}
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
	
//	/*
//	 * To start the Client in console mode use one of the following command
//	 * > java Client
//	 * > java Client username
//	 * > java Client username portNumber
//	 * > java Client username portNumber serverAddress
//	 * at the console prompt
//	 * If the portNumber is not specified 1500 is used
//	 * If the serverAddress is not specified "localHost" is used
//	 * If the username is not specified "Anonymous" is used
//	 * > java Client 
//	 * is equivalent to
//	 * > java Client Anonymous 1500 localhost 
//	 * are eqquivalent
//	 * 
//	 * In console mode, if an error occurs the program simply stops
//	 * when a GUI id used, the GUI is informed of the disconnection
//	 */
//	public static void main(String[] args) {
//		// default values
//		int portNumber = 1500;
//		String serverAddress = "localhost";
//		String userName = "Anonymous";
//		User user = new User(userName,null);
//
//		// depending of the number of arguments provided we fall through
//		switch(args.length) {
//			// > javac Client username portNumber serverAddr
//			case 3:
//				serverAddress = args[2];
//			// > javac Client username portNumber
//			case 2:
//				try {
//					portNumber = Integer.parseInt(args[1]);
//				}
//				catch(Exception e) {
//					System.out.println("Invalid port number.");
//					System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
//					return;
//				}
//			// > javac Client username
//			case 1: 
//				userName = args[0];
//			// > java Client
//			case 0:
//				break;
//			// invalid number of arguments
//			default:
//				System.out.println("Usage is: > java Client [username] [portNumber] {serverAddress]");
//			return;
//		}
//		// create the Client object
//		Client client = new Client(serverAddress, portNumber, user);
//		// test if we can start the connection to the Server
//		// if it failed nothing we can do
//		if(!client.start())
//			return;
//		
//		// wait for messages from user
//		Scanner scan = new Scanner(System.in);
//		// loop forever for message from the user
//		while(true) {
//			System.out.print("> ");
//			// read message from user
//			String msg = scan.nextLine();
//			// logout if message is LOGOUT
//			if(msg.equalsIgnoreCase("LOGOUT")) {
//				client.sendMessage(new Message(Message.LOGOUT, null,null,null,null));
//				break;
//			}
//			// message WhoIsIn
//			else if(msg.equalsIgnoreCase("WHOISIN")) {
//				client.sendMessage(new Message(Message.LIST, null,null,null,null));				
//			}
//			else {				// default to ordinary message
//				client.sendMessage(new Message(Message.MESSAGE, null,null,null,null));
//			}
//		}
//		// done disconnect
//		client.disconnect();	
//	}
}
