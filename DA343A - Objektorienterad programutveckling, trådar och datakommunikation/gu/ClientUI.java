package gu;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class ClientUI extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private final int port = 1500;
	private final String ipAdress = "127.0.0.1";
	private Client client;
	private boolean connected, imageChoosed=false;
	private User user;
	private ImageIcon profileImage, img;
	private ArrayList<User> reciverList = new ArrayList<User>();
	private static Contacts contacts = new Contacts();

	private JPanel panelNorth = new JPanel();
	private JPanel panelNorthCenter = new JPanel();
	private JPanel panelNorthSouth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelSouth = new JPanel();
	private JPanel panelWhole = new JPanel();

	private JButton btnChoose = new JButton("Choose");
	private JButton btnConnect = new JButton("Connect to server");
	private JButton btnSend = new JButton("Send");
	private JButton btnImage = new JButton("+");
	private JButton btnDisconnect = new JButton("Disconnect");
	private JButton btnShowList = new JButton("Show list");
	private JButton btnMessages = new JButton("Show message between dates");
	private JButton btnContacts = new JButton("Contact list");
	
	private JTextPane image = new JTextPane();

	private JTextField tfUsername = new JTextField();
	private JList<Object> textPaneViewer;
	private JTextPane textPaneMessage = new JTextPane();
	
	private DefaultListModel<Object> messageListModel;

	private JLabel lblName = new JLabel("Username: ", SwingConstants.RIGHT);
	

	public ClientUI() {
		connected(false);
		actionListener();
		setLayout();
		setPreferredSize();
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		panelNorthCenter.add(btnChoose);
		panelNorthCenter.add(image);
		panelNorthCenter.add(lblName);
		panelNorthCenter.add(tfUsername);
		panelNorthSouth.add(btnConnect);
		panelNorthSouth.add(btnDisconnect);

		panelNorthSouth.add(btnMessages);
		panelNorthSouth.add(btnShowList);
		panelNorth.add(panelNorthCenter, BorderLayout.CENTER);
		panelNorth.add(panelNorthSouth, BorderLayout.SOUTH);

		messageListModel = new DefaultListModel<Object>();
		textPaneViewer = new JList<Object>(messageListModel);
		textPaneViewer.setSelectionModel(new DisabledItemSelectionModel());
		textPaneViewer.setBackground(new Color(220,220,220));
		panelCenter.add(new JScrollPane(textPaneViewer));

		panelSouth.add(btnSend, BorderLayout.EAST);
		panelSouth.add(textPaneMessage, BorderLayout.CENTER);
		panelSouth.add(btnImage,BorderLayout.WEST);
		panelSouth.add(btnContacts, BorderLayout.SOUTH);
		
		image.setEnabled(false);
	}
	public void actionListener() {
		btnChoose.addActionListener(this);
		btnConnect.addActionListener(this);
		btnSend.addActionListener(this);
		btnImage.addActionListener(this);
		btnDisconnect.addActionListener(this);
		btnShowList.addActionListener(this);
		btnContacts.addActionListener(this);
	}
	public void setLayout() {
		setLayout(new BorderLayout());
		panelNorthCenter.setLayout(new GridLayout(1,4));
		panelNorthSouth.setLayout(new GridLayout(0,2));
		panelNorth.setLayout(new BorderLayout());
		panelCenter.setLayout(new BorderLayout());
		panelSouth.setLayout(new BorderLayout());
	}
	public void setPreferredSize() {
		btnChoose.setPreferredSize(new Dimension(80,30));
		btnChoose.setOpaque(true);
		btnConnect.setPreferredSize(new Dimension(80,30));
		btnConnect.setOpaque(true);
		btnSend.setPreferredSize(new Dimension(80,30));
		btnSend.setOpaque(true);
		btnImage.setPreferredSize(new Dimension(80,30));
		btnImage.setOpaque(true);
		btnContacts.setPreferredSize(new Dimension(80,30));
		btnContacts.setOpaque(true);
		tfUsername.setPreferredSize(new Dimension(150, 40));
	}
	public void append(Object obj) {
		if(obj instanceof Message) {
			Message m = (Message)obj;
			messageListModel.addElement(m.getIcon());
			messageListModel.addElement(m.getText()+" sent from "+m.getSender().getUsername()+". Sent "+m.getTimeSent());
		}	
		else messageListModel.addElement(obj);
			
	}
	private void connected(boolean connected) {
		this.connected = connected;
		btnDisconnect.setEnabled(connected);
		btnConnect.setEnabled(!connected);
		btnSend.setEnabled(connected);
		btnShowList.setEnabled(connected);
		btnImage.setEnabled(connected);
		btnChoose.setEnabled(!connected);
		btnMessages.setEnabled(connected);
		textPaneMessage.setEnabled(connected);
		tfUsername.setEnabled(!connected);
	}
	private User getUser(String username) throws IOException {
		User u = null;
		try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/users.dat")))) {
			int n = ois.readInt();
			for(int i=0;i<n;i++){
				u=(User)ois.readObject();
				if(u.getUsername().equals(username)) {
					System.out.println(u.getStatus());
					return u;
				}		
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	private boolean userExists(String username) throws FileNotFoundException, IOException {
		User[] users = getUserList();
		for(User u:users) {
			if(u.getUsername().equals(username)&&u.getStatus()==1) {
				System.out.println(u.getStatus());
				return true;
			}
		}
		return false;
	}
	private User[] getUserList() throws IOException {
		User[] u = null;
		try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/users.dat")))) {
			int n = ois.readInt();
			u = new User[n];
			for(int i=0;i<n;i++){
				u[i]=(User)ois.readObject();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return u;
	}
	public ImageIcon resizeImage(String ImagePath, JTextPane tp){
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(tp.getHeight(), tp.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	private ImageIcon imageChooser() {
		ImageIcon img = null;
		JFileChooser file = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
		file.addChoosableFileFilter(filter);
		int result = file.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION){
			File selectedFile = file.getSelectedFile();
			String path = selectedFile.getAbsolutePath();
			img = resizeImage(path, image);
		}
		else if(result == JFileChooser.CANCEL_OPTION){
			System.out.println("No File Select");
		}
		return img;
	}
	public static Contacts getContacts() {
		return contacts;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnChoose) {
			profileImage=imageChooser();
			image.insertIcon(profileImage);
		}
		if(e.getSource()==btnConnect) {
			String username = tfUsername.getText().trim();
			if(!username.isEmpty()&&profileImage!=null) {
				boolean userExists = false;
				if(new File("files/users.dat").exists()) {
					try { 
						userExists(username);
						System.out.println("test");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(!userExists) {
					user = new User(username,profileImage);
				}
				else {
					try {
						user = getUser(username);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				client = new Client(ipAdress, port, user, this);
				if(!client.start()) return;
				connected(true);
			}
		}
		if(e.getSource()==btnSend) {
			if(!textPaneMessage.getText().isEmpty()) {
				try {
					for(User user:getUserList()) {reciverList.add(user);}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(imageChoosed) client.sendMessage(new Message(Message.MESSAGE, textPaneMessage.getText(),img,user,reciverList));
				else client.sendMessage(new Message(Message.MESSAGE, textPaneMessage.getText(),null,user,reciverList));
				textPaneMessage.setText("");
				textPaneMessage.repaint();
				img=null;
			}
		}
		if(e.getSource()==btnImage) {
			img=imageChooser();
			imageChoosed=true;
			textPaneMessage.insertIcon(img);
		}
		if(e.getSource()==btnDisconnect) {
			client.logout();
			connected(false);
		}
		if(e.getSource()==btnShowList) {
			try {
				User[] users = client.getUserList();
				for(User u:users) {
					System.out.println(u.getUsername());
				}
			} catch(IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == btnContacts) {
			contacts.createFrame();
		}
	}
	private class DisabledItemSelectionModel extends DefaultListSelectionModel {
		public void setSelectionInterval(int index0, int index1) {
			super.setSelectionInterval(-1,-1);
		}
	}
	public static void main(String[] args) {
		ClientUI chatUI = new ClientUI();
		JFrame frame = new JFrame("Chat");
		frame.setPreferredSize(new Dimension(450, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.getContentPane().add(chatUI);
	}
}