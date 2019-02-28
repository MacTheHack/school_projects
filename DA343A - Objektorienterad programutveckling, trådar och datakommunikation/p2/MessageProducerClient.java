package p2;

import java.io.*;
import java.net.*;
import p1.ArrayProducer;
import p1.TextfileProducer;

public class MessageProducerClient {
	private String ip;
	private int port;
	
	public MessageProducerClient(String ip, int port) throws UnknownHostException, IOException, ClassNotFoundException {
		this.ip=ip;
		this.port=port;
		
	}
	
	public void send(TextfileProducer textfileProducer) throws IOException, ClassNotFoundException {
		new Connection(textfileProducer).start();
	}

	public void send(ArrayProducer arrayProducer) throws IOException, ClassNotFoundException {
		new Connection(arrayProducer).start();
	}

	public void send(ShowGubbe showGubbe) {
		new Connection(showGubbe).start();
	}
	
	private class Connection extends Thread {
		private Object obj;
		
		public Connection(Object obj ) {
			this.obj=obj;
		}
		public void run() {
			try (Socket socket = new Socket(ip,port); 
				 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()) ){
				oos.writeObject(obj);
				oos.flush();
				ois.readObject();
			} catch(IOException | ClassNotFoundException e) { 
				System.out.println(e.toString());
			}
		}
	}

}
