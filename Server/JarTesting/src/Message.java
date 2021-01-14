class Message {
	String type; 
	String username;
	String passHash;
	String input;
	double currPrice;
	double bid; 

	protected Message() {
		this.type = ""; 
		this.username = "guest"; 
		this.passHash = ""; 
		this.input = "";
		this.currPrice = 0.0;
		this.bid = 0.0; 
		System.out.println("server-side message created");
	}
	
	protected Message(String type, String user, String pass, String input, double number, double bid) {
	    this.type = type;
		this.username = user; 
	    this.passHash = pass; 
	    this.input = input;
	    this.currPrice = number;
	    this.bid = bid; 
	    System.out.println("server-side message created");
	}
}