import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class CreateAuctionItemsJSON {
	
	public static List<AuctionItem> items; 
	public static List<User> users; 
	
	public void createList() {
		
		try {
		    // create a list of users
		    items = Arrays.asList(
		            new AuctionItem("breadboard", "conventional solderless breadboard, .1 inch spread",
		                    2.12, 5.12, "New"),
		            new AuctionItem("Wire Set", "MM, MF, FF Wires in an assortment of colors",
		                    3.30, 6.0, "New"), 
		            new AuctionItem("Epaper Display", "2.9 inch 3-color display", 13.40, 22.50, "New"), 
		            new AuctionItem("Glasses Cleaner", "Glasses cleaning spray, 4 oz", 1.20, 7.80, "New"), 
		            new AuctionItem("Rasperry Pi 4", "4 Gb RAM, ethernet and 2 USB 3.0 ports", 20.33, 65.70, "New"), 
		            new AuctionItem("NodeMCU ESP32", "ESP32 Development board, 32 pins, WIFI and Bluetooth", 4.21, 15.39, "New")
		    );

		    // create writer
		    Writer writer = new FileWriter("items0.json");

		    // convert users list to JSON file
		    new Gson().toJson(items, writer);

		    // close writer
		    writer.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		
	}
	
	public void createUsers() {
		try {
			users = Arrays.asList(
				new User("guest"),
				new User("CottonEyeJoe"), 
				new User("OldCowboy"), 
				new User("GoldMiner"),
				new User("CyberCactus")
			);
			
			Writer userWriter = new FileWriter("users0.json"); 
			new Gson().toJson(users, userWriter); 
			userWriter.close();
			
		} catch(Exception e) {
			System.err.println("Error creating users JSON"); 
			e.printStackTrace();
		}		
	}
	
	// Turn into a file?
	
}
