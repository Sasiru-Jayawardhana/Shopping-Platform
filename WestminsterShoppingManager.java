import java.util.List;

public class WestminsterShoppingManager implements ShoppingManager{
    private List <Product> productList;
    private ShoppingCart shoppingCart;

    public WestminsterShoppingManager (List <Product> productList){
        this.productList = productList;
        this.shoppingCart= new ShoppingCart();
    }
}
