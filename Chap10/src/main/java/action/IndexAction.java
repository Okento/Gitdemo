package action;


public class IndexAction {
	private String message;
	public void setMessage(String message){
		this.message = message;
	}
	
	
	public String getMessage(){
		return message;
	}
	
	public String execute(){
		setMessage("Hello");
		return "success";
	}
}
