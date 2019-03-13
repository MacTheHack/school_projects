package gu;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Contacts extends JPanel implements ActionListener{
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	private JPanel panelNorth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelSouth = new JPanel();

	private JLabel activeUsers = new JLabel("Active users");
	private JLabel yourContacts = new JLabel("Your contacts");

	private JButton addContact = new JButton("Add contact");
	private JButton removeContact = new JButton("Remove Contact");
	private JButton send = new JButton("Send to selected contacts");

	private JTextPane contactList = new JTextPane();


	//	private Vector<String> list = new Vector<String>();
	private DefaultListModel<String> list;
	private JList userList;

	private ArrayList<User> usersList;

	
	private ClientUI clientUI;
	
	public Contacts(ClientUI clientUI) {
		list = new DefaultListModel<String>();
		userList = new JList(list);
		
		this.clientUI=clientUI;

		userList.setEnabled(true);
		userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		setLayout();
		setComponetSize();
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		panelNorth.add(activeUsers, BorderLayout.NORTH);
		panelCenter.add(yourContacts, BorderLayout.NORTH);
		panelCenter.add(contactList, BorderLayout.CENTER);
		panelSouth.add(addContact, BorderLayout.WEST);
		panelSouth.add(send, BorderLayout.CENTER);
		panelSouth.add(removeContact, BorderLayout.EAST);	
		panelNorth.add(userList, BorderLayout.CENTER);
		
		send.addActionListener(this);
	}

	public void createFrame() {
		JFrame frame = new JFrame("Your contacts");
		frame.setPreferredSize(new Dimension(500,600));
		frame.setBounds(900,0,500,600);
		frame.setVisible(true);
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
	public void displayUsers(ArrayList<User> arrayList) {
		usersList=arrayList;
		for(User u: arrayList) {
			String userName = u.getUsername();
			list.addElement(userName);
		}
	}
	public void test(ArrayList<String> alist) {
		for(String u : alist) {
			list.addElement(u);
		}
	}

	private ArrayList<User> selectedItem() {
		int[] selectedIx = userList.getSelectedIndices();
		ArrayList<User> selectedUsers = new ArrayList<>();
		for (int i = 0; i < selectedIx.length; i++) {
			Object sel = userList.getModel().getElementAt(selectedIx[i]);
			for(User u:usersList) {
				if(sel.equals(u.getUsername()))
					selectedUsers.add((User) u);
			}
		}
		return selectedUsers;
	}

//	public static void main(String[] args) {
//		Contacts contacts = new Contacts();
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("123");
//		list.add("fff");
//		list.add("1524");
//		contacts.test(list);
//		contacts.createFrame();
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==send) {
			clientUI.setReciverList(selectedItem());
		}
	}
}



