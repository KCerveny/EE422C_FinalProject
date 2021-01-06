
public class User {
	private String username; 
	private String passHash; 

	public User() {
		username = "guest"; 
		passHash = ""; 
	}
	
	public User(String username) {
		this.username = username;
		this.passHash = "";
	}
	
	public String getUsername() {
		return this.username;
	}
	public String getpassHash() {
		return this.passHash;
	}
	
	public void setPass(String hash) {
		this.passHash = hash; // In theory, the server will pass a hashed pswd to item
	}
	
	
}
