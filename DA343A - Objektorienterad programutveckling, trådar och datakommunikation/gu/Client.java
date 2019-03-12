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
			clientUI.append("Error connectiong to server:" + e);
			return false;
		}
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		clientUI.append(msg);
		try{
			oos = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException e) {
			clientUI.append("Exception creating new Input/output Streams: " + e);
			return false;
		}
		new ListenFromServer().start();
		try{
			oos.writeObject(user);
		}
		catch (IOException e) {
			clientUI.append("Exception doing login : " + e);
			disconnect();
			return false;
		}
		return true;
	}
	public void sendMessage(Message msg) {
		try {
			oos.writeObject(msg);
		}
		catch(IOException e) {
			clientUI.append("Exception writing to server: " + e);
		}
	}
	private void disconnect() {
		try { 
			if(ois != null) ois.close();
			if(oos != null) oos.close();
			if(socket != null) socket.close();
		}
		catch(Exception e) {}	
	}
	public void logout() {
		sendMessage(new Message(Message.LOGOUT));
	}
	public User[] getUserList() throws FileNotFoundException, IOException {
		User[] u = null;
		try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/users.dat")))) {
			int n = ois.readInt();
			u = new User[n];
			for (int i = 0; i < n; i++) {
				u[i]=(User) ois.readObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return u;
	}
	public Socket getSocket() {
		return socket;
	}

	private class ListenFromServer extends Thread {
		Contacts contacts = new Contacts();
		ArrayList<User> list = new ArrayList<User>();
		public void run() {
			try {
				ois  = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while(true) {
				try {
					Message message = (Message)ois.readObject();
					clientUI.append(message);
				}
				catch(IOException e) {
					clientUI.append("Server has close the connection: " + e);
					break;
				}
				catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		new ClientUI().main(args);
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
