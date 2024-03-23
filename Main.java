import java.net.Authenticator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager =new WestminsterShoppingManager();
        UserAuthenticator userAuth = new UserAuthenticator();


        Scanner input=new Scanner(System.in);

        System.out.println("---------- Welcome to the Westminster Shopping Manager ----------");
        int userCheck;
        do {
            System.out.println(" Who are you? ");
            System.out.println("1) System Manager");
            System.out.println("2) Customer");
            System.out.print("Enter your option: ");

            while (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid option (1 for System Manager or 2 for Customer).");
                input.next();
            }
            userCheck = input.nextInt();
            if (userCheck==1){
                shoppingManager.loadUserData(); //loading user data stored text fie using this method
                System.out.println("_____________________________________");
                if (shoppingManager.runManagerLoginSystem()){ //calling manager log in system
                    systemManager(); //system manager console calling
                }
            } else if (userCheck == 2) {
                System.out.println("welcome to Our Shop! ");
                customer(input,userAuth); //user access system

            }else {
                System.out.println( "Invalid choice. Please choose 1 for System Manager or 2 for Customer.");
            }
        }while (userCheck !=1 && userCheck!=2);


    }

    private static void customer(Scanner input,UserAuthenticator userAuth) {
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Log in");
            System.out.println("2. Create account");
            System.out.println("3. Exit");
            System.out.print("Enter your Choice: ");
            int choice;

            while (true) {
                try {
                    choice = input.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    /*
                    * Clear the buffer
                    * this ensure that the buffer is empty and ready for the next input.*/
                    input.nextLine();
                    System.out.println("Invalid input. Please enter a number.");
                    System.out.print("Enter your Choice: ");
                }
            }

            switch (choice) {
                case 1:
                    // Log in
                    boolean loginSuccessful;
                    do {
                        userAuth.readUserDataFromFile();
                        System.out.print("Enter your username:");
                        String signInUsername = input.next();
                        System.out.print("Enter your password:");
                        String signInPassword = input.next();
                        loginSuccessful = userAuth.signIn(signInUsername, signInPassword);//calling a boolean which says if login is successful or not

                        if (!loginSuccessful) {
                            System.out.print("Do you want to try logging in again? (yes/no)");
                            String tryAgain = input.next().toLowerCase();
                            if (!tryAgain.equals("yes")) {
                                System.out.print("Do you want to create a new account? (yes/no)");
                                String createNewAccount = input.next().toLowerCase();
                                if (createNewAccount.equals("yes")) {
                                    System.out.print("Enter your new username:");
                                    String signUpUsername = input.next();
                                    System.out.print("Enter your new password:");
                                    String signUpPassword = input.next();
                                    userAuth.signUp(signUpUsername, signUpPassword);
                                    loginSuccessful = true;  // Break out of the loop after creating a new account
                                }
                            }
                        }
                    } while (!loginSuccessful);
                    ShopApp shopApp = new ShopApp(); //running GUI
                    break;
                case 2:
                    // Create account
                    System.out.print("Enter your username:");
                    String signUpUsername = input.next();
                    System.out.print("Enter your password:");
                    String signUpPassword = input.next();
                    userAuth.signUp(signUpUsername, signUpPassword);
                    break;
                case 3:
                    // Exit
                    input.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    //System manager console
    private static void systemManager() {
        WestminsterShoppingManager shoppingManager =new WestminsterShoppingManager();
        Scanner input=new Scanner(System.in);
        int option=1;
        shoppingManager.readFromFile();
        shoppingManager.loadProductIdsFromFile();

        while (option !=0){
            System.out.println("\n☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲");
            System.out.println("Please Select an option: ");
            System.out.println("1) Add a new Product");
            System.out.println("2) Delete a Product");
            System.out.println("3) Print the list of products");
            System.out.println("4) Save in file ");
            System.out.println("\t0) Quit ");
            System.out.println("☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲☲");
            do{ //option validation
                System.out.print("Enter option: ");
                if (input.hasNextInt()){
                    option= input.nextInt();
                    if (option<5 && option>-1){
                        break;
                    }
                }
                input.nextLine();
                System.out.println("Invalid input.");
            }while (true);
            switch (option) {
                case 1 -> shoppingManager.addProductToSystem();
                case 2 -> shoppingManager.removeProductFromSystem();
                case 3 -> shoppingManager.printProductListInSystem();
                case 4 -> shoppingManager.saveToFile();
            }
        }
    }
}
