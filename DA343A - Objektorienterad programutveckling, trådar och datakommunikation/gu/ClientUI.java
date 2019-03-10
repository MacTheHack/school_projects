package chatUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatUI extends JPanel implements ActionListener{
	
	private JPanel panelNorth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelSouth = new JPanel();
	private JPanel panelWhole = new JPanel();
	
	private JButton btnChoose = new JButton("Choose");
	private JButton btnConnect = new JButton("Connect");
	private JButton btnSend = new JButton("Send");
	private JButton btnImage = new JButton("+");
	
	private JTextPane image = new JTextPane();
	
	private JTextField tf1 = new JTextField();
	private JTextField tf2 = new JTextField();
	private JTextField tf3 = new JTextField();
	
	private JLabel lblName = new JLabel("UserName: ", SwingConstants.RIGHT);

	
	public ChatUI() {
		
		btnChoose.addActionListener(this);
		btnConnect.addActionListener(this);
		btnSend.addActionListener(this);
		btnImage.addActionListener(this);
	
		setLayout(new BorderLayout());
		panelNorth.setLayout(new BorderLayout());
		panelCenter.setLayout(new BorderLayout());
		panelSouth.setLayout(new BorderLayout());
		
		btnChoose.setPreferredSize(new Dimension(80,30));
		btnChoose.setOpaque(true);
		btnConnect.setPreferredSize(new Dimension(80,30));
		btnConnect.setOpaque(true);
		btnSend.setPreferredSize(new Dimension(80,30));
		btnSend.setOpaque(true);
		btnImage.setPreferredSize(new Dimension(80,30));
		btnImage.setOpaque(true);
		
		tf1.setPreferredSize(new Dimension(150, 40));
	
		panelWhole.add(panelNorth);
		panelWhole.add(panelCenter);
		panelWhole.add(panelSouth);
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		
		panelNorth.add(btnChoose, BorderLayout.WEST);
		panelNorth.add(lblName, BorderLayout.CENTER);
		panelNorth.add(tf1, BorderLayout.EAST);
		panelNorth.add(btnConnect, BorderLayout.SOUTH);
		
		panelCenter.add(tf2);
		
		panelSouth.add(btnSend, BorderLayout.EAST);
		panelSouth.add(tf3, BorderLayout.CENTER);
		panelSouth.add(btnImage,BorderLayout.WEST);
		
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if(btnChoose == e.getSource()) {
			System.out.println("Väljer användare");
		}
		if(btnConnect == e.getSource()) {
			System.out.println("Connected to server");
		}
		if(btnSend == e.getSource()) {
			System.out.println("Message sent");
		}
		if(btnImage == e.getSource()) {
			System.out.println("Image Added");
		}
	}
	
	public static void main(String[] args) {
		ChatUI chatUI = new ChatUI();
		JFrame frame = new JFrame("Chat");
		frame.setPreferredSize(new Dimension(450, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.add(chatUI);
		
	}

	
	
	
	

}
