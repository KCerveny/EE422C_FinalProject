import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import java.util.logging.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/*
 * Author: Vallath Nandakumar and EE 422C instructors
 * Date: November 9, 2020
 * This starter code is from the MultiThreadChat example from the lecture, and is on Canvas. 
 * It doesn't compile.
 */


public class Client extends Application { 
	// I/O streams 
	private PrintWriter toServer = null; 
	private BufferedReader fromServer = null;	
	private ClientGUI display; 
	private Client client; 
	
	// Keep track of to close out on shutdown
	private Socket socket;
	private Thread readerThread; 
	
	// Debugging logger
	static Logger logger; 
	
	// Server message variables
	public boolean loginSuccess; 
	public boolean loginReceived; 
	public String username; 
	public String passHash; 
	
	// Items available to bid on
	public ObservableList<AuctionItem> marketItems; 
	public Property<ObservableList<AuctionItem>> marketProperty; 
	
	// Initializer
	public Client() {
		this.loginSuccess = false; // Not logged-in to begin with 
	}
	
	@Override
	public void start(Stage primaryStage) { 
		
		client = new Client(); 
		
		// Create logger instance for debugging
		logger = Logger.getLogger(Client.class.getName());
		
		try {
			client.connectToServer(); 
		} catch (Exception e) {
			System.err.println("Client connection error!"); 
			e.printStackTrace();
		}
		ClientGUI disp = new ClientGUI(); 
		this.display = disp; 
		
		display.showLogin(primaryStage, client); 
		
		
	}
	
	private void connectToServer() throws Exception { 
		int port = 5000; 
		// Create a socket to connect to the server 

		socket = new Socket("localhost", port); 
		
		// Create an input stream to receive data from the server 
		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
		// Create an output stream to send data to the server 
		toServer = new PrintWriter(socket.getOutputStream()); 
		
		readerThread = new Thread(new Runnable() {
	      @Override
	      public void run() {
	        String input;
	        try {
	          while ((input = fromServer.readLine()) != null) {
	            System.out.println("From server: " + input);
	            processRequest(input);
	          }
	        } catch (Exception e) {
	        	logger.log(Level.SEVERE, "Client: reader thread failure!");
	        	e.printStackTrace();
	        }
	      }
	    });

	    readerThread.start();
	}
	
	
	// Processes all returned messages from the server
	protected void processRequest(String input) {
	    
		Gson gson = new Gson();
		Message message = gson.fromJson(input, Message.class);
		System.out.println(message.type); 
		try {
			switch(message.type) {
			
				case "login": 
					if(message.loginSuccess == true) {
						logger.log(Level.FINE, "Valid login attempt.");
						this.username = message.username;
						
						
						this.loginSuccess = true;
						this.loginReceived = true; 
						
						// Server request for puchase history, current sale items
//						sendToServer("{ type: 'history', username: '" + username + "'}");
						
						// Retrieve all items on market
						sendToServer("{ type: 'getItems', username: '" + username + "'}");
						
						
					}
					else {
						// Display "incorrect username or password"
						logger.log(Level.INFO, "Invalid login attempt.");
						this.loginSuccess = false; 
						this.loginReceived = true; 
					}
					break; 
					
				case "history": 
					try {
						// Populate window with user's purchase history
						
					} catch(Exception ex) {
						logger.log(Level.SEVERE, "Unable to populate user purchase history.");
						ex.printStackTrace();
					}
					return; 
					
				case "getItems": 
					
						// convert JSON to list of AuctionItem objects
						List<AuctionItem> jsonList = new Gson().fromJson(message.input, new TypeToken<List<AuctionItem>>() {}.getType()); 
						marketItems = FXCollections.observableArrayList(jsonList); // Cast into ObservableList for TableView
						marketProperty = new SimpleObjectProperty<>(marketItems); 
						for(AuctionItem i : marketItems) System.out.println(i); 
						
					return; 
				
				// Item has been bidded on, updating its status for the client
				case "bid":
					System.out.println("We have received a new bid: " + message.currPrice);
					
					// Update price of item which has been bid on
					for(AuctionItem item: marketItems) {
						// Find correct item, update information
						if(item.getName().equals(message.input)) {
							
							// We have found the correct item
							int place = marketItems.indexOf(item); 
							
							item.setMinPrice((double) message.currPrice);
							if(item.getSalePrice() <=  item.getMinPrice()) {
								item.setStatus("Sold!");
							}
							else {
								item.setStatus("New Bid!");
							}
							
							marketItems.set(place, item); 

						}
					}
					
					return; 
					
				case "error": 
					
					
					System.out.println("Error: " + message.input);
			}
		}catch(Exception ex) {
			logger.log(Level.SEVERE, "Error in client processing message from server");
			ex.printStackTrace();
		}
		
	  }
	
	protected void sendToServer(String string) {
	    logger.log(Level.INFO, "Sending to server: " + string);
	    toServer.println(string);
	    toServer.flush();
	  }
	
	@SuppressWarnings("deprecation")
	public void appShutdown() throws IOException {
		readerThread.stop();
		socket.close();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}