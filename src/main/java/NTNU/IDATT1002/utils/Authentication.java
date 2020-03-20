package NTNU.IDATT1002.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Authentication {

    public static Random r = new SecureRandom();

    /**
     * Hashes password with salt from getSalt method
     * @param password desired password as input
     * @return hashed password
     * @throws NoSuchAlgorithmException
     */
    public static boolean setPassword(String username, String password) throws NoSuchAlgorithmException {
        String hashedPassword = null;
        byte[] salt = getSalt();
        StringBuilder sb = new StringBuilder();

        if (username == null || password == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        if(password.isBlank() || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }
        if (username.isEmpty() || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());

            //Converts the StringBuilder to hexadecimal
            for(int i = 0; i < bytes.length; i++) {
                sb.append((Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1)));
            }
            //Gets the whole hash in hexformat into a string
            hashedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //TODO: Need to make database connection here
        return false;
    }

    /**
     * Gets hash and salt from database with the username
     * Hashes input password with same algorithm and salt as when created
     * Compares the expected has and the new hash
     * @param username to get the stored hash on give user
     * @param password that will be hashed and compared to original hash
     * @return boolean of whether the hashes are similiar or not
     */
    public static boolean isCorrectPassword(String username, String password) {
        //TODO: get password salt and hash with username from db

        byte[] dbSalt = null;
        String expectedHash = null;

        if(dbSalt == null) {
            throw new IllegalArgumentException("Salt cannot be null");
        }
        else if(expectedHash == null) {
            throw new IllegalArgumentException("Hash cannot be null");
        }
        else if(username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        if(password.isBlank() || username.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }

        String hashedInputPassword = createHashWithPredeterminedSalt(dbSalt, password);

        if(expectedHash.equals(hashedInputPassword)) {
            return true;
        }

        return false;
    }

    /**
     * Creates a hash with a predetermined salt and password determined in advance
     * @param salt that will be used when hashing
     * @param password that will be hashed
     * @return hashed string based on salt and password
     */
    private static String createHashWithPredeterminedSalt(byte[] salt, String password) {
        StringBuilder sb = new StringBuilder();
        String hashedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());

            //Converts the StringBuilder to hexadecimal
            for (int i = 0; i < bytes.length; i++) {
                sb.append((Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1)));
            }

            //Gets the whole hash in hexformat into a string
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }


    /**
     * Creates hash with an random salt
     * @param password that wil be hashed
     * @return hashed password with salt
     */
    private static String createHash(String password) {
        StringBuilder sb = new StringBuilder();
        String hashedPassword = null;
        byte[] salt = getSalt();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());

            //Converts the StringBuilder to hexadecimal
            for(int i = 0; i < bytes.length; i++) {
                sb.append((Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1)));
            }
            hashedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e ) {
            e.printStackTrace();
        }
        return hashedPassword;
    }


    /**
     * Uses secure random to create a random salt
     * @return random salt
     */
    private static byte[] getSalt() {
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return salt;
    }
}
