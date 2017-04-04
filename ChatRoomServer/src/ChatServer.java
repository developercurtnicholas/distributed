import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ChatServer implements ClientUpdater {
	
	private Registry registry;
	private Chat chat;
	private ServerSocket ss;
	private Socket s;
	private ArrayList<Client> clients = new ArrayList<Client>();
	
	public ChatServer(){
		setUpRegistry();
		try {
			ss = new ServerSocket(100);
			
			//Accept more than one clients
			while(true){
				s = ss.accept();
				Client c = new Client(s);
				clients.add(c);

				//Multi-Threaded server 
				new Thread(new Runnable(){
					@Override
					public void run() {
						Handler handler = new Handler(c);
					}
				}).start();
				
				System.out.println("Connected Clients : " + clients.size());
				for(Client cl : clients){
					System.out.println(cl.getSocket().getRemoteSocketAddress().toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setUpRegistry(){
		try {
			
			chat = new Chat(this);
			registry = LocateRegistry.createRegistry(1099);
			registry.bind("chat", chat);

		} catch (Exception e ) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		ChatServer server = new ChatServer();
	}
	
	private class Handler{
		
		private Client client;
		
		public Handler(Client c){
			this.client = c;
		}
	}

	//Let all connected clients know that we have new messages on the server
	@Override
	public void update() {
		for(Client c : clients){
			try {
				c.getOutputStream().writeObject(true);
				c.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
