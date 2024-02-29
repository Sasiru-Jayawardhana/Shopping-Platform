import  java.io.*;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager{
    private static final String userDataFilePath = "ManagerData.txt";
    private List<User> userList;
    private List<Product> productList;
    private ArrayList<String> IdList;

    public WestminsterShoppingManager() { // Constructor initializes lists and loads user data from a file

        this.productList = new ArrayList<>();
        this.IdList = new ArrayList<>();
        this.userList = loadUserData();

    }

    @Override
    public void addProductToSystem() { // Method to add a product to the system
        Scanner input =new Scanner(System.in);
        int option;
        if (productList.size()<50){  //cant be equal to 50 because there have to be one space left to add
            while (true){
                System.out.print("Enter 1 for Electronics or Enter 2 for Clothing: ");
                try{
                    option= input.nextInt();
                    if (option ==1 || option == 2){
                        break;
                    } else {
                        System.out.println("Invalid Input.");
                    }
                }catch (InputMismatchException e){
                    System.out.println("Invalid Input. please enter a valid integer.");
                }
            }
            if (option == 1){
                addElectronicProducts(input);
            }else {
                addClothingProducts(input);
            }
        }else {
            System.out.println("No space to add products");
        }


    }

    private void addClothingProducts(Scanner input) {
        String colour;
        int noOfAvailableProducts;
        double size;
        double price;
        String productID;
        String productName;

        System.out.println("------Add Clothing Products ------");
        productID = IdValidation(input); //checing if the product ID is already in use

        System.out.print("Enter Product Name: ");
        productName= input.next();

        do {
            System.out.print("Enter Number of Available items: ");
            while (!input.hasNextInt()){
                System.out.println("Please enter a valid integer for the number of available items.");
                input.next();
            }
            noOfAvailableProducts = input.nextInt();
            if (noOfAvailableProducts<=0){
                System.out.println("Number of available items must be greater than 0. Please enter a valid number.");
            }
        }while (noOfAvailableProducts<=0);

        do {
            System.out.print("Enter the Price: ");
            while (!input.hasNextDouble()){
                System.out.println("Please enter a valid input for the price.");
                input.next();
            }
            price= input.nextDouble();
            if (price<=0){
                System.out.println("Price must be greater than 0. Please enter a valid price.");
            }
        }while (price<=0);

        do {
            System.out.print("Enter the Size: ");
            while (!input.hasNextDouble()) {
                System.out.println("Please enter a valid input for the size.");
                input.next(); // consume the invalid input
            }
            size = input.nextDouble();
            if (size <= 0) {
                System.out.println("Size must be greater than 0. Please enter a valid size.");
            }
        } while (size <= 0);

        System.out.print("Enter the colour: ");
        colour= input.next();

        Clothing clothing =new Clothing(productID,productName,noOfAvailableProducts,price,size,colour);
        productList.add(clothing);
    }

    private void addElectronicProducts(Scanner input) {
        double price;
        String brand;
        String productID;
        int warrantyPeriod;
        String productName;
        int noOfAvailableProducts;
        System.out.println("------ Add Electronic products--------");

        //product Id validation
        productID = IdValidation(input); //checing if the product ID is already in use

        System.out.print("Enter Product Name: ");
        productName= input.next();

        do {
            System.out.print("Enter Number of Available items: ");
            while (!input.hasNextInt()) {
                System.out.println("Please enter a valid integer for the number of available items.");
                input.next(); // consume the invalid input
            }
            noOfAvailableProducts = input.nextInt();
            if (noOfAvailableProducts <= 0) {
                System.out.println("Number of available items must be greater than 0. Please enter a valid number.");
            }
        } while (noOfAvailableProducts <= 0);

        do {
            System.out.print("Enter the Price: ");
            while (!input.hasNextDouble()) {
                System.out.println("Please enter a valid input for the price.");
                input.next(); // consume the invalid input
            }
            price = input.nextDouble();
            if (price <= 0) {
                System.out.println("Price must be greater than 0. Please enter a valid price.");
            }
        } while (price <= 0);

        System.out.print("Enter the Brand: ");
        brand= input.next();

        do {
            System.out.print("Enter the warranty period (in Months): ");
            while (!input.hasNextInt()) {
                System.out.println("Please enter a valid integer for the warranty period.");
                input.next(); // consume the invalid input
            }
            warrantyPeriod = input.nextInt();
            if (warrantyPeriod <= 0) {
                System.out.println("Warranty period must be greater than 0. Please enter a valid number.");
            }
        } while (warrantyPeriod <= 0);

        Electronics electronics = new Electronics(productID,productName,noOfAvailableProducts,price,brand,warrantyPeriod);
        productList.add(electronics);
    }

    @Override
    public void removeProductFromSystem() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the Product ID: ");
        String productIdToRemove = input.next();

        boolean found = false;
        for (Product product : productList) {
            if (product.getProductId().equals(productIdToRemove)) {
                productList.remove(product);
                found = true;
                System.out.println("Product removed Successfully");
                System.out.println("Total Number of products left: " + productList.size());
                break; // Exit the loop after removing the product
            }
        }

        if (!found) {
            System.out.println("Product Not Found! Check if the Product ID is Correct or not.");
        }

    }

    @Override
    public void printProductListInSystem() {
        System.out.println("Products in the list:");
        productList.sort(Comparator.comparing(Product::getProductId));
        for (Product product : productList) {
            System.out.println("Product Type: " + product.getProductType());
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Available Items: " + product.getNoOfAvailableItems());
            System.out.println("Price: " + product.getPrice());

            if (product instanceof Electronics) {
                System.out.println("Brand: " + ((Electronics) product).getBrand());
                System.out.println("Warranty Period: " + ((Electronics) product).getWarrantyPeriod() + " months\n");

            } else if (product instanceof Clothing) {
                System.out.println("Size: " + ((Clothing) product).getSize());
                System.out.println("Color: " + ((Clothing) product).getColour() +"\n" );
            }

            System.out.println();
        }

    }

    @Override
    public void saveToFile() {
        try (BufferedWriter writer =new BufferedWriter(new FileWriter ("saveData.txt"))) {
            for (Product product : productList){
                writer.write(product.toString());
                writer.newLine();
            }
            saveProductIdsToFile(IdList);
            System.out.println("Product list saved to file saveData.txt Successfully.");


        } catch (FileNotFoundException e){
            System.out.println("File not found : saveData.txt");
        } catch (IOException e) {
            System.out.print("Error " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("saveData.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // toString() method in Product class returns a formatted string
                updateProductListFromFile(line);
            }
            System.out.println("Product list loaded from file successfully");
        } catch (FileNotFoundException e){
            System.out.println("saveData.txt File Not Found");
        } catch (IOException e) {
            System.out.println("error" + e.getMessage());
        }
    }

    private void updateProductListFromFile(String line)
    {
        try {
            String[] parts = line.split(",\\s*");
            if (parts.length < 6) {
                System.out.println("Invalid product format: " + line);
                return;
            }

            String productIdFromFile = parts[0];
            String productNameFromFile = parts[1];
            int availableItemsFromFile = Integer.parseInt(parts[2]);
            double priceFromFile  = Double.parseDouble(parts[3]);
            //determine the product type according to the data type  in the 5th place
            if (parts[4].matches("\\d*\\.?\\d+")){   //if the 5th place is a double it is a Clothing product (size)
                double sizeFromFile = Double.parseDouble(parts[4]);
                String colorFromFile = parts[5];
                Clothing clothing=new Clothing(productIdFromFile, productNameFromFile, availableItemsFromFile, priceFromFile, sizeFromFile, colorFromFile);
                productList.add(clothing);

            }else {
                String brandFromFile = parts[4];
                int warrantyFormFile = Integer.parseInt(parts[5]);
                Electronics electronics=new Electronics(productIdFromFile, productNameFromFile, availableItemsFromFile, priceFromFile, brandFromFile, warrantyFormFile);
                productList.add(electronics);
            }
        }catch (NumberFormatException e){
            System.out.println("Error passing numeric value from line" );
        }

    }

    public static void saveProductIdsToFile(ArrayList<String> productIds) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ExistingIdList.txt"))) {
            for (String productId : productIds) {
                writer.write(productId);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    // Method to load Product IDs from a text file and add them to the Product ID arraylist
    public void loadProductIdsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("ExistingIdList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                IdList.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    private String IdValidation(Scanner input) {
        String productID;
        while (true) {
            System.out.print("Enter a product ID: ");
            productID= input.next();
            // Check if the entered ID already exists
            if (IdList.contains(productID)) {
                System.out.println("Error: The entered Product ID already exists. Please try again.");
            } else {
                // Add the valid ID to the list and break the loop
                IdList.add(productID);
                break;
            }
        }
        return productID;
    }

    public Boolean runManagerLoginSystem() { //System manager login system.
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to User Login System");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (isValidUser(username, password)) {
            System.out.println("Login successful! Welcome, " + username + "!");
            return true;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return false;
        }


    }
    private boolean isValidUser(String username, String password) {
        for (User user : userList) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) { //checks if user has a account in shopping platform
                return true;
            }
        }
        return false;
    }
    public List<User> loadUserData() { //system manager information loading method
        List<User> userList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(userDataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",\\s*");
                if (userData.length == 2) {
                    String username = userData[0];
                    String password = userData[1];
                    userList.add(new User(username, password));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user data file.");
        }

        return userList;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
