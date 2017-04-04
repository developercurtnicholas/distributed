import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Client(Socket s){
		this.s = s;
		try {
			this.in = new ObjectInputStream(s.getInputStream());
			this.out = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Socket getSocket(){
		return this.s;
	}
	public ObjectInputStream getInputStream(){
		return this.in;
	}
	public ObjectOutputStream getOutputStream(){
		return this.out;
	}
}
