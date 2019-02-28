package p2;

import java.io.*;
import java.net.*;

import p1.ArrayProducer;
import p1.MessageProducerInput;

public class MessageProducerServer extends Thread {
	private MessageProducerInput mpInput;
	private int port;
	
	public MessageProducerServer(MessageProducerInput mpInput, int port) {
		this.mpInput=mpInput;
		this.port=port;
	}
	public void startServer() {
		start();
	}
	public void run() {
		Object request;
		try(ServerSocket serverSocket = new ServerSocket(port)) {
			while(true) {
				try(Socket socket = serverSocket.accept();
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()) ) {
					request = ois.readObject();
					if(request instanceof ArrayProducer) mpInput.addMessageProducer(((ArrayProducer)request));
					if(request instanceof ShowGubbe) mpInput.addMessageProducer(((ShowGubbe)request));
					
					oos.writeObject(request);
					oos.flush();
				} catch(Exception e) {
					System.err.println("Server: " + e.toString());
				}
			}
		} catch(IOException e) {
			System.err.println(e.toString()+"\n");
			System.out.println("Server down");
		}
	}
}
