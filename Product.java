abstract class Product {

//    variables
    private String productId;
    private String productName;
    private int noOfAvailableItems;
    private float price;


//constructor
    public Product(String productId, String productName, int noOfAvailableItems, float price) {
        this.productId = productId;
        this.productName = productName;
        this.noOfAvailableItems = noOfAvailableItems;
        this.price = price;
    }

//    Abstract method to be implemented by subclass
    public abstract String getProductType();

//getter and setter methods
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
