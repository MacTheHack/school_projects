package gu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ServerUI extends JFrame implements ActionListener, WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton stopStart;
	private Server server;
	private JButton btnMessages;
	private JPanel center = new JPanel(new GridLayout(3,1));
	private int port = 1500;
	private JList userList, messageList, eventList;
	private DefaultListModel<String> userListModel, eventListModel;
	private DefaultListModel<Object> messageListModel;
	private boolean serverStarted = false;
	

	public ServerUI(int port) {
		super("Chat Server");
		server = null;
		JPanel north = new JPanel();
		btnMessages =  new JButton("Show messages between dates");
		btnMessages.addActionListener(this);
		btnMessages.setEnabled(false);
		stopStart = new JButton("Start server");
		stopStart.addActionListener(this);
		north.setLayout(new GridLayout(2, 1, 0, 0));
		north.add(stopStart);
		north.add(btnMessages);
		getContentPane().add(north, BorderLayout.NORTH);
		
		messageList();
		userList();
		eventList();
		
		center.add(new JScrollPane(messageList));
		center.add(new JScrollPane(eventList));
		center.add(new JScrollPane(userList));
		
		add(center);
		addWindowListener(this);
		setSize(400, 600);
		setVisible(true);
	}		

	public void messageList() {
		messageListModel = new DefaultListModel<Object>();
		messageListModel.addElement("Chat room.");
		messageList = new JList(messageListModel);
		messageList.setSelectionModel(new DisabledItemSelectionModel());
	}
	public void eventList() {
		eventListModel = new DefaultListModel<String>();
		eventListModel.addElement("Event log.");
		eventList = new JList(eventListModel);
		eventList.setSelectionModel(new DisabledItemSelectionModel());
	}
	public void userList() {
		userListModel = new DefaultListModel<String>();
		userListModel.addElement("Connected Users.");
		userList = new JList(userListModel);
		userList.setSelectionModel(new DisabledItemSelectionModel());
	}
	
	public void appendUsers(String str) {
		userListModel.addElement(str);
	}
	public void removeUsers(String str) {
		for(int i=0;i<userListModel.size();i++) {
			if(userListModel.get(i).equals(str)) {
				userListModel.removeElementAt(i);
			}
		}
	}
	public void appendRoom(Object obj) {
		if(obj instanceof Message) {
			Message m = (Message)obj;
			messageListModel.addElement(m.getIcon());
			messageListModel.addElement(m.getText()+" sent from "+m.getSender().getUsername()+". Sent "+m.getTimeSent());
		}
	}
	public void appendEvent(String str) {
		eventListModel.addElement(str);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==stopStart) {
			if(server != null) {
				server.stop();
				server = null;
				stopStart.setText("Start server");
				btnMessages.setEnabled(false);
				serverStarted=false;
				return;
			}	
			server = new Server(port, this);
			serverStarted=true;
			btnMessages.setEnabled(true);
			new ServerRunning().start();
			stopStart.setText("Stop server");
		}
		if(e.getSource()==btnMessages) {
			try {
				if(serverStarted)
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
			try {server.stop();
			}catch(Exception e1) {
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

	private class ServerRunning extends Thread {
		public void run() {
			server.start();     
			stopStart.setText("Start");
			appendEvent("Server stopped\n");
			server = null;
		}
	}
	private class DisabledItemSelectionModel extends DefaultListSelectionModel {
		@Override
		public void setSelectionInterval(int index0, int index1) {
			super.setSelectionInterval(-1,-1);
		}
	}
}

