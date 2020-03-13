package utils;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizerTest {

    @Test
    void testNullAsInputOnsetPassword() throws NoSuchAlgorithmException {
        /**
         * Testing the method with null as input
         */
        try {
            Authorizer.setPassword(null, null);
            fail("Expected action to throw  IllegalArgumentException ");
        } catch (IllegalArgumentException e) {
            //Test is a Success
        }
    }

    @Test
    void testBlankPasswordOnsetPassword() throws NoSuchAlgorithmException {
        /**
         * Testing the method with an empty password and an credible username
         */
        try {
            Authorizer.setPassword("Username", "");
            fail("Expected action to throw  IllegalArgumentException ");
        } catch (IllegalArgumentException e) {
            //Test is a success as you are not supposed to get anything checked with empty password
        }
    }

    @Test
    void testBlankUsernameOnsetPassword() throws NoSuchAlgorithmException {
        /**
         * Testing the method with an empty username and an credible username
         */
        try {
            Authorizer.setPassword("", "Password");
            fail("Expected action to throw  IllegalArgumentException ");
        }
        catch (IllegalArgumentException e) {
            //Test is a success as you are not supposed to get anything checked with empty password
        }
    }

    @Test
    void testCredibleInputOnsetPassword() throws NoSuchAlgorithmException {
        /**
         * TODO: Maybe add actual user credentials as input. Change the boolean
         * Testing the method with an credible username and password
         */
        try {
            assertEquals(false, Authorizer.setPassword("Username", "Password"));
        }
        catch (Exception e) {
            fail("Method should not throw an exception with this input");
        }
    }



    @Test
    void testNullAsInputOnisCorrectPassword() {
        /**
         * Testing the method with null ass input
         */
        try {
            Authorizer.isCorrectPassword(null, null);
            fail("Expected action to throw  IllegalArgumentException ");
        }
        catch (IllegalArgumentException e) {
            //Test is a Success as the method should return an IllegalArgumentException
        }
    }

    @Test
    void testBlankPasswordOnisCorrectPassword() throws NoSuchAlgorithmException {
        /**
         * Testing the method with an empty password and an credible username
         */
        try {
            Authorizer.isCorrectPassword("Username", "");
            fail("Expected action to throw  IllegalArgumentException ");
        } catch (IllegalArgumentException e) {
            //Test is a success as you are not supposed to get anything checked with empty password
        }
    }

    @Test
    void testBlankUsernameOnisCorrectPassword() throws NoSuchAlgorithmException {
        /**
         * Testing the method with an empty username and an credible username
         */
        try {
            Authorizer.isCorrectPassword("", "Password");
            fail("Expected action to throw  IllegalArgumentException ");
        }
        catch (IllegalArgumentException e) {
            //Test is a success as you are not supposed to get anything checked with empty password
        }
    }

    @Test
    void testCredibleInputOnisCorrectPassword() throws NoSuchAlgorithmException {
        /**
         * TODO: Maybe add actual user credentials as input. Change the boolean
         * Testing the method with an credible username and password
         */
        try {
            assertEquals(false, Authorizer.isCorrectPassword("Username", "Password"));
            fail("The salt is set as null automatically. The test should therefore throw IllegalArgumentException");
        }
        catch (Exception e) {
        //Salt is set to null since it is not connected to database and should therefore throw exception
        }
    }
}

