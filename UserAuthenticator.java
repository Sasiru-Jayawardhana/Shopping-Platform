import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserAuthenticator {

    public void signUp(String username, String password) {// Create a new User object with the provided username and password
        User user = new User(username, password);
        user.addUser(user);// Add the user to the list of users in the system
        User.setCurrentUser(user);// Set the current user to the newly registered user
        System.out.println("User registered successfully!");
    }
    public boolean signIn(String username, String password) {
        // Check if the provided username and password match any existing user
        for (User user : User.getUsers()) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                User.setCurrentUser(user);// Set the current user to the logged-in user
                System.out.println("User logged in successfully!");
                return true;
            }
        }
        return false;// If no match is found, return false
    }
    // Method to write user data to a file
    public void writeUserDataToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("CustomerData.txt"))) {
            // Iterate through all users and write their data to the file
            for (User user : User.getUsers()) {
                bufferedWriter.write(user.getUserName() + ":" + user.getPassword()+ ":" + user.getIsFirstTime());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    //method to read user data
    public void readUserDataFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("CustomerData.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Split the line into username, password, and isFirstTime using ":" as the separator
                String[] userData = line.split(":");

                // Create a new User object with the read data
                User user = new User(userData[0], userData[1]);
                user.setFirstPurchase(Boolean.parseBoolean(userData[2]));

                // Add the user to the list of users in the system
                user.addUser(user);
            }
            System.out.println("User data read from file successfully!");
        } catch (IOException e) {
            System.out.println("Error reading user data from file");
            e.printStackTrace();
        }
    }

}
