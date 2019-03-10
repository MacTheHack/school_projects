package gu;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Server implements Runnable{
	private Thread server = new Thread(this);
	private ServerSocket serverSocket;
	private ServerUI ui;
	private ClientStorage clientStorage = new ClientStorage();
	private LinkedList<CallbackInterface> list = new LinkedList<CallbackInterface>();
	private LinkedList<Socket> connectedUsers = new LinkedList<>();
	protected List<ClientHandler> clients;

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		clients = Collections.synchronizedList(new ArrayList<ClientHandler>());
		server.start();
	}

	public void run() {
		System.out.println("Server running");
		ui = new ServerUI();
		while(true) {
			try  {      
				Socket socket = serverSocket.accept();
				new ClientHandler(socket).start();
			} catch(IOException e) { 
				System.err.println(e);
			}
		}
	}

	private class ClientHandler extends Thread {
		private Socket socket;
			
		public ClientHandler(Socket socket) {
			this.socket = socket;
			connectedUsers.add(socket);
		}

		public void run() {
			Object obj;
			System.out.println("Klient uppkopplad");
			try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())	) {
				while(!Thread.interrupted()) {
					obj = ois.readObject();
					for(Socket s:connectedUsers) {
						System.out.println(s.getPort());
					}
					if(obj instanceof User) {
						System.out.println(((User)obj).getUsername());
						ui.append(obj);
					}
					if(obj instanceof Message) {
						Message m = (Message)obj;
						m.setTimeRecived(new Timestamp(System.currentTimeMillis()).toString());
						m.setTimeSent(new Timestamp(System.currentTimeMillis()).toString());
						ui.append(obj);
					}
					oos.writeObject(obj);
					oos.flush();
				}
			} catch(IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				socket.close();
			} catch(Exception e) {}
			System.out.println("Klient nerkopplad");
		}
	}
	
	public static void main(String[] args) throws IOException {
		new Server(3464);
	}
}
