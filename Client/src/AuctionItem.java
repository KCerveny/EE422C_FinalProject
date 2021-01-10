
public class AuctionItem {
	
	private String name;
	private String description; 
    private double minPrice;
    private boolean isSold;

    public AuctionItem() {
    	this.name = ""; 
    	this.description = ""; 
    	this.minPrice = 0.0; 
    	this.isSold = false; 
    }

    public AuctionItem(String name, String description, double minPrice, boolean isSold) {
        this.name = name;
        this.description = description;
        this.minPrice = minPrice;
        this.isSold = isSold;
    }

    // getters
    public String getName() {
    	return this.name; 
    }
    public String getDescription() {
    	return this.description;
    }
    public double getMinPrice() {
    	return this.minPrice;
    }
    public boolean getIsSold() {
    	return this.isSold;
    }
    
    // Setters
    public void setName(String name) {
    	this.name = name; 
    }
    public void setDescription(String description) {
    	this.description = description;
    }
    public void setMinPrice(double price) {
    	this.minPrice = price; 
    }
    public void setIsSold(boolean sold) {
    	this.isSold = sold; 
    }
    	
    public String toString() {
    	return(this.name + ", " + this.description + ", " + this.isSold);
    }
	
}
