import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class WestminsterShoppingManagerTest {
    WestminsterShoppingManager wstmanager = new WestminsterShoppingManager();


    @Test
    @Order(1)

    void addProductToSystem() {
        int initialSize = wstmanager.getProductList().size();

        String testUserInput = "1\n001\nElectronic1\n5\n499.99\nBrandX\n12\n";
        System.setIn(new ByteArrayInputStream(testUserInput.getBytes()));
        wstmanager.addProductToSystem();
        assertEquals(initialSize + 1, wstmanager.getProductList().size());
        Product addedProducts = wstmanager.getProductList().get(0);
        assertInstanceOf(Electronics.class, addedProducts);
        assertEquals("001", addedProducts.getProductId());
        assertEquals("Electronic1", addedProducts.getProductName());
        assertEquals(499.99, addedProducts.getPrice(), 0.01);
        assertEquals(5, addedProducts.getNoOfAvailableItems());
        assertEquals("BrandX", ((Electronics) addedProducts).getBrand());
        assertEquals(12, ((Electronics) addedProducts).getWarrantyPeriod());

        // Reset System.in
        System.setIn(System.in);
    }

    @Test
    @Order(3)
    void removeProductFromSystem() {
        int initialSize = wstmanager.getProductList().size();

        String simulatedUserInput = "001\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

        wstmanager.removeProductFromSystem();
        // Assert expected behavior
        assertEquals(initialSize, wstmanager.getProductList().size());

        System.setIn(System.in);
    }

    @org.junit.jupiter.api.Test
    @Order(2)
    void printProductListInSystem() {
        wstmanager.printProductListInSystem();
    }
}