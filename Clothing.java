public class Clothing extends Product{
    private  float size;
    private String colour;

//    public Clothing(String productId, String productName, int noOfAvailableItems, float price) {
//        super(productId, productName, noOfAvailableItems, price);
//    }

    public Clothing(String productId, String productName, int noOfAvailableItems, float price, float size, String colour) {
        super(productId, productName, noOfAvailableItems, price);
        this.size = size;
        this.colour = colour;
    }

    @Override
    public String getProductType() {
        return "Clothing";
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
