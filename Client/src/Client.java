import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	private Scanner consoleInput = new Scanner(System.in);

	
	
	@Override
	public void start(Stage primaryStage) { 
		BorderPane mainPane = new BorderPane(); 

		// Create a scene and place it in the stage 
		Scene scene = new Scene(mainPane, 700, 500); 
		primaryStage.setTitle("Client"); // Set the stage title 
		primaryStage.setScene(scene); // Place the scene in the stage 
		primaryStage.show(); // Display the stage 

//		XX.setOnAction(e -> { 
//		});  // etc.	
		
		
		try {
			connectToServer(); 
		} catch (Exception e) {
			System.err.println("Client connection error!"); 
			e.printStackTrace();
		}
		
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
	          e.printStackTrace();
	        }
	      }
	    });

		// TODO: This will be updated, no longer taking input from the terminal
		// Add GUI actions for application
		Thread writerThread = new Thread(new Runnable() {
	      @Override
	      public void run() {
	        while (true) {
	          String input = consoleInput.nextLine();
	          String[] variables = input.split(",");
	          Message request = new Message(variables[0], variables[1], Integer.valueOf(variables[2]));
	          GsonBuilder builder = new GsonBuilder();
	          Gson gson = builder.create();
	          sendToServer(gson.toJson(request));
	        }
	      }
	    });

	    readerThread.start();
	    writerThread.start();

	}
	
	protected void processRequest(String input) {
	    return;
	  }
	
	protected void sendToServer(String string) {
	    System.out.println("Sending to server: " + string);
	    toServer.println(string);
	    toServer.flush();
	  }
	
	public static void main(String[] args) {
		launch(args);
	}
}