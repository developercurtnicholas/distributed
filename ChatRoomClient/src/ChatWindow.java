import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class ChatWindow extends JFrame implements ActionListener{
	
	private JPanel chatLog;
	private JPanel msgAndSendPanel;
	private JScrollPane chatLogScrollPane;
	private JButton sendButton;
	private JTextField typeMessage;
	private Controller controller;
	private String userName = "";
	private int msgSize = 30;
	private int recur = 0;
	
	public ChatWindow(Controller c){
		controller = c;
		initializeWindow();
		buildUI();
	}
	
	public void promptForUserName(){
		while(userName == "" || userName == null){
			userName = JOptionPane.showInputDialog("Please Enter a user name");
		}
	}
	
	private void initializeWindow(){
		this.setSize(500,500);
		this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	private void buildUI(){
		
		//Log that hold all the chat messages
		chatLog = new JPanel();
		chatLog.setLayout(new BoxLayout(chatLog,BoxLayout.Y_AXIS));
		
		//Chat messge box
		typeMessage = new JTextField();
		typeMessage.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		typeMessage.addActionListener(this);
		//Send button
		sendButton = new JButton("Send");
		sendButton.addActionListener(this);
		
		//panel that hold the area to type message and the send button
		msgAndSendPanel = new JPanel();
		msgAndSendPanel.setLayout(new BoxLayout(msgAndSendPanel,BoxLayout.X_AXIS));
		msgAndSendPanel.setMaximumSize(new Dimension(500,28));
		msgAndSendPanel.add(typeMessage);
		msgAndSendPanel.add(sendButton);
		
		//Makes the chat log scrollable
		chatLogScrollPane = new JScrollPane(chatLog);
		chatLogScrollPane.setMaximumSize(new Dimension(500,470));
		chatLogScrollPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		this.add(chatLogScrollPane);
		this.add(msgAndSendPanel);

		
		this.setVisible(true);
	}
	
	public void showDialog(String msg){
		JOptionPane.showMessageDialog(this, msg);
	}
	
	//A new message has arrived update the chatlog
	public void newMessage(Message m){
		
		JPanel chatMsg = new JPanel(new BorderLayout());
		
		JLabel t = null;
		if(m.getSender().equals(userName)){
			t = new JLabel(format("You" + " : " + m.getMessage(),true));
			
			chatMsg.setMaximumSize(new Dimension(500,msgSize*recur));
			chatMsg.add(t, BorderLayout.LINE_END);
			chatLog.add(chatMsg);
			
		}else{
			t = new JLabel(format(m.getSender() + " : " + m.getMessage(),false));
		
			chatMsg.setMaximumSize(new Dimension(500,msgSize*recur));
			chatMsg.add(t, BorderLayout.LINE_START);
			chatLog.add(chatMsg);
		}
		
		chatLog.revalidate();
		chatLog.repaint();
		recur = 0;
	}
	
	private String format(String m,boolean you){
		
		
		String newString = divide(m,40);
		
		System.out.println(newString);
		
		if(you){
			return "<html><p color='green'>"+newString+"</p></html>";
		}else{
			return "<html><p color='blue'>"+newString+"</p></html>";
		}
		
	}
	
	private String divide(String m,int pos){
		
		recur++;
		//Can't be divided
		if(pos >= m.length()){
			return m;
		}else{
			m = new StringBuilder(m).insert(pos, "<br>").toString();
			return divide(m,(pos+43));
		}
		
	}
	//When the send button is pressed this method is called
	@Override
	public void actionPerformed(ActionEvent arg0) {	
		controller.sendMessage(typeMessage.getText(), userName);
		typeMessage.setText("");
	}
	
}
