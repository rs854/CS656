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
        try {
            email.setToClient(testText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(testText, email.getToClient());
    }

    @Test
    public void testFromClient() {
        final String testText = "tester@tester.com";
        try {
            email.setFromClient(testText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(testText, email.getFromClient());
    }

    @Test
    public void testToClientInvalid() {
        final String testText = "JLNKASFFNLS$(@*#(&^*%@(&*";
        try {
            email.setToClient(testText);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "ERR: Invalid email.");
        }
        assertEquals(true, email.getFromClient().isEmpty());
    }

    @Test
    public void testFromClientInvalid() {
        final String testText = "^&*$%@*&^^&*@$&^*JKNFSJKNLFSNJKLF3498198732927843";
        try {
            email.setFromClient(testText);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "ERR: Invalid email.");
        }
        assertEquals(true, email.getFromClient().isEmpty());
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
