abstract class Product {

//    variables
    private String productId;
    private String productName;
    private int noOfAvailableItems;
    private double price;


//constructor
    public Product(String productId, String productName, int noOfAvailableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.noOfAvailableItems = noOfAvailableItems;
        this.price = price;
    }

    public abstract String getProductType();


//getters and setters
    public String getProductId() {

        return productId;
    }

    public void setProductId(String productId) {

        this.productId = productId;
    }

    public String getProductName() {

        return productName;
    }

    public void setProductName(String productName) {

        this.productName = productName;
    }

    public int getNoOfAvailableItems() {

        return noOfAvailableItems;
    }

    public void setNoOfAvailableItems(int noOfAvailableItems) {

        this.noOfAvailableItems = noOfAvailableItems;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    public String toString(){  //to write the file as a human-readable manner
        return String.format("%s, %s, %d, %.2f",
                getProductId(), getProductName(), getNoOfAvailableItems(), getPrice());
    }


}
