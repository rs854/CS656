package edu.njit.cs656.fall.njitmobilemailer;

/**
 * Created by eugeneturgil on 10/22/17.
 */


import org.junit.Before;
import org.junit.Test;

import edu.njit.cs656.fall.njitmobilemailer.email.Mail;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

public class EnvTest {

    @Test
    public void testUsername() {
        assertEquals("et24@njit.edu", System.getProperty("username"));
    }

    @Test
    public void testPassword() {
        assertNotSame("password", System.getProperty("password"));
    }
}
