package FinalProject;
/*
 * Tobias O'Brien
 * IT-145-T1457
 * Final Project
 * 10-21-2018
 * 
 * UserAuth class containing
 * userPassMD5 method to convert user supplied password to MD5 format
 * validation method to validate username and MD5 formated password against
 * credentials.txt file.  If valid, it will display the users role file
 * and return true
 */

// Import libraries
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.List;

public class UserAuth {

    // userPassMD5 method to generate MD5 hash for user entered passWord
    public static String userPassMD5(String passWord) throws Exception {
        String orgPass = passWord; // Sets user supplied passWord to orgPass
        String orgPassHash = ""; // Declare orgPassHash to hold hash value
        
        // Get MD5 hash of orgPass
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(orgPass.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        
        // Set hash results to orgPassHash
        orgPassHash = sb.toString();
        
        // Return orgPassHash
        return orgPassHash;  // Return hashed password results   
    }
    
    // Validates user supplied userName and MD5 hash of converted user supplied passWord
    public static boolean validation(String userName, String passWordMD5) throws IOException {
        String orgUser = userName; // Sets user supplied userName to orgUser
        String orgPassMD5 = passWordMD5; // Sets user supplied passWord to orgPassMD5        
        //boolean userFound = false; // Reserved for future enhancements
        //boolean passFound = false; // Reserved for future enhancements 
        boolean validUser = false; // validUser used for final return 
        FileInputStream fileStream = null; // File input Stream for credentials       
        Scanner inFileStream = null;  // Scanner object for credentials      
        
        // Gather credentials files for usage;
        fileStream = new FileInputStream("credentials.txt");       
        
        // Scanner for credentials file, uses tab and return delimiter
        inFileStream = new Scanner(fileStream);
        inFileStream.useDelimiter("[\\t\\r]");  

        // Load credentials lines into userCredLine
        String tmpUserCredLine = ""; // Used to storage crediential lines tmp
        
        // Utilize loop to go thorugh each line of credentials file
        while (inFileStream.hasNextLine()) {
            tmpUserCredLine = inFileStream.nextLine();

            // Find the last word on the line to identify role
            String findRole  = tmpUserCredLine.replaceAll("^.*?(\\w+)\\W*$", "$1");

            // Check to see if userName and MD5 hash is on the line
            if ((tmpUserCredLine.contains(orgUser)) && (tmpUserCredLine.contains(orgPassMD5))) {
                //userFound = true;  // Reserved for future enhancements
                //passFound = true;  // Reserved for furutre enhancements
                validUser = true;  // Used for return 
                
                // Set path for the role files
                Path rolePath = Paths.get(findRole + ".txt");

                // Display the role file
                List<String> lines = Files.readAllLines(rolePath);
                System.out.println( );
                for (String line : lines) {
                    System.out.println(line);
                }
                System.out.println();
                break;
                }
            else {
                //userFound = false;  // Reserved for future enhancements
                //passFound = false;  // Reserved for furutre enhancements
                validUser = false;  // Used for return                               
            }    
        }  
        return validUser;  // Return validUser results
    }    
}    
