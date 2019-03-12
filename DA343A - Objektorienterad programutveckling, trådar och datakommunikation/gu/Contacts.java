package gu;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Contacts extends JPanel{

	private boolean connected;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	//private JPanel panelWhole = new JPanel();
	private JPanel panelNorth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelSouth = new JPanel();
	
	private JLabel activeUsers = new JLabel("Active users");
	private JLabel yourContacts = new JLabel("Your contacts");
	
	private JButton addContact = new JButton("Add contact");
	private JButton removeContact = new JButton("Remove Contact");
	
	private JTextPane contactList = new JTextPane();
	

//	private Vector<String> list = new Vector<String>();
	private DefaultListModel<String> list = new DefaultListModel<String>();
	private JList userList = new JList(list);;



	public Contacts() {
		setLayout();
		setComponetSize();
		setListener();
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		panelNorth.add(activeUsers, BorderLayout.NORTH);
		panelCenter.add(yourContacts, BorderLayout.NORTH);
		panelCenter.add(contactList, BorderLayout.CENTER);
		panelSouth.add(addContact, BorderLayout.WEST);
		panelSouth.add(removeContact, BorderLayout.EAST);	
		userList.setEnabled(true);
		userList.setVisibleRowCount(list.size());
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelNorth.add(userList, BorderLayout.CENTER);
	}
	
	public void createFrame() {
		JFrame frame = new JFrame("Your contacts");
		frame.setPreferredSize(new Dimension(500,600));
		frame.setBounds(900,0,500,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.getContentPane().add(this);
	}

	private void setLayout() {
		setLayout(new BorderLayout());
		//panelWhole.setLayout(new BorderLayout());
		panelNorth.setLayout(new BorderLayout());
		panelCenter.setLayout(new BorderLayout());
		panelSouth.setLayout(new BorderLayout());
		
	}

	private void setComponetSize() {
		contactList.setPreferredSize(new Dimension(70,100));
		userList.setPreferredSize(new Dimension(70,100));
		addContact.setPreferredSize(new Dimension(100,20));
		removeContact.setPreferredSize(new Dimension(120,20));
	}

	private void setListener() {

	}
	
	public void displayUsers(ArrayList<User> arrayList) {
		for(User u: arrayList) {
			String userName = u.getUsername();
			list.addElement(userName);
			
		}
	}
	
	

//	public static void main(String[] args) {
//		Contacts contacts = new Contacts();
//		contacts.createFrame();
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("123");
//		list.add("fff");
//		list.add("1524");
//		contacts.displayUsers(list);
//	}
}



