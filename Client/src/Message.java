
class Message {
  String type; // what the message is in regards to
  boolean loginSuccess; 
  String username; 
  String passHash; 
  String input;
  double currPrice;
  double bid; 

  protected Message() {
    this.type = "";
    this.loginSuccess = false; 
    this.username = "guest"; 
	this.passHash = ""; 
    this.input = "";
    this.currPrice= 0.0;
    this.bid = 0.0;
    
    System.out.println("client-side message created");
  }

  protected Message(String type, boolean login, String user, String pass, String input, double number, double bid) {
    this.type = type;
    this.loginSuccess = login;
    this.username = user; 
    this.passHash = pass;
    this.input = input;
    this.currPrice = number;
    this.bid = bid; 
    System.out.println("client-side message created");
  }
}