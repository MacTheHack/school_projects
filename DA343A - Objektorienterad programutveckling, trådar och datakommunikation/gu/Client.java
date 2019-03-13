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
		ArrayList<User> list = new ArrayList<User>();
		public void run() {
			try {
				ois  = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while(true) {
				try {
					Object obj = ois.readObject();
					if(obj instanceof ArrayList) {
						System.out.println(user.getUsername()+" new contact connected");
						ClientUI.getContacts().displayUsers((ArrayList<User>) obj);
					}
					else if(obj instanceof Message) {
						clientUI.append((Message)obj);
					}
					
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
}
