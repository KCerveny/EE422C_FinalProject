import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.logging.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	
	// Debugging logger
	static Logger logger; 
	
	// Server message variables
	private boolean loginSuccess; 
	private String username; 
	private String passHash; 
	
	// Items available to bid on
	private List<AuctionItem> marketItems; 
	
	
	
	
	// Initializer
	public Client() {
		this.loginSuccess = false; // Not logged-in to begin with 
	}
	
	@Override
	public void start(Stage primaryStage) { 
		
		// Create logger instance for debugging
		logger = Logger.getLogger(Client.class.getName());
		
		try {
			connectToServer(); 
		} catch (Exception e) {
			System.err.println("Client connection error!"); 
			e.printStackTrace();
		}
		
		display = new ClientGUI(primaryStage, toServer); 
		display.showLogin(); 
		
	}
	
	private void connectToServer() throws Exception { 
		int port = 5000; 
		// Create a socket to connect to the server 
		@SuppressWarnings("resource")
		Socket socket = new Socket("localhost", port); 
		
		// Create an input stream to receive data from the server 
		fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
		// Create an output stream to send data to the server 
		toServer = new PrintWriter(socket.getOutputStream()); 
		
		Thread readerThread = new Thread(new Runnable() {
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
						this.loginSuccess = true; // Set login to true
						this.username = message.username;
						
						// Server request for puchase history, current sale items
//						sendToServer("{ type: 'history', username: '" + username + "'}");
						
						// Retrieve all items on market
						sendToServer("{ type: 'getItems', username: '" + username + "'}");
						// Move on to bidding screen
//						display.showMarket(); 
						
					}
					else {
						// Display "incorrect username or password"
						logger.log(Level.INFO, "Invalid login attempt.");
						display.loginError();
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
						marketItems = new Gson().fromJson(message.input, new TypeToken<List<AuctionItem>>() {}.getType()); 
						
						// Display all auction items on GUI
						
					return; 
					
				case "bid": 
					
					return; 
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
	
	public static void main(String[] args) {
		launch(args);
	}
}