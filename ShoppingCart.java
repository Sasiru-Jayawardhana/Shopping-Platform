import java.util.*;

public class ShoppingCart {
    private ArrayList<Product> productsInCart = new ArrayList<>();// ArrayList to store products in the cart

    public void addProductToCart(Product product){  //add products to list by the Product class object
        // Check if the product is already in the cart
        for (Product cartProduct : productsInCart) {
            if (cartProduct.equals(product)) {// Check if the product is already in the cart
                // If the product is already in the cart, increase its quantity and return
                cartProduct.incrementQuantity();
                return;
            }
        }
        // If the product is not in the cart, add it with quantity 1
        product.setQuantity(1);
        productsInCart.add(product);
    }

    public ArrayList<Product> getProductsInCart() {
        return productsInCart;
    }

    // Method to calculate the total cost of products in the cart
    public double calculateTotalCost(){
        double totalCost = 0;

        for (Product product : productsInCart) { // Calculate total cost considering quantity discounts
            totalCost += (product.getPrice() * product.getQuantity());
        }

        return totalCost;
    }
    public double calculateCategoryDiscount(){ // Method to calculate first-time user discount

        if (hasCategoryDiscount()) {
            return 0.2 * calculateTotalCost(); // 20% discount
        }
        return 0;
    }

    // Method to calculate first-time user discount
    public double calculateFirstDealDiscount(){
        if (User.getCurrentUser().getIsFirstTime()) {
            return 0.1 * calculateTotalCost(); // 10% discount
        }
        return 0;
    }

    // Helper method to check if there is a category discount available
    boolean hasCategoryDiscount() {
        // Check if the user buys at least three products of the same category
        Map<String, Integer> categoryCount = new HashMap<>();

        for (Product product : productsInCart) {
            String category = product.getProductType().toLowerCase();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + product.getQuantity());
        }

        for (int count : categoryCount.values()) {
            if (count >= 3) {
                return true;
            }
        }
        return false;
    }
}
