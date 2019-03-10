package gu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.GridLayout;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ServerUI {
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextPane textPane;
	private LinkedList<Message> list = new LinkedList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		start();
	}
	
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI window = new ServerUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerUI() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Server");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		
		textPane = new JTextPane();
		textPane.setBackground(Color.WHITE);
		textPane.setEditable(false);
		frame.getContentPane().add(textPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		GridLayout gbl_panel = new GridLayout(1,5);
		
		panel.setLayout(gbl_panel);
		
		JLabel lblFrom = new JLabel("From:", SwingConstants.RIGHT);
		panel.add(lblFrom);
		
		textField = new JTextField(10);
		panel.add(textField);
		
		JLabel lblTo = new JLabel("To:", SwingConstants.RIGHT);
		
		panel.add(lblTo);
		
		textField_1 = new JTextField(10);
		panel.add(textField_1);
		
		JButton btnNewButton = new JButton("Search");
		panel.add(btnNewButton);
	}
	
	public void append(Object obj) {
		if(obj instanceof Message) {
			list.add((Message) obj);
			for(Message m:list) {
				
			}
			textPane.setText(textPane.getText()+((Message)obj).getSender().getUsername()+": "+((Message)obj).getText()+((Message)obj).getTimeRecived()+"\n");
		}
		else if(obj instanceof User) {
			textPane.setText(textPane.getText()+((User)obj).getUsername()+" has joined the server!\n");
		}
		else {
			textPane.setText(obj.toString());
		}
	}
	private class CallbackClass implements CallbackInterface {
		public void update(Object result) {
			append(result);
		}
	}

}
