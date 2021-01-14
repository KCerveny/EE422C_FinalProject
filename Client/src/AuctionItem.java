
public class AuctionItem {
	
	private String name;
	private String description; 
    private double minPrice;
    private double salePrice; 
    private String status;

    public AuctionItem() {
    	this.name = ""; 
    	this.description = ""; 
    	this.minPrice = 0.0; 
    	this.salePrice = 5.0; 
    	this.status = "New Item"; 
    }

    public AuctionItem(String name, String description, double minPrice, double salePrice, String status) {
        this.name = name;
        this.description = description;
        this.minPrice = minPrice;
        this.salePrice = salePrice; 
        this.status = status; 
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
    	
    public String toString() {
    	return(this.name + ", " + this.description + ", " + this.status);
    }

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	
	public String getStatus() {
		return status; 
	}
	
	public void setStatus(String status) {
		this.status = status; 
	}
	
}
