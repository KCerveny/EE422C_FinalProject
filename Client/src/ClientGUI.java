import java.io.PrintWriter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientGUI {
	
	private Stage primaryStage; 
	private PrintWriter toServer = null; 
	
	
	public TextField username; 
	public TextField password;
	private Text logError = new Text("Invalid username or password"); ;
	
	// Initialize instance of GUI
	public ClientGUI(Stage prim, PrintWriter toServer) {
		this.primaryStage = prim; 
		this.toServer = toServer; 
	}
	
	public void showLogin() {
		
		Text userTxt=new Text("Username: ");
		Text passTxt= new Text("Password : ");
		username =new TextField();
		password =new TextField();
		Button loginBtn = new Button("Login");
		loginBtn.setAlignment(Pos.CENTER);
		Button guestBtn = new Button("Enter as Guest"); 
		
		logError.setVisible(false);
		
		// Attempt to log in with credentials entered in username and password boxes
		loginBtn.setOnAction(e-> {
			try {
				toServer.println("{ type: 'login', username: '" + username.getText() + "'}");
				toServer.flush();
			} catch(Exception ex) {
				System.out.println("No such luck it seems"); 
				ex.printStackTrace();
			}
			
		});
		
		// Log in with guest credentials
		guestBtn.setOnAction(event ->{
			toServer.println("{ type: 'login', username: 'guest'}");
			toServer.flush();
		});
		
		
		
		//create a gridpane
		GridPane r = new GridPane();
		r.setMinSize(400,200); 
		r.setPadding(new Insets(10, 10, 10, 10));
		r.setVgap(5);
		r.setHgap(5);
		r.setAlignment(Pos.CENTER);
		
		r.add(userTxt, 0, 0);
		r.add(username, 1, 0);
		r.add(passTxt, 0, 1);
		r.add(password, 1, 1);
		r.add(loginBtn, 1, 2);
		r.add(logError, 0, 2);
		r.addRow(3,guestBtn);
		
		
		// Create a scene and place it in the stage 
		Scene scene = new Scene(r);
		primaryStage.setScene(scene); //set the scene
		primaryStage.setTitle("eHills User Login");
		primaryStage.show(); //display the result
	}
	
	public void loginError() {
		logError.setFill(Color.RED); 
		logError.setVisible(true);
	}
	
	public void showMarket() {
		
	}
	
}
