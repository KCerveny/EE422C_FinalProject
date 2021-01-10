import java.io.IOException;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Observable;
import java.util.logging.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    static List<AuctionItem> readItems; //AuctionItems read in from local json
    static Logger log; 

    public static void main (String [] args) {
        server = new Server();
        log = Logger.getLogger(Server.class.getName()); // Instantiate a logger
        
        CreateAuctionItemsJSON s = new CreateAuctionItemsJSON();
        s.createUsers();
        server.populateItems();
        server.SetupNetworking();
    }
    
    private boolean checkUsers(String username) {
    	try {
    		Gson gson = new Gson(); 
        	Reader reader = Files.newBufferedReader(Paths.get("users0.json")); 
        	List<User> readUser = new Gson().fromJson(reader, new TypeToken<List<User>>() {}.getType());

        	for(User u: readUser) {
        		if(u.getUsername().equals(username)) {
        			return true; // We have found a matching username
        		}
        	}
        	return false; 
        			
    	} catch(Exception ex) {
    		log.log(Level.WARNING, "Error finding users"); 
    		ex.printStackTrace();
    	}
		return false;
    	
    }

    private void populateItems() {
    	
    	try {
    	    Gson gson = new Gson();
    	    Reader reader = Files.newBufferedReader(Paths.get("items0.json"));
    	    readItems = new Gson().fromJson(reader, new TypeToken<List<AuctionItem>>() {}.getType());
    	    readItems.forEach(System.out::println);
    	    reader.close();
    	    
    	    
    	    // Thread constantly checks items to see if they are expired
    	    Thread expireThread = new Thread(new Runnable() {
    	    	@Override
    	    	public void run() {
    	    		// check all items in AuctionItems
    	    		// If time is expired, remove from list (?), or mark asSold(?)
    	    	}
    	    });
    	    
    	    
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	}	
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
    protected String processRequest(String input) {
        String output = "Error";
        Gson gson = new Gson();
        Message message = gson.fromJson(input, Message.class);
        try {
          String temp = "";
          switch (message.type) {
          
          	// User is attempting to log into the application
          	case "login": 
          		log.log(Level.FINE, "Login attempt: "+ message.username);
          		
          		// Check to see if username is in database
          		// Optional: check to see if passHash matches
          		if(this.checkUsers(message.username)){
          			log.log(Level.INFO, "Valid Login Credentials");
          			return "{ type: 'login', loginSuccess: true, username: '"+message.username+"'}";
          		}
          		else {
          			log.log(Level.INFO, "Invalid Login Credentials");
          			return "{ type: 'login', loginSuccess: false, username: '"+message.username+"'}";
          		}
          	
          	// Client is requesting purchase history associated with their account
          	case "history": 
      			
          		return "client history";
          		
          	// Client requesting list of all valid items on the market
          	case "getItems": 
          			String itemsToJSON = new Gson().toJson(readItems);
          		return  "{ type: 'getItems', input: '" + itemsToJSON + "'}";
          		
      		// Client sending a bid for an item on the market
            case "bid":
              System.out.println("A bid was placed: " + message.number); 
              // Update price of item
              // 
              
              return "TODO: bid JSON"; 
          }
          
          log.log(Level.SEVERE, "Invalid Client Message"); 
          return "Did not match any cases"; 
          
//          output = "";
//          for (int i = 0; i < message.number; i++) {
//            output += temp;
//            output += " ";
//          }
//          this.setChanged();
//          this.notifyObservers(output);
        } catch (Exception e) {
          e.printStackTrace();
          return "Server error"; 
        }
      }
	
}