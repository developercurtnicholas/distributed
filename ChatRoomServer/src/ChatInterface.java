import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatInterface extends Remote {
	
	//Get All the messages in the chat room
	ArrayList<Message> getMessages() throws RemoteException;
	
	//Get Last Message
	Message getLast()throws RemoteException;
	
	//add a message to the list of messages
	boolean addMessage(Message msg) throws RemoteException;
}
