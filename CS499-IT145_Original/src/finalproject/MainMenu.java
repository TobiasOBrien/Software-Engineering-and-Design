package FinalProject;
/*
 * Tobias O'Brien
 * IT145-T1457
 * Final Project
 * 10-21-2018
 * 
 * MainMenu class containing
 * main method used to display and control the user menu and
 * user input options for username and password
 * Method makes calls to UserAuth class userPassMD5 and validation methods
 * to convert the password to MD5 format and validate the username and password
 */

// Import libraries
import java.util.Scanner;

public class MainMenu {

    public static void main(String args[]) throws Exception {
        Scanner scnr = new Scanner(System.in);  // Create a new scanner

        // Declaration of variables
        String menuResponseString = "";
        int menuResponse = 0; // Used for menuResponse
        String userName = ""; // Used for user supplied userName
        String passWord = ""; // Used for user supplied passWord
        int badLogin = 0; // Track bad login attempts
        boolean showMenu = true;  // Used to control display of user menu

        //  Loop menu while showMenu is true
        while (showMenu) {

            // System menu to prompt user for action
            System.out.println("Welcome to the Zoo Computer System");
            System.out.println();
            System.out.println("Please Select an Option from the following Menu");
            System.out.println("    Enter (1) to login");
            System.out.println("    Enter (2) to logout");
            System.out.println();

   
            // Set menuResponse to user response
            // Error exception will catch error for non integer values 
            try 
            {
                menuResponseString = scnr.nextLine();  // Capture user input
                menuResponse = Integer.parseInt(menuResponseString.trim()); // Convert repose to a integer 
            }
            catch (NumberFormatException nfe)  // Catch exception if user response was not an integer
            {
                System.out.println("\nError: ");  // Print Error if exception
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
                String userPassMD5 = UserAuth.userPassMD5(passWord);

                // Verify if userName and userPassMD5 are valid by calling calidation method
                boolean validUserPass = UserAuth.validation(userName, userPassMD5);
                
                // Set count of badLogins +1 for bad and notify user
                if (!validUserPass) {
                    ++badLogin;
                    System.out.println();
                    System.out.println("Bad login....");
                    System.out.println("You have " + (3 - (badLogin)) + " attempt(s) left.");               
                    System.out.println();
                    menuResponse = 0; // Reset maneResponse to 0
                }
                else {
                    badLogin = 0;  // Set badLogin count to 0 if validation was true
                    menuResponse = 0; // Reset menuResponse to 0
                }
                
                // Log user out if three incorrect logins occur
                if (badLogin == 3) {
                    System.out.println("You have entered a incorrect username or");
                    System.out.println("password too many times and will be logged out.");
                    System.out.println();
                    showMenu = false;  // Stop displaying menu
                }
            } 

            // Log user out if menuResponse is 2
            else if (menuResponse == 2) {
                System.out.println();
                System.out.println("Logging out...");
                System.out.println();
                showMenu = false;  // Stop displaying menu
            }
            
            // Notify the user they didnt enter either 1 or 2
            else {
                System.out.println();
                System.out.printf("You have entered %s.", menuResponseString);
                System.out.println("\nEnter either 1 or 2.");
                System.out.println();
            }
        }
    }
}
