package gu;

import java.net.Socket;

public class UnsentMessage extends Message{
	private String username;
	public UnsentMessage(String username, Message msg) {
		super(msg);
		this.username=username;
	}
}
