public class Clothing extends Product{
    private  double size;
    private String colour;

//    public Clothing(String productId, String productName, int noOfAvailableItems, float price) {
//        super(productId, productName, noOfAvailableItems, price);
//    }

    public Clothing(String productId, String productName, int noOfAvailableItems, double price, double size, String colour) {
        super(productId, productName, noOfAvailableItems, price);
        this.size = size;
        this.colour = colour;
    }

    @Override
    public String getProductType() {

        return "Clothing";
    }

    public double getSize() {

        return size;
    }

    public void setSize(double size) {

        this.size = size;
    }

    public String getColour() {

        return colour;
    }

    public void setColour(String colour) {

        this.colour = colour;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %d, %.2f, %.2f, %s",
                getProductId(), getProductName(), getNoOfAvailableItems(), getPrice(), getSize(), getColour());
    }
}
