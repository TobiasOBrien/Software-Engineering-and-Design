package zooauthsystem;
/*
 * Tobias O'Brien
 * CS499
 * Nov/ Dec 2021
 */

// Import classes
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.List;

public class UserAuthSHA {

    // userPass method to generate SHA-256 hash for user entered passWord
    public static String userPassSHA(String passWord) throws Exception {
        String orgPass = passWord; // Sets user supplied passWord to orgPass
        String orgPassHash = ""; // Declare orgPassHash to hold hash value
        
        // Get SHA-256 hash of orgPass
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(orgPass.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        
        // Set hash results to orgPassHash
        orgPassHash = sb.toString();
        
        // Return orgPassHash
        return orgPassHash;   
    }
    
    // Validates user supplied userName and SHA-256 hash of converted user supplied passWord
    public static boolean validation(String userName, String passWordSHA) throws Exception {
        String orgUser = userName; // Sets user supplied userName to orgUser
        String orgPassSHA = passWordSHA; // Sets user supplied passWord to orgPass        
        boolean userFound = false; // userFound used for final return
        boolean passFound = false; // passFound used for final return 
        boolean validUser = false; // validUser used for final return 
        FileInputStream fileStream = null; // File input Stream for credentials       
        Scanner inFileStream = null;  // Scanner object for credentials  
        
        // Gather credentials files for usage;
        fileStream = new FileInputStream("credentialsSHA.txt");    
        
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
            // Find the first word on the line to identify username
            String findUname = tmpUserCredLine.replaceAll("\\t.*", "");
            // Check to see if userName matches and SHA-256 hash is on the line            
            if ((findUname == null ? orgUser == null : findUname.equals(orgUser)) && (tmpUserCredLine.contains(orgPassSHA))) {
                userFound = true;  // Reserved for future enhancements
                passFound = true;  // Reserved for furutre enhancements
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
                userFound = false;  // Reserved for future enhancements
                passFound = false;  // Reserved for furutre enhancements
                validUser = false;  // Used for return                               
            }    
        }  
        return validUser;
    }    
}    
