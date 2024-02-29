import java.util.ArrayList;

public class User {
    private String userName;
    private static User currentUser; // The currently logged-in user
    private String password;

    private boolean firstPurchase; //indicating if it's the user's first purchase

    public static ArrayList<User> users = new ArrayList<>();// Static list to keep track of all users in the system

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        firstPurchase = true; // Default to true for first purchase
    }

    public User(String userName, String password, boolean firstPurchase) {
        this.userName = userName;
        this.password = password;
        this.firstPurchase = firstPurchase;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        User.currentUser = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public boolean getIsFirstTime() {
        return firstPurchase;
    }

    public void setFirstTime() {
        firstPurchase = false;
    } // Method to set the user's first purchase boolean to false

    public void setFirstPurchase(boolean firstPurchase) {
        this.firstPurchase = firstPurchase;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
