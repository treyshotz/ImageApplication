package NTNU.IDATT1002.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Authentication {

    public static Random r = new SecureRandom();

    /**
     * Hashes password with salt from getSalt method
     * @param password desired password as input
     * @return hashed password
     * @throws NoSuchAlgorithmException
     */
    public static ArrayList<String> setPassword(String password) {
        String hashedPassword = null;
        ArrayList<String> info = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        if (password == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        if(password.isBlank() || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }
        return createHash(password);
    }

    /**
     * Gets hash and salt from database with the salt
     * Hashes input password with same algorithm and salt as when created
     * Compares the expected has and the new hash
     * @param salt to get the stored hash on give user
     * @param password that will be hashed and compared to original hash
     * @return boolean of whether the hashes are similiar or not
     */
    public static boolean isCorrectPassword(String salt, String password, String expectedHash) {
        if(salt == null) {
            throw new IllegalArgumentException("Salt cannot be null");
        }
        else if(expectedHash == null) {
            throw new IllegalArgumentException("Hash cannot be null");
        }
        else if(password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        if(salt.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        if(password.isBlank() || salt.isBlank()) {
            throw new IllegalArgumentException("Password cannot be blank");
        }
        if(expectedHash.isBlank() || expectedHash.isEmpty()) {
            throw new IllegalArgumentException("Hash from db is blank");
        }

        byte[] dbSalt = buildBytes(salt);

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
    private static ArrayList<String> createHash(String password) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> info = new ArrayList<>();
        String hashedPassword = null;
        byte[] salt = getSalt();
        String saltAsString = buildHexString(salt);

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
        info.add(saltAsString);
        info.add(hashedPassword);
        return info;
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

    /**
     * Byte-to-Hex converter
     * @param bytes is an array of byte
     * @return String bytes in hex
     */
    private static String buildHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            //Convert from byte to hex
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Hex-to-byte converter
     * @param hex input to get an byte array
     * @return byte[] converted from hex
     */
    private static byte[] buildBytes(String hex) {
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i+=2) {
            int v = Integer.parseInt(hex.substring(i, i + 2), 16);
            b[i/2] = (byte) v;
        }
        return b;
    }
}
