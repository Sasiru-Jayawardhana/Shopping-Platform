import java.util.Objects;

abstract class Product {

    private String productId;
    private String productName;
    private int noOfAvailableItems;
    private double price;
    private String info1;
    private String info2;
    private int quantity; //added for cart functionality


//constructor
    public Product(String productId, String productName, int noOfAvailableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.noOfAvailableItems = noOfAvailableItems;
        this.price = price;
    }
    //this constructor is using for the cart in GUI
    public Product(String productId, String productName, double price, String info1, String info2) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.info1 = info1; //size or brand
        this.info2 = info2; //color or warranty Period
    }

    public abstract String getProductType(); //implementing subclasses


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

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    // toString method to represent the product in a human-readable manner
    public String toString(){  //to write the file as a human-readable manner
        return String.format("%s, %s, %d, %.2f",
                getProductId(), getProductName(), getNoOfAvailableItems(), getPrice());
    }

    //methods for cart functionality
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    // Equals method to determine if two products are equal based on their productId
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if the compared object is the same instance (reference equality)
        if (obj == null || getClass() != obj.getClass()) return false;// Check if the compared object is null or belongs to a different class
        Product product = (Product) obj;// Cast the compared object to Product type for further comparison
        return productId.equals(product.productId);// Compare the productId of both objects for equality
    }
}
