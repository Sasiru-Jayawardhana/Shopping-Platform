public class Electronics extends Product{
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId, String productName, int noOfAvailableItems, double price , String brand, int warrantyPeriod) {
        super(productId, productName, noOfAvailableItems, price);  // Call the constructor of the super class (Product)
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Overriding the getProductType method to specify the type of product
    @Override
    public String getProductType() {
        return "Electronics";
    }


    public String getBrand() {

        return brand;
    }

    public void setBrand(String brand) {

        this.brand = brand;
    }

    public int getWarrantyPeriod() {

        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {

        this.warrantyPeriod = warrantyPeriod;
    }

    //overriding the toString method to provide a formatted String representation of the object
    @Override
    public String toString() {
        return String.format("%s, %s, %d, %.2f, %s, %d",
                getProductId(), getProductName(), getNoOfAvailableItems(), getPrice(), getBrand(), getWarrantyPeriod());
    }
}
