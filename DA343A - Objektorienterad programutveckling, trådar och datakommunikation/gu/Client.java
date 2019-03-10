package gu;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.SwingUtilities;



public class Client{
	private ClientUI ui;
	private Socket socket;
	private Connection connection;
	private String ipAddress;
	private int serverPort;
	private LinkedList<CallbackInterface> list = new LinkedList<CallbackInterface>();

	public Client(String ipAddress, int serverPort) {
		this.ipAddress = ipAddress;
		this.serverPort = serverPort;
		ui = new ClientUI(this);
	}

	public void connect() {
		if(connection == null) {
			try {
				socket = new Socket(ipAddress, serverPort);
				connection = new Connection(socket);
				connection.start();
			} catch(IOException e) {
				disconnect();
			}
		}
	}

	public void disconnect() {
		try {
			connection = null;
			socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void send(Object obj) {
		if(connection!=null) {
			connection.send(obj);
		}
	}

	private void setResult(final Object obj) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(obj instanceof User) {
					ui.setResult(((User)obj).getUsername()+" has joined the server!");
				}
				else if(obj instanceof Message) {
					ui.setResult(((Message)obj).getSender().getUsername()+": "+((Message)obj).getText()+((Message)obj).getTimeSent());
				}
				else {
					System.out.println("Error");
				}
			}
		});
	}

	private class Connection extends Thread {
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private Object result;

		public Connection(Socket socket) throws IOException {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		}

		public void send(Object obj) {
			try {
				System.out.println(obj.toString());
				oos.writeObject(obj);
				oos.flush();
			} catch(IOException e) {}
		}

		public void run() {
			try {
				while(!Thread.interrupted()) {
					result = ois.readObject();
					setResult(result);
				}
			} catch(IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				disconnect();
			} catch(Exception e) {}
		}
	}

	public static void main(String[] args) {
		new Client("127.0.0.1" , 3464);
	}
}