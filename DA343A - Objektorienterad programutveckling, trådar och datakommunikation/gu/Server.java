package test;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server {
	private static int uniqueId;
	private ArrayList<ClientThread> al;
	private ServerUI serverUI;
	private SimpleDateFormat sdf;
	private int port;
	private boolean keepGoing;
	public Server(int port) {
		this(port, null);
	}

	public Server(int port, ServerUI serverUI) {
		this.serverUI = serverUI;
		this.port = port;
		sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
		al = new ArrayList<ClientThread>();
	}

	public void start() {
		keepGoing = true;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while(keepGoing) {
				display("Server waiting for Clients on port " + port + ".");
				Socket socket = serverSocket.accept(); 
				if(!keepGoing)
					break;
				ClientThread t = new ClientThread(socket);  
				al.add(t);						
				t.start();
			}
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
						tc.sInput.close();
						tc.sOutput.close();
						tc.socket.close();
					}
					catch(IOException e) {
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		catch (IOException e) {
			String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}
	}		

	protected void stop() {
		keepGoing = false;
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
		}
	}

	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		if(serverUI == null)
			System.out.println(time);
		else
			serverUI.appendEvent(time + "\n");
	}

	private synchronized void broadcast(Message message) {
		String time = sdf.format(new Date());
		String messageLf;
		message.setTimeSent(time);
		if(!message.imageExists()) {
			messageLf = message.getTimeSent()+" "+
						message.getSender().getUsername()+" "+
						message.getSender().getProfilepic().toString()+": "+
						message.getText() + "\n";
		}
		else {
			messageLf = message.getTimeSent()+" "+ 
						message.getSender().getUsername()+" "+ 
						message.getSender().getProfilepic().toString()+": "+
						message.getText() +
						message.getIcon().toString()+"\n";
		}
		if(serverUI == null) System.out.print(messageLf);
		else serverUI.appendRoom(messageLf);    
	}
	synchronized void remove(int id) {
		for(int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			if(ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}

	public static void main(String[] args) {
		int portNumber = 1500;
		switch(args.length) {
		case 1:
			try {
				portNumber = Integer.parseInt(args[0]);
			}
			catch(Exception e) {
				System.out.println("Invalid port number.");
				System.out.println("Usage is: > java Server [portNumber]");
				return;
			}
		case 0:
			break;
		default:
			System.out.println("Usage is: > java Server [portNumber]");
			return;

		}
		Server server = new Server(portNumber);
		server.start();
	}

	public void showList() {
		display("List of the users connected at " + sdf.format(new Date()) + "\n");
		for(int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			display((i+1) + ") " + ct.user.getUsername() + " since " + ct.date);
		}
	}
	
	private class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		int id;
		String username;
		Message message;
		String date;
		User user;
		ClientThread(Socket socket) {
			id = ++uniqueId;
			this.socket = socket;
			System.out.println("Thread trying to create Object Input/Output Streams");
			try {
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());
				user = (User) sInput.readObject();
				display(user.getUsername() + " just connected.");
			}
			catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
			catch (ClassNotFoundException e) {
			}
			date = new Date().toString() + "\n";
		}

		// what will run forever
		public void run() {
			boolean keepGoing = true;
			while(keepGoing) {
				try {
					String time = sdf.format(new Date());
					message = (Message) sInput.readObject();
					message.setTimeRecived(time);
					for(int i = 0; i < al.size(); ++i) {
						message.setReciver(al.get(i).user);
					}
				}
				catch (IOException e) {
					display(username + " Exception reading Streams: " + e);
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
				// the messaage part of the ChatMessage

				// Switch on the type of message receive
				switch(message.getType()) {
				case Message.MESSAGE:
					broadcast(message);
					break;
				case Message.LOGOUT:
					display(username + " has disconnected from the server.");
					keepGoing = false;
					break;
				case Message.LIST:
					writeMsg("List of the users connected at " + sdf.format(new Date()) + "\n");
					for(int i = 0; i < al.size(); ++i) {
						ClientThread ct = al.get(i);
						writeMsg((i+1) + ") " + ct.username + " since " + ct.date);
					}
					break;
				}
			}
			remove(id);
			close();
		}

		private void close() {
			try {
				if(sOutput != null) sOutput.close();
				if(sInput != null) sInput.close();
				if(socket != null) socket.close();
			}
			catch(Exception e) {}
		}

		/*
		 * Write a String to the Client output stream
		 */
		private boolean writeMsg(String msg) {
			if(!socket.isConnected()) {
				close();
				return false;
			}
			try {
				sOutput.writeObject(msg);
			}
			catch(IOException e) {
				display("Error sending message to " + username);
				display(e.toString());
			}
			return true;
		}
	}
}

