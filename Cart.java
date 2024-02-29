import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;


public class Cart {
    JFrame frame;
    private ShoppingCart shoppingCart;

    public Cart(ShoppingCart shoppingCart){
        this.shoppingCart = shoppingCart;
    }

    public void OrderProcess() {
        ArrayList<Product> productsInCart = shoppingCart.getProductsInCart();

        // Create a table model with column names

        String[] columnNames = {"Product", "Quantity", "Price"};
        DefaultTableModel CartModel = new DefaultTableModel(columnNames, 0);

        // Add data to the table model
        for (Product product : productsInCart) {
            Object[] rowData = {
                    //including all the relevant information in the first column of the cart
                    (product.getProductId() + "\n, " + product.getInfo1() + "\n, " + product.getInfo2()),
                    product.getQuantity(), // Include quantity in the table
                    product.getPrice()
            };
            CartModel.addRow(rowData);
        }

        // Create a table with the table model
        JTable table = new JTable(CartModel);
        table.setRowHeight(30);
        table.setPreferredScrollableViewportSize(new Dimension(200, 200));

        // Create a scroll pane with the table
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel CartDetails = new JPanel(new GridLayout(0, 2)); //Creating a grid layout for product information panel

        //Total
        CartDetails.add(new JLabel("Total: "));
        JTextField Total = new JTextField(20);
        Total.setText(String.valueOf(shoppingCart.calculateTotalCost())); // Calculate and set the actual total based on your logic
        Total.setEditable(false);
        CartDetails.add(Total);

        //Add First purchase discount of 10%
        CartDetails.add(new JLabel("First Purchase Discount (10%):"));
        JTextField FirstDealDiscount = new JTextField(20);
        double firstDealDiscount = shoppingCart.calculateFirstDealDiscount(); //calling the discount calculation method
        FirstDealDiscount.setText(String.valueOf(firstDealDiscount));
        FirstDealDiscount.setEditable(false);
        CartDetails.add(FirstDealDiscount);

        //Add category discount of 20%
        CartDetails.add(new JLabel("Three Items in the same Category Discount (20%):"));
        JTextField sameCategoryDisc = new JTextField(20);
        double categoryDiscount = shoppingCart.calculateCategoryDiscount();//calling the discount calculation method
        sameCategoryDisc.setText(String.valueOf(categoryDiscount));
        sameCategoryDisc.setEditable(false);
        CartDetails.add(sameCategoryDisc);

        //final total deducting all the discounts
        CartDetails.add(new JLabel("Final Total"));
        JTextField FinalTotal = new JTextField(20);
        double finalTotal = shoppingCart.calculateTotalCost() - categoryDiscount - firstDealDiscount;
        FinalTotal.setText(String.valueOf(finalTotal));
        FinalTotal.setEditable(false);
        CartDetails.add(FinalTotal);

        //checkout process
        JButton btnBuy = new JButton("Buy");
        btnBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateProductQuantities(productsInCart); // Update the product quantities in the text file
                    User user = User.getCurrentUser();
                    user.setFirstTime();
                    UserAuthenticator ua= new UserAuthenticator();
                    ua.writeUserDataToFile();
                    JOptionPane.showMessageDialog(frame, "Thank you for your purchase!");
                    System.exit(0);

                } catch (IOException ex) {
                    System.out.println("Error");
                    // Handle the IOException appropriately, e.g., show an error message to the user
                }

            }
        });
        CartDetails.add(btnBuy); //adding button to the panel

        //Shopping cart main frame
        JFrame frame = new JFrame("Shopping Cart");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the frame layout
        frame.setLayout(new BorderLayout());

        // Add the scroll
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(CartDetails, BorderLayout.SOUTH);

        // Set the frame size and visibility
        frame.setSize(600, 300);
        frame.setVisible(true);

    }
    /*
    * This method is updating product quantities when someone check out.
    * It reads the relevant line using the product IDs and deduct the quantities that user bought */
    private void updateProductQuantities(ArrayList<Product> productsInCart) throws IOException {

        // Read all lines from the product text file
        BufferedReader reader = new BufferedReader(new FileReader("saveData.txt"));
        List<String> lines = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        // Iterate through the products in the cart and update the quantities in the text file
        for (Product product : productsInCart) {
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(", ");
                if (parts.length >= 3 && parts[0].equals(product.getProductId())) {
                    int availableQuantity = Integer.parseInt(parts[2]);
                    int purchasedQuantity = product.getQuantity();

                    // Update the quantity in the text file
                    lines.set(i, parts[0] + ", " + parts[1] + ", " + (availableQuantity - purchasedQuantity) +
                            ", " + parts[3] + ", " + parts[4] + ", " + parts[5]);

                    break;
                    /*
                    * Break out of the inner loop since program found the product
                    * and then for the other product(s) if exists */
                }
            }
        }

        // Write the updated lines back to the product text file
        BufferedWriter writer = new BufferedWriter(new FileWriter("saveData.txt"));
        for (String updatedLine : lines) {
            writer.write(updatedLine);
            writer.newLine();
        }
        writer.close();
    }
}
