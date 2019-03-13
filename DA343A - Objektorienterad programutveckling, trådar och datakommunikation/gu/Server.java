package gu;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class Server {
	private ArrayList<ClientThread> al;
	private ArrayList<Message> messages;
	private ArrayList<User> users;
	private ArrayList<User> userList = new ArrayList<>();
	private HashMap<Socket,Message> unsentMessages;
	private ServerUI serverUI;
	private SimpleDateFormat sdf;
	private int port;
	private boolean running;
	private HashMap<String, Socket> clients;

	public Server(int port) {
		this(port, null);
	}

	public Server(int port, ServerUI serverUI) {
		this.serverUI = serverUI;
		this.port = port;
		sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
		al = new ArrayList<ClientThread>();
		messages = new ArrayList<Message>();
		users = new ArrayList<User>();
		clients = new HashMap<>();
		unsentMessages = new HashMap<>();
	}

	public void start() {
		running = true;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			deleteFile("files/message.dat");
			deleteFile("files/users.dat");
			display("Server started. Port: " + port + ".");
			while(running) {
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
			String message = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			display(message);
		}
	}		

	private void deleteFile(String filename) {
		File file = new File(filename);
		file.delete();
	}

	public void stop() {
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
		message.setTimeSent(time);
		serverUI.appendRoom(message);  
		for(int i=0;i<al.size();i++) {
			if(al.get(i).user.getUsername().equals(message.getSender().getUsername())) {
				ClientThread ct = al.get(i);
				if(!ct.isAlive()) System.out.println(ct.user.getUsername());
				ct.sendMessage(message);
			}
		}

	}

	public static void main(String[] args) {
		new ServerUI(1500);
	}

	private void writeMessageToFile(Message message, String filename) throws IOException {
		messages.add(message);
		try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
			oos.writeInt(messages.size());
			for(Message m:messages) {
				oos.writeObject(m);
				oos.flush();
			}
		}
	}

	private void writeUserToFile(User user, String filename) throws IOException {
		users.add(user);
		try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
			oos.writeInt(users.size());
			for(User u:users) {
				oos.writeObject(u);
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
			for (int i=0;i<n;i++) {
				message=(Message) ois.readObject();
				System.out.println(message.getTimeSent()+": "+message.getText());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void removeAllClients() {
		for(int i = al.size(); --i >= 0;) {
			serverUI.removeUsers(al.get(i).user.getUsername());
			al.removeAll(al);
		}
	}

	private class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		Message message;
		User user;
		ClientThread(Socket socket) {
			this.socket = socket;
			try {
				sInput  = new ObjectInputStream(socket.getInputStream());
				user = (User) sInput.readObject();
				System.out.println(socket.getPort()+" "+user.getUsername());
				userList.add(user);
				//				sendUserList();
				clients.put(user.getUsername(), socket);
				display(user.getUsername()+" just connected.");
				writeUserToFile(user,"files/users.dat");
				serverUI.appendUsers(user.getUsername());
			}
			catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			boolean running = true;
			while(running) {
				try {
					String time = sdf.format(new Date());
					Object obj = sInput.readObject();
					if(obj instanceof Message) {
						message = (Message) obj;
						message.setTimeRecived(time);
					}
				}
				catch (IOException e) {
					display(user.getUsername()+ " Exception reading Streams: " + e);
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
				if(message!=null) {
					switch(message.getType()) {
					case Message.MESSAGE:
						broadcast(message);
						break;
					case Message.LOGOUT:
						display(user.getUsername() + " has disconnected from the server.");
						serverUI.removeUsers(user.getUsername());
						user.setStatus(0);
						running = false;
						break;
					}
				}
			}
			close();
		}
		private void close() {
			try {
				sInput.close();
				socket.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		private void sendUserList() throws IOException {
			sOutput = new ObjectOutputStream(socket.getOutputStream());
			sOutput.writeObject(userList);
		}

		private boolean sendMessage(Message message) {
			try {
				for(User user:message.getReciverList()){
					Socket socket = clients.get(user.getUsername());
					if(!socket.isClosed()) {
						System.out.println("User is connected");
						sOutput = new ObjectOutputStream(socket.getOutputStream());
											sOutput.writeObject(userList);
						sOutput.writeObject(message);
					}
					else {
						unsentMessages.put(socket, message);
						System.out.println("User is not connected");
					}
				}
			}
			catch(IOException e) {
				display("Error sending message to " + user.getUsername()+"\n"+e.toString());
			}
			return true;
		}
	}
}

