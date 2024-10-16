// Class that defines the functionality of User Accounts
public class UserAccount {

    // Variables for User Accounts
    private int accountId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private int age;
    private Preferences userPreferences;

    // Default Constructor
    public UserAccount(){
        accountId = 0;
        username = null;
        password = null;
        email = null;
        phoneNumber = null;
        age = 0;
        userPreferences = null;
    }

    // Parameterized Constructor
    public UserAccount(int AccountId, String Username, String Password, String Email, String PhoneNumber, int Age, Preferences UserPreferences){
        accountId = AccountId;
        username = Username;
        password = Password;
        email = Email;
        phoneNumber = PhoneNumber;
        age = Age;
        userPreferences = UserPreferences;
    }

    // GETTERS
    public int getAccountId(){
        return accountId;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public int getAge(){
        return age;
    }
    public Preferences getUserPreferences(){
        return userPreferences;
    }

    // SETTERS
    public void setAccountId(int AccountId){
        accountId = AccountId;
    }
    public void setUsername(String Username){
        username = Username;
    }
    public void setPassword(String Password){
        password = Password;
    }
    public void setEmail(String Email){
        email = Email;
    }
    public void setPhoneNumber(String PhoneNumber){
        phoneNumber = PhoneNumber;
    }
    public void setAge(int Age){
        age = Age;
    }
    public void setUserPreferences(Preferences UserPreferences){
        userPreferences = UserPreferences;
    }

    // Function for login checks
    public Boolean login(){
        return true;
    }

    // Function for signup checks
    public Boolean signup(){
        return true;
    }
}
