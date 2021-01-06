class Message {
	String type; 
	String username;
	String passHash;
	String input;
	int number;

	protected Message() {
		this.type = ""; 
		this.username = "guest"; 
		this.passHash = ""; 
		this.input = "";
		this.number = 0;
		System.out.println("server-side message created");
	}
	
	protected Message(String type, String user, String pass, String input, int number) {
	    this.type = type;
		this.username = user; 
	    this.passHash = pass; 
	    this.input = input;
	    this.number = number;
	    System.out.println("server-side message created");
	}
}