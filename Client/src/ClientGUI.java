import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientGUI {
	
	private  Stage primaryStage; 
	private  Client client; 
	
	public  TextField username; 
	public  TextField password;
	private  Text logError = new Text("Invalid username or password");
	
	public boolean loginSuccess; 
	public boolean loginReceived; 
	
	public void showLogin(Stage primary, Client cli) {
		client = cli; 
		primaryStage = primary; 
		
		
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
				client.sendToServer("{ type: 'login', username: '" + username.getText() + "'}");
				client.loginReceived = false; 
				while(client.loginReceived == false) {
					// wait for server response; 
					System.out.print("."); 
				}
				
				if(client.loginSuccess) {
					showMarket(); 
				}
				else loginError(); 
								
			} catch(Exception ex) {
				System.out.println("Username Login: attempt failure"); 
				ex.printStackTrace();
			}
			
		});
		
		
		// Log in with guest credentials
		guestBtn.setOnAction(event ->{
			try {
				client.sendToServer("{ type: 'login', username: 'guest'}");
				client.loginReceived = false; 
				while(client.loginReceived == false) {
					// wait for server response; 
					System.out.print("."); 
				}
				
				if(client.loginSuccess) showMarket(); 
				else loginError(); 
								
			} catch(Exception ex) {
				System.out.println("No such luck it seems"); 
				ex.printStackTrace();
			}
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
	
	private void loginError() {
		logError.setFill(Color.RED); 
		logError.setVisible(true);
	}
	
	@SuppressWarnings("rawtypes")
	public void showMarket() {
		
		Text welcome = new Text("Items for sale: ");
		
		// Create housing gridpane
		GridPane background = new GridPane(); 
		background.setPadding(new Insets(10, 10, 10, 10));
		background.setVgap(5);
		background.setHgap(5);
		background.setAlignment(Pos.CENTER);
		
		TableView<AuctionItem> table = new TableView<AuctionItem>(); 
		table.setEditable(true);
		table.setMinSize(500, 500);
		
		TableColumn<AuctionItem, String> itemNameCol = new TableColumn<AuctionItem, String>("Item"); 
		itemNameCol.setCellValueFactory(
				new PropertyValueFactory<AuctionItem, String>("name"));
		
		TableColumn<AuctionItem, String> itemDescCol = new TableColumn<AuctionItem, String>("Decription"); 
		itemDescCol.setCellValueFactory(
				new PropertyValueFactory<AuctionItem, String>("description"));
		itemDescCol.setMaxWidth(200);
		
		TableColumn<AuctionItem, String> itemPriceCol = new TableColumn<AuctionItem, String>("Price"); 
		itemPriceCol.setCellValueFactory(
				new PropertyValueFactory<AuctionItem, String>("minPrice"));
		
		TableColumn<AuctionItem, String> statusCol = new TableColumn<AuctionItem, String>("Status"); 
		statusCol.setCellValueFactory(
				new PropertyValueFactory<AuctionItem, String>("status"));
		
		table.setItems(client.marketItems);
		table.getColumns().addAll(itemNameCol, itemDescCol, itemPriceCol, statusCol); 
		
		
		// Control Panel
		GridPane control = new GridPane(); 
		
		Text selectedItemText = new Text("No Items Selected"); 
		Text selectedItemPrice = new Text("NA"); 
		Text bidErrorText = new Text(""); 
		bidErrorText.setFill(Color.RED);
		
		Text bidVal = new Text("Bid Amount: "); 
		TextField bidInput = new TextField(); 
		Button sendBid = new Button("Bid"); 
		
		Button logout = new Button("Log Out"); 
		
		control.add(bidVal, 0, 0);
		control.add(bidInput, 1, 0);
		control.add(sendBid, 2, 0);
		
		control.add(selectedItemText, 0, 1);
		control.add(selectedItemPrice, 1, 1);
		
		control.add(bidErrorText, 1, 3);
		control.add(logout, 2, 5);
		
		background.add(table, 0, 0);
		background.add(control, 1, 0);
		
		// Button to close the application
		logout.setOnAction(event ->{
			try {
				client.appShutdown();
				Thread.sleep(500);
				System.exit(0); // backup shutdown
			}
			catch(Exception ex) {
				System.err.println("Logout button failure!"); 
				ex.printStackTrace();
			}
		});
		
		sendBid.setOnAction(event ->{
			try {
				// Has the item been sold
				for(AuctionItem it: client.marketItems) {
					if (selectedItemText.getText().equals(it.getName())) {
						
						// has it been sold?
						if(it.getStatus().equals("Sold!")) {
							bidErrorText.setText("Error: this item has been sold!");
						}
						
						// is it a legal bid?
						else if(Double.parseDouble(bidInput.getText()) <= it.getMinPrice()) {
							bidErrorText.setText("Error: bid must be greater than current price");
						}
						
						else {
							bidErrorText.setText("");
						}
						
					}
				}
				
				client.sendToServer("{ type: 'bid', username: '" + client.username + "', input: '" + selectedItemText.getText() + "', bid: '" + bidInput.getText() + "' }");
				bidInput.clear();
										
				
			}catch(Exception ex) {
				System.out.println("Error sending bid value"); 
				ex.printStackTrace();
			}
		});
		
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		        //Check whether item is selected and set value of selected item to Label
		        if(table.getSelectionModel().getSelectedItem() != null) 
		        {    
		           TableViewSelectionModel selectionModel = table.getSelectionModel();
		           AuctionItem selectedItem = (AuctionItem) selectionModel.getSelectedItem();

		           selectedItemText.setText(selectedItem.getName());
		           selectedItemPrice.setText(String.valueOf(selectedItem.getMinPrice()));
		           System.out.println("Updated " + selectedItem.getName()); 
		         }
	         }
		});
		
		
		// Create a scene and place it in the stage 
		Scene market = new Scene(background, 1000, 500);
		primaryStage.setTitle("eHills Bidding Market");
		primaryStage.setScene(market); //set the scene
		
		primaryStage.show(); //display the result
	}

	
}
