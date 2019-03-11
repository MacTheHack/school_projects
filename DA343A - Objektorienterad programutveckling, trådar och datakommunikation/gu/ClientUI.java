package gu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ClientUI extends JPanel implements ActionListener{
	private final int port = 1500;
	private final String ipAdress = "127.0.0.1";
	private Client client;
	private boolean connected;
	private User user;
	private ImageIcon profileImage;

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

	private JTextPane image = new JTextPane();

	private JTextField tf1 = new JTextField();
	private JTextPane textPaneViewer = new JTextPane();
	private JTextPane textPaneMessage = new JTextPane();

	private JLabel lblName = new JLabel("Username: ", SwingConstants.RIGHT);
	private final JButton btnNewButton = new JButton("Show message between dates");

	public ClientUI() {
		actionListener();
		setLayout();
		setPreferredSize();
//		panelWhole.add(panelNorth);
//		panelWhole.add(panelCenter);
//		panelWhole.add(panelSouth);
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		panelNorthCenter.add(btnChoose);
		panelNorthCenter.add(image);
		panelNorthCenter.add(lblName);
		panelNorthCenter.add(tf1);
		panelNorthSouth.add(btnConnect);
		panelNorthSouth.add(btnDisconnect);
		
		panelNorthSouth.add(btnNewButton);
		panelNorthSouth.add(btnShowList);
		panelNorth.add(panelNorthCenter, BorderLayout.CENTER);
		panelNorth.add(panelNorthSouth, BorderLayout.SOUTH);

		textPaneViewer.setEditable(false);
		textPaneViewer.setBackground(new Color(220,220,220));
		panelCenter.add(textPaneViewer);

		panelSouth.add(btnSend, BorderLayout.EAST);
		panelSouth.add(textPaneMessage, BorderLayout.CENTER);
		panelSouth.add(btnImage,BorderLayout.WEST);
	}
	public void actionListener() {
		btnChoose.addActionListener(this);
		btnConnect.addActionListener(this);
		btnSend.addActionListener(this);
		btnImage.addActionListener(this);
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
		tf1.setPreferredSize(new Dimension(150, 40));
	}
	public void append(Object obj) {
		if(!textPaneViewer.getText().isEmpty()) {
			textPaneViewer.setText(textPaneViewer.getText()+"\n"+obj.toString());
		}
		else 
			textPaneViewer.setText(obj.toString());
	}
	public void connectionFailed() {
		System.out.println("connection failed");
	}
	public ImageIcon resizeImage(String ImagePath, JTextPane tp){
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(tp.getWidth(), tp.getHeight(), Image.SCALE_SMOOTH);
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
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnChoose) {
			profileImage=imageChooser();
			image.insertIcon(profileImage);
		}
		if(e.getSource()==btnConnect) {
			String username = tf1.getText().trim();
			user = new User(username,profileImage);
			client = new Client(ipAdress, port, user, this);
			if(!client.start()) return;
			tf1.setEnabled(false);
			connected = true;
		}
		if(e.getSource()==btnSend) {
			if(connected) {
				client.sendMessage(new Message(Message.MESSAGE, textPaneMessage.getText(),null,user,null));
			}
		}
		if(e.getSource()==btnImage) {

		}
		if(e.getSource()==btnDisconnect) {
//			client.sendMessage(new Message(Message.MESSAGE));
			client.logout();
		}
		if(e.getSource()==btnShowList) {
			client.showUserList();
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

