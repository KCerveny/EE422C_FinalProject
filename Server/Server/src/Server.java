import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import com.google.gson.Gson; 

/*
 * Author: Vallath Nandakumar and the EE 422C instructors.
 * Data: November 9, 2020
 * This starter code assumes that you are using an Observer Design Pattern and the appropriate Java library
 * classes.  Also using Message objects instead of Strings for socket communication.
 * See the starter code for the Chat Program on Canvas.  
 * This code does not compile.
 */

public class Server extends Observable {

    static Server server;

    public static void main (String [] args) {
        server = new Server();
//        server.populateItems();
        server.SetupNetworking();
    }

    private void SetupNetworking() {
        int port = 5000;
        try {
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                Socket clientSocket = ss.accept();
                System.out.println("got a connection");
                
                ClientHandler handler = new ClientHandler(this, clientSocket); // Pass the server to be observed, socket to update
                this.addObserver(handler);
                
                Thread t = new Thread(handler);
                t.start();               
            }
        } catch (IOException e) {
        	System.out.println("Server Network Setup Exception: "+ e.toString());
        	e.printStackTrace(); 
        }
    }
    
    // TODO: Write driving function to process client requests
    protected void processRequest(String input) {
        String output = "Error";
        Gson gson = new Gson();
        Message message = gson.fromJson(input, Message.class);
        try {
          String temp = "";
          switch (message.type) {
            case "upper":
              temp = message.input.toUpperCase();
              break;
            case "lower":
              temp = message.input.toLowerCase();
              break;
            case "strip":
              temp = message.input.replace(" ", "");
              break;
          }
          output = "";
          for (int i = 0; i < message.number; i++) {
            output += temp;
            output += " ";
          }
          this.setChanged();
          this.notifyObservers(output);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
	
}