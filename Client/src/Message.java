
class Message {
  String type; // what the message is in regards to
  boolean loginSuccess; 
  String username; 
  String passHash; 
  String input;
  int number;

  protected Message() {
    this.type = "";
    this.loginSuccess = false; 
    this.username = "guest"; 
	this.passHash = ""; 
    this.input = "";
    this.number = 0;
    System.out.println("client-side message created");
  }

  protected Message(String type, boolean login, String user, String pass, String input, int number) {
    this.type = type;
    this.loginSuccess = login;
    this.username = user; 
    this.passHash = pass;
    this.input = input;
    this.number = number;
    System.out.println("client-side message created");
  }
}