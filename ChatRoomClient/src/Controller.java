
public class Controller {
	
	private ChatWindow window;
	private Model model;
	
	public static void main(String[] args){
		Controller controller = new Controller();
	}
	
	public Controller(){
		this.model = new Model(this);
		this.begin();
		this.window = new ChatWindow(this);
		this.window.promptForUserName();
	}
	
	public void begin(){
		if(connectToServer()){
			System.out.println("Connection to server successful");
		}else{
			System.out.println("Connection failed");
		}
	}
	
	private boolean connectToServer(){
		return model.connect();
	}
	
	public void showDialog(String msg){
		window.showDialog(msg);
	}
	
	public void addLast(Message m){
		window.newMessage(m);
	} 
	
	public boolean sendMessage(String message,String sender){
		
		return model.sendMessage(message, sender);
	}
}
