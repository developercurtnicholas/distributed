import java.io.Serializable;

public class Message implements Serializable  {
	private String sender;
	private String content;

	public Message(String s , String c){
		sender = s;
		content = c;
	}
	
	public String getSender(){
		return this.sender;
	}
	public String getMessage(){
		return this.content;
	}
}
