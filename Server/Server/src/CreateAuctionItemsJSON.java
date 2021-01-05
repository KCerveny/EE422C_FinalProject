import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class CreateAuctionItemsJSON {
	
	public static List<AuctionItem> items; 
	
	public void createList() {
		
		try {
		    // create a list of users
		    items = Arrays.asList(
		            new AuctionItem("breadboard", "conventional solderless breadboard, .1 inch spread",
		                    2.12, false),
		            new AuctionItem("Wire Set", "MM, MF, FF Wires in an assortment of colors",
		                    3.30, false)
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
	
}
