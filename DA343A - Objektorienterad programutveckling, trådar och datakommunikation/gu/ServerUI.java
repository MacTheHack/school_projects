package gu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ServerUI extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private JButton stopStart;
	private JTextArea chat, event, users;
	private Server server;
	private JButton btnMessages;
	private JPanel center = new JPanel(new GridLayout(3,1));
	private int port = 1500;

	public ServerUI(int port) {
		super("Chat Server");
		server = null;
		JPanel north = new JPanel();
		btnMessages =  new JButton("Show messages between dates");
		btnMessages.addActionListener(this);
		stopStart = new JButton("Start server");
		stopStart.addActionListener(this);
		north.setLayout(new GridLayout(2, 1, 0, 0));
		north.add(stopStart);
		north.add(btnMessages);
		add(north, BorderLayout.NORTH);
		chat = new JTextArea(80,80);
		event = new JTextArea(80,80);
		users = new JTextArea(80,80);
		chat.setEditable(false);
		event.setEditable(false);
		users.setEditable(false);
		appendRoom("Chat room.\n");
		appendEvent("Events log.\n");
		appendUsers("Connected Users.\n");
		center.add(new JScrollPane(chat));
		center.add(new JScrollPane(event));
		center.add(new JScrollPane(users));
		add(center);
		addWindowListener(this);
		setSize(400, 600);
		setVisible(true);
	}		

	public void appendUsers(String str) {
		users.append(str);
		chat.setCaretPosition(chat.getText().length() - 1);
	}
	public void appendRoom(String str) {
		chat.append(str);
		chat.setCaretPosition(chat.getText().length() - 1);
	}
	public void appendEvent(String str) {
		event.append(str);
		event.setCaretPosition(chat.getText().length() - 1);

	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==stopStart) {
			if(server != null) {
				server.stop();
				server = null;
				stopStart.setText("Start server");
				return;
			}	
			server = new Server(port, this);
			new ServerRunning().start();
			stopStart.setText("Stop server");
		}
		if(e.getSource()==btnMessages) {
			try {
				server.showMessages();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] arg) {
		new ServerUI(1500);
	}

	public void windowClosing(WindowEvent e) {
		if(server != null) {
			try {
				server.stop();
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
			server = null;
		}
		dispose();
		System.exit(0);
	}
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	class ServerRunning extends Thread {
		public void run() {
			server.start();     
			stopStart.setText("Start");
			appendEvent("Server crashed\n");
			server = null;
		}
	}

}

