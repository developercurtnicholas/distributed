import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Chat extends UnicastRemoteObject implements ChatInterface {
	
	private ArrayList<Message> messages;
	private ClientUpdater updater;
	protected Chat(ClientUpdater updater) throws RemoteException {
		super();
		messages = new ArrayList<Message>();
		this.updater = updater;
	}

	@Override
	public ArrayList<Message> getMessages() {
		return messages;
	}

	@Override
	public boolean addMessage(Message msg){
		boolean added = messages.add(msg);
		if(added){
			updater.update();
		}else{
			System.out.println("Message was not added!!!");
		}
		return added;
	}

	@Override
	public Message getLast() throws RemoteException {
		if(messages != null){
			if(messages.size() > 0){
				return messages.get(messages.size()-1);
			}
		}
		return null;
	}
}
