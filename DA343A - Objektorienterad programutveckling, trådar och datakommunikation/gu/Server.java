package gu;

import java.awt.Dimension;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Server {
	private static int uniqueId;
	private ArrayList<ClientThread> al;
	private ArrayList<Message> messages;
	private ArrayList<User> user;
	private ServerUI serverUI;
	private SimpleDateFormat sdf;
	private int port;
	private boolean running;

	public Server(int port) {
		this(port, null);
	}

	public Server(int port, ServerUI serverUI) {
		this.serverUI = serverUI;
		this.port = port;
		sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
		al = new ArrayList<ClientThread>();
		messages = new ArrayList<Message>();
	}

	public void start() {
		running = true;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			deleteMessageFile();
			while(running) {
				display("Server waiting for Clients on port " + port + ".");
				Socket socket = serverSocket.accept(); 
				if(!running)
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

	private void deleteMessageFile() {
		File file = new File("files/messages.dat");
		file.delete();
	}

	protected void stop() {
		running = false;
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
		}
	}

	private void display(Object obj) {
		String time = sdf.format(new Date()) + " " + obj;
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
		serverUI.appendRoom(messageLf);    
	}
	public synchronized void remove(int id) {
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
		Server server = new Server(portNumber);
		server.start();
	}

	private void writeToFile(Message message, String filename) throws IOException {
		messages.add(message);
		try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
			oos.writeInt(messages.size());
			for(Message m:messages) {
				oos.writeObject(m);
				oos.flush();
			}
		}
	}

	public void showMessages() throws FileNotFoundException, IOException {
		String dateFrom = JOptionPane.showInputDialog("Date from:");
		String dateTo = JOptionPane.showInputDialog("Date to:");
		Message message = null;
		try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/messages.dat")))) {
			int n = ois.readInt();
			for (int i = 0; i < n; i++) {
				message=(Message) ois.readObject();
				System.out.println(message.getTimeSent()+": "+message.getText());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		int id;
		String username, date;
		Message message;
		User user;
		ClientThread(Socket socket) {
			id = ++uniqueId;
			this.socket = socket;
			try {
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());
				user = (User) sInput.readObject();
				display(user.getUsername() + " just connected.");
				serverUI.appendUsers(user.getUsername()+"\n");
			}
			catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
			catch (ClassNotFoundException e) {
			}
			date = new Date().toString() + "\n";
		}
		public void run() {
			boolean running = true;
			while(running) {
				try {
					String time = sdf.format(new Date());
					message = (Message) sInput.readObject();
					message.setTimeRecived(time);
				}
				catch (IOException e) {
					display(username + " Exception reading Streams: " + e);
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
				switch(message.getType()) {
				case Message.MESSAGE:
					broadcast(message);
					try {
						writeToFile(message, "files/messages.dat");
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case Message.LOGOUT:
					display(username + " has disconnected from the server.");
					running = false;
					break;
				}
			}
			remove(id);
			close();
		}
		private void close() {
			try {
				sOutput.close();
				sInput.close();
				socket.close();
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

