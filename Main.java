import java.util.Scanner;

public class Main {

    public static final String managerPassword = "123";


    public static void main(String[] args) {
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
                input.next();  // consume the invalid input
            }
            userCheck = input.nextInt();
            if (userCheck==1){
                boolean accessGranted = false;
                while (!accessGranted){
                    System.out.println("_____________________________________");
                    System.out.print("Enter manager password: ");
                    String password = input.next();
                    if (password.equals(managerPassword)){
                        accessGranted =true;
                        System.out.println("Welcome, System Manager! You are now logged in to the Shop Managing system.");
                        systemManager();
                    }else {
                        System.out.println("Incorrect Password. Access denied. ");
                        System.out.print(" You want to try again? (y/n) : ");
                        char tryAgain = input.next().toLowerCase().charAt(0);
                        if (tryAgain!= 'y'){
                            systemManager();
                            break;
                        }
                    }
                }

            } else if (userCheck == 2) {
                System.out.println("welcome to Our Shop! ");
                customer();

            }else {
                System.out.println( "Invalid choice. Please choose 1 for System Manager or 2 for Customer.");
            }
        }while (userCheck !=1 && userCheck!=2);


    }

    private static void customer() {
        System.out.println("in the customer method");
    }

    private static void systemManager() {
        ShoppingManager shoppingManager =new WestminsterShoppingManager();
        Scanner input=new Scanner(System.in);
        int option=1;
        ((WestminsterShoppingManager) shoppingManager).readFromFile();
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
                }input.nextLine();
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
