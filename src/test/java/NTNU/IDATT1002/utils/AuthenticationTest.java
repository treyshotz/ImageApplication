package NTNU.IDATT1002.utils;

import NTNU.IDATT1002.repository.TagRepository;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link Authentication}
 *
 * @author madslun
 * @version 1.0 06.04.20
 */

class AuthenticationTest {

    /**
     * Testing the method with null as input
     */
    @Test
    void testNullAsInputOnsetPassword() throws NoSuchAlgorithmException {

        try {
            Authentication.setPassword(null);
            fail("Expected action to throw  IllegalArgumentException ");
        } catch (IllegalArgumentException e) {
            //Test is a Success
        }
    }

    /**
     * Testing the method with an empty password and an credible username
     */
    @Test
    void testBlankPasswordOnsetPassword() throws NoSuchAlgorithmException {
        try {
            Authentication.setPassword("");
            fail("Expected action to throw  IllegalArgumentException ");
        } catch (IllegalArgumentException e) {
            //Test is a success as you are not supposed to get anything checked with empty password
        }
    }

    /**
     * Testing the method with an empty username and an credible username
     */
    @Test
    void testBlankUsernameOnsetPassword() throws NoSuchAlgorithmException {
        try {
            Authentication.setPassword("");
            fail("Expected action to throw IllegalArgumentException ");
        }
        catch (IllegalArgumentException e) {
            //Test is a success as you are not supposed to get anything checked with empty password
        }
    }

    /**
     * Testing the method with an credible username and password
     */
    @Test
    void testCredibleInputOnsetPassword() throws NoSuchAlgorithmException {
            String password = "Test123";
            ArrayList<String> credentials = Authentication.setPassword(password);
            String salt = credentials.get(0);
            String hash = credentials.get(1);
            assertTrue(Authentication.isCorrectPassword(salt, password, hash));
    }


    /**
     * Testing the method with null ass input
     */
    @Test
    void testNullAsInputOnisCorrectPassword() {
        try {
            Authentication.isCorrectPassword(null, null, null);
            fail("Expected action to throw  IllegalArgumentException ");
        }
        catch (IllegalArgumentException e) {
            //Test is a Success as the method should return an IllegalArgumentException
        }
    }

    /**
     * Testing the method with an empty password and an credible username
     */
    @Test
    void testBlankPasswordOnisCorrectPassword() throws NoSuchAlgorithmException {
        try {
            Authentication.isCorrectPassword("Username", "", "");
            fail("Expected action to throw  IllegalArgumentException ");
        } catch (IllegalArgumentException e) {
            //Test is a success as you are not supposed to get anything checked with empty password
        }
    }


    /**
     * Testing the method with an empty username and an credible username
     */
    @Test
    void testBlankUsernameOnisCorrectPassword() throws NoSuchAlgorithmException {
        try {
            Authentication.isCorrectPassword("", "Password", "");
            fail("Expected action to throw  IllegalArgumentException ");
        }
        catch (IllegalArgumentException e) {
            //Test is a success as you are not supposed to get anything checked with empty password
        }
    }


    /**
     * Testing the method with an credible username and password
     */
    @Test
    void testCredibleInputOnisCorrectPassword() throws NoSuchAlgorithmException {
        ArrayList<String> credentials = Authentication.setPassword("test");
        String saltAsString = credentials.get(0);
        String hash = credentials.get(1);
        assertTrue(Authentication.isCorrectPassword(saltAsString, "test", hash));
    }
}

