import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.table.TableRowSorter;
import javax.swing.RowSorter;

public class ShopApp extends JFrame {
    Object[][] data; //store data
    JTable productTable;
    private JPanel productDetailsPanel;

    private ShoppingCart shoppingCart = new ShoppingCart();
    private Cart newWindow;

    public ShopApp() {
        JPanel mainPanel = new JPanel(new BorderLayout()); //main panel
        JButton cartButton = new JButton("Shopping Cart"); //cart button
        cartButton.setFont(new Font("Lucida Sans", Font.BOLD, 12));
        cartButton.setFocusable(false);
        cartButton.setBorder(BorderFactory.createEtchedBorder());
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cartButton) {
                    newWindow = new Cart(shoppingCart); //opens cart
                    newWindow.OrderProcess(); //main method of the cart window
                }
            }
        };
        cartButton.addActionListener(actionListener);

        //panels for shopping cart button
        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartButtonPanel.add(cartButton);

        //creating combobox for product categories
        JLabel label = new JLabel("Select Product Category");
        label.setFont(new Font("Lucida Sans", Font.BOLD, 16));
        String[] productTypes = {"All", "Electronics", "Clothing"};
        JComboBox<String> comboBox = new JComboBox<>(productTypes);
        ActionListener productTypeSelection = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == comboBox) {
                    updateTable(comboBox.getSelectedItem().toString()); //calling the method that update the table according to product type chose
                }
            }
        };
        comboBox.addActionListener(productTypeSelection);

        //data and column names for the product table
        data = getData("All");
        String[] colNames = new String[]{"Product ID", "Name", "Category", "Price", "Info"};
        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);

        //creating product table and scroll pane
        productTable = new JTable(tableModel);
        productTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(750, 250));
        productDetailsPanel = new JPanel(new GridLayout(0, 2));

        // Creating the TableRowSorter
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        productTable.setRowSorter(sorter);

        // Set sorting by the "Name" column initially
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING)); // 1 is the column index for "Name"
        sorter.setSortKeys(sortKeys);

        /*product details panel text field updating
        * because last two product data is different between clothing and electronic
        * so adding a ListSelectionListener to update product details panel */
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    updateTextFieldsLabels(selectedRow);
                }
            }
        });


        //Creating panels for category selection and product display
        JPanel categoryPanel = new JPanel();
        categoryPanel.add(label);
        categoryPanel.add(comboBox);
        JPanel productShowPanel = new JPanel();
        productShowPanel.setLayout(new BoxLayout(productShowPanel, BoxLayout.Y_AXIS));
        productShowPanel.add(cartButtonPanel);
        productShowPanel.add(categoryPanel);

        // Creating panel for the product table
        JPanel productTablePanel = new JPanel();
        productTablePanel.add(scrollPane);
        productTablePanel.setBounds(10, 10, 200, 100);
        productTablePanel.setBackground(new Color(215, 212, 212));

        // Adding components to the main panel
        mainPanel.add(productShowPanel, BorderLayout.NORTH);
        mainPanel.add(productTablePanel, BorderLayout.CENTER);
        mainPanel.add(productDetailsPanel, BorderLayout.SOUTH);

        // Creating and configuring the main JFrame
        JFrame frame = new JFrame("Westminster Shopping Platform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 600);
        frame.setIconImage(new ImageIcon("logo.png").getImage());
        frame.getContentPane().setBackground(new Color(234, 234, 234));
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Method to update the product table based on selected product category
    private void updateTable(String productType) {
        data = getData(productType);
        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
        tableModel.setDataVector(data, new String[]{"Product ID", "Name", "Category", "Price", "Info"});
    }

    // Method to retrieve data from the text file based on selected product category
    public Object[][] getData(String productType) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("saveData.txt"));
            ArrayList<String> loadedProducts = new ArrayList<>();
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                if (isProductOfType(string, productType)) {
                    loadedProducts.add(string);
                }
            }
            Object[][] data = new Object[loadedProducts.size()][5];
            for (int i = 0; i < loadedProducts.size(); i++) {
                String[] elements = loadedProducts.get(i).split(",");
                data[i][0] = elements[0];
                data[i][1] = elements[1];
                /*
                * checks if the value at the index 4 in the elements array is numeric
                * If the value is numeric, it assigns "Clothing" to data[i][2], otherwise, it assigns "Electronics".*/
                data[i][2] = isNumeric(elements[4]) ? "Clothing" : "Electronics";
                data[i][3] = elements[3];
                data[i][4] = elements[4] + ", " + elements[5];
            }
            bufferedReader.close();
            return data;

        } catch (Exception x) {
            System.out.println("Error");
            x.printStackTrace();
            return null;
        }
    }
    // Method to retrieve available items for a given product ID from the text file
    private String getAvailableItemsFromTextFile(String productId) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("saveData.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] elements = line.split(",");
                if (productId.equals(elements[0])) {
                    return elements[2];
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        return "";
    }
    // Method to update text fields and labels in the product details panel
    private void updateTextFieldsLabels(int selectedRow) {
        productDetailsPanel.removeAll(); //removing existing elements in the panel

        // Retrieving data from the selected row in the product table
        String productId = productTable.getValueAt(selectedRow, 0).toString();
        String category = productTable.getValueAt(selectedRow, 2).toString();
        String name = productTable.getValueAt(selectedRow, 1).toString();
        String MoreInfo = productTable.getValueAt(selectedRow,4).toString();
        String[] MoreInfoArray = MoreInfo.split("\\s*,\\s*");
        String Info1 = MoreInfoArray [0];
        String Info2 = MoreInfoArray[1];

        // Retrieving available items from the text file based on product ID
        String availableItems = getAvailableItemsFromTextFile(productId);

        // Creating product-specific labels and text fields in the product details panel by getting it from the product table
        String productCategory = productTable.getValueAt(selectedRow, 2).toString();

        if ("Clothing".equalsIgnoreCase(productCategory)) {
            // Clothing-specific labels and text fields
            productDetailsPanel.add(new JLabel("Product ID"));
            JTextField txtProductId = new JTextField(20);
            txtProductId.setText(productId);
            txtProductId.setEditable(false);
            productDetailsPanel.add(txtProductId);

            productDetailsPanel.add(new JLabel("Category:"));
            JTextField txtCategory = new JTextField(20);
            txtCategory.setText(category);
            txtCategory.setEditable(false);
            productDetailsPanel.add(txtCategory);

            productDetailsPanel.add(new JLabel("Name:"));
            JTextField txtName = new JTextField(20);
            txtName.setText(name);
            txtName.setEditable(false);
            productDetailsPanel.add(txtName);

            productDetailsPanel.add(new JLabel("Size:"));
            JTextField txtSize = new JTextField(20);
            txtSize.setText(Info1);
            txtSize.setEditable(false);
            productDetailsPanel.add(txtSize);

            productDetailsPanel.add(new JLabel("Colour:"));
            JTextField txtColor = new JTextField(20);
            txtColor.setText(Info2);
            txtColor.setEditable(false);
            productDetailsPanel.add(txtColor);

            productDetailsPanel.add(new JLabel("Items Available:"));
            JTextField txtAvailableItems = new JTextField(20);
            txtAvailableItems.setText(availableItems);
            txtAvailableItems.setEditable(false);
            productDetailsPanel.add(txtAvailableItems);

        } else if ("Electronics".equalsIgnoreCase(productCategory)) {
            // Electronics-specific labels and text fields
            productDetailsPanel.add(new JLabel("Product ID"));
            JTextField txtProductId = new JTextField(20);
            txtProductId.setText(productId);
            txtProductId.setEditable(false);
            productDetailsPanel.add(txtProductId);

            productDetailsPanel.add(new JLabel("Category:"));
            JTextField txtCategory = new JTextField(20);
            txtCategory.setText(category);
            txtCategory.setEditable(false);
            productDetailsPanel.add(txtCategory);

            productDetailsPanel.add(new JLabel("Name:"));
            JTextField txtName = new JTextField(20);
            txtName.setText(name);
            txtName.setEditable(false);
            productDetailsPanel.add(txtName);

            productDetailsPanel.add(new JLabel("Brand:"));
            JTextField txtBrand = new JTextField(20);
            txtBrand.setText(Info1);
            txtBrand.setEditable(false);
            productDetailsPanel.add(txtBrand);

            productDetailsPanel.add(new JLabel("Warranty Period:"));
            JTextField txtWPeriod = new JTextField(20);
            txtWPeriod.setText(Info2);
            txtWPeriod .setEditable(false);
            productDetailsPanel.add(txtWPeriod);

            productDetailsPanel.add(new JLabel("Items Available:"));
            JTextField txtAvailableItems = new JTextField(20);
            txtAvailableItems.setText(availableItems);
            txtAvailableItems.setEditable(false);
            productDetailsPanel.add(txtAvailableItems);
        }
        // Creating "Add to Shopping Cart" button
        JButton btnAdd = new JButton("Add to Shopping Cart");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSelectedItemToCart();
            }
        });
        productDetailsPanel.add(btnAdd);

        // Refreshing the product details panel
        productDetailsPanel.revalidate();
        productDetailsPanel.repaint();
    }
    /**
     * Determines whether a product line matches a desired product type.
     *
     * @param productLine The product information as a comma-separated string.
     * @return True if the product matches the desired type, false otherwise.
     */
    private boolean isProductOfType(String productLine, String desiredType) {
        String[] elements = productLine.split(",");
        if ("All".equalsIgnoreCase(desiredType)) {
            // If the desired type is "All," all products are considered a match
            return true;
        } else if ("clothing".equalsIgnoreCase(desiredType)) {
            // If the user chosen type is "Clothing," check if the category in the elements array is numeric
            return isNumeric(elements[4]);
        } else if ("electronics".equalsIgnoreCase(desiredType)) {
            // If the user chosen type is "Electronics," check if the category in the elements array is not numeric
            return !isNumeric(elements[4]);
        }
        return false;
    }

    // Method to check if a given string is numeric
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method to add the selected item to the shopping cart
    private void addSelectedItemToCart() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Retrieving information from the selected row in the product table
            String productId = productTable.getValueAt(selectedRow, 0).toString();
            String name = productTable.getValueAt(selectedRow, 1).toString();
            double price = Double.parseDouble(productTable.getValueAt(selectedRow, 3).toString());
            String productCategory = productTable.getValueAt(selectedRow, 2).toString();
            String info1 = "";
            String info2 = "";

            // Extract information from text fields based on product category
            if ("Clothing".equalsIgnoreCase(productCategory)) {
                info1 = ((JTextField) productDetailsPanel.getComponent(7)).getText(); // Size
                info2 = ((JTextField) productDetailsPanel.getComponent(9)).getText(); // Color
            } else if ("Electronics".equalsIgnoreCase(productCategory)) {
                info1 = ((JTextField) productDetailsPanel.getComponent(7)).getText(); // Brand
                info2 = ((JTextField) productDetailsPanel.getComponent(9)).getText(); // Warranty Period
            }
            // Creating a Product object with extracted information
            Product product = new Product(productId, name, price, info1, info2) {
                @Override
                public String getProductType() {
                    return productCategory; // Return the actual product category
                }
            };

            // Adding the product to the shopping cart
            shoppingCart.addProductToCart(product);
            JOptionPane.showMessageDialog(this, "Item added to the shopping cart");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product from the table");
        }
    }


}
