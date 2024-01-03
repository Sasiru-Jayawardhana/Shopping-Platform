import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> productsInCart;


    public ShoppingCart() {

        this.productsInCart = new ArrayList<>();
    }

    public void addProductToCart(Product product){  //add products to list by the Product class object

        productsInCart.add(product);
    }
    public void deleteProductFromCart(Product product){ //delete products to list by the Product class object

        productsInCart.remove(product);
    }

    public double calculateTotalCost(){
        double totalCost = 0 ;
        for (Product product : productsInCart){
            totalCost += product.getPrice();
        }
        return totalCost;
    }
}
