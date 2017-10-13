package edu.njit.cs656.fall.njitmobilemailer;

/**
 * Created by Eugen on 10/12/2017.
 */

import org.junit.Before;
import org.junit.Test;

import edu.njit.cs656.fall.njitmobilemailer.email.Mail;

import static junit.framework.Assert.assertEquals;

public class MailTest {

    private Mail email;

    @Before
    public void initialize() {
        email = new Mail();
    }

    @Test
    public void testToClient() {
        final String testText = "test@test.com";
        email.setToClient(testText);
        assertEquals(testText, email.getToClient());
    }

    @Test
    public void testFromClient() {
        final String testText = "tester@tester.com";
        email.setFromClient(testText);
        assertEquals(testText, email.getFromClient());
    }

    @Test
    public void testSubject() {
        final String testText = "TEST";
        email.setSubject(testText);
        assertEquals(testText, email.getSubject());
    }

    @Test
    public void testMessage() {
        final String testText = "HELLO";
        email.setMessage(testText);
        assertEquals(testText, email.getMessage());
    }
}
