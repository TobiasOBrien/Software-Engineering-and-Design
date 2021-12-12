package zooauthsystem;
/*
 * Tobias O'Brien
 * CS499
 * Nov/ Dec 2021
 */

// Import libraries
import java.util.Scanner;

public class MainMenu {

    public static void main(String args[]) throws Exception {
        Scanner scnr = new Scanner(System.in);

        // Declaration of variables
        String menuResponseString = "";
        int menuResponse = 0; // Used for menuResponse
        String userName = ""; // Used for user supplied userName
        String passWord = ""; // Used for user supplied passWord
        int badLogin = 0; // Track bad login attempts
        boolean showMenu = true;

        //  Loop menu while menuResponse != logout or badLogin != 3
        while (showMenu) {

            // System menu to prompt user for action
            System.out.println("Welcome to the Zoo Computer System");
            System.out.println();
            System.out.println("Please Select an Option from the following Menu");
            System.out.println("    Enter (1) to login (MD5 Hash Check)");
            System.out.println("    Enter (2) to login (SHA-256 Hash Check)");
            System.out.println("    Enter (3) to logout");
            System.out.println();

   
            // Set menuResponse to user response
            // Error exception will catch error for non integer values 
            try 
            {
                menuResponseString = scnr.nextLine();
                menuResponse = Integer.parseInt(menuResponseString.trim());
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("\nError: ");
            }

            // If menuRespone = (1) login then prompt for userName and password
            if (menuResponse == 1) {

                // Get user supplied userName
                System.out.println("Enter Username");
                userName = scnr.nextLine();

                // Get user supplied passWord
                System.out.println("Enter Password");
                passWord = scnr.nextLine();

                // Get passWord Hash from UserAuth.userPassMD5
                String userPassMD5 = UserAuthMD5.userPassMD5(passWord);

                //Verify if userName and userPassMD5 are valid
                boolean validUserPass = UserAuthMD5.validation(userName, userPassMD5);
                
                // Set count of badLogins +1 for bad 0 for good and notify user for bad
                if (!validUserPass) {
                    ++badLogin;
                    System.out.println();
                    System.out.println("Bad login....");
                    System.out.println("You have " + (3 - (badLogin)) + " attempt(s) left.");               
                    System.out.println();
                }
                else {
                    badLogin = 0;   
                }
                
                // Log user out if three incorrect logins occur
                if (badLogin == 3) {
                    System.out.println("You have entered a incorrect username or");
                    System.out.println("password too many times and will be logged out.");
                    System.out.println();
                    showMenu = false;
                }
            } 
            
            // If menuRespone = (2) login then prompt for userName and password
            if (menuResponse == 2) {

                // Get user supplied userName
                System.out.println("Enter Username");
                userName = scnr.nextLine();

                // Get user supplied passWord
                System.out.println("Enter Password");
                passWord = scnr.nextLine();

                // Get passWord Hash from UserAuthSHA.userPassSHA
                String userPassSHA = UserAuthSHA.userPassSHA(passWord);

                //Verify if userName and userPassSHA are valid
                boolean validUserPass = UserAuthSHA.validation(userName, userPassSHA);
                
                // Set count of badLogins +1 for bad 0 for good and notify user for bad
                if (!validUserPass) {
                    ++badLogin;
                    System.out.println();
                    System.out.println("Bad login....");
                    System.out.println("You have " + (3 - (badLogin)) + " attempt(s) left.");               
                    System.out.println();
                }
                else {
                    badLogin = 0;   
                }
                
                // Log user out if three incorrect logins occur
                if (badLogin == 3) {
                    System.out.println("You have entered a incorrect username or");
                    System.out.println("password too many times and will be logged out.");
                    System.out.println();
                    showMenu = false;
                }
            }             

            // Log user out if menuResponse is 3
            else if (menuResponse == 3) {
                System.out.println();
                System.out.println("Logging out...");
                System.out.println();
                showMenu = false;
            }
            
            // Notify the user they didnt enter either 1, 2, or 3
            else {
                System.out.println();
                System.out.printf("You have entered %s.", menuResponseString);
                System.out.println("\nEnter either 1, 2, or 3.");
                System.out.println();
            }
        }
    }
}
 