import java.awt.Window;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Model {
	
	Registry registry;
	ChatInterface chat;
	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Controller controller;
	
	public Model(Controller c){
		this.controller = c;
	}
	
	public void waitForMessages(){
		while(true){
			try {
				boolean newMessages = (Boolean)in.readObject();
				
				if(newMessages){
					controller.addLast(chat.getLast());
				}
				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
	
	public boolean connect(){
		setUpRegistry();
		try {
			s = new Socket("localhost",100);
			setUpStreams();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		//Start waiting for messages from the server
		new Thread(new Runnable(){
			@Override
			public void run() {
				waitForMessages();
			}
			
		}).start();
		return true;
	}
	private void setUpStreams(){
		try {
			out = new ObjectOutputStream(s.getOutputStream());
			in = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void setUpRegistry(){
			try {
				registry = LocateRegistry.getRegistry(1099);
				chat = (ChatInterface)registry.lookup("chat");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public boolean sendMessage(String msg,String sender){
		try {
			if(chat == null){
				System.out.println("Chat is null");
			}else{
				chat.addMessage(new Message(sender,msg));
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
		return true; 
	}
}
