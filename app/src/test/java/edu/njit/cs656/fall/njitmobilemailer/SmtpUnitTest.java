package edu.njit.cs656.fall.njitmobilemailer;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

import edu.njit.cs656.fall.njitmobilemailer.email.Mail;

/**
 * Created by Eugen on 9/19/2017.
 */

public class SmtpUnitTest {

    private Smtp smtp;
    private Mail email;

    @Before
    public void initialize() {
        smtp = new Smtp();
        email = new Mail();
    }

    @Test
    public void testMail() {
        email.setFromClient("et24@njit.edu");
        assertEquals("et24@njit.edu", email.getFromClient());

        email.setToClient("eugeneturgil@gmail.com");
        assertEquals("eugeneturgil@gmail.com", email.getToClient());

        email.setMessage("Hello World!");
        assertEquals("Hello World!", email.getMessage());

        email.setSubject("Subject Test!");
        assertEquals("Subject Test!", email.getSubject());

        String encoded = new String(smtp.encode("test@test"));
        String decoded = new String(smtp.decode(encoded));
        assertEquals(decoded, "test@test");

        try {
            smtp.send(email);

        } catch(Exception e) {
            System.out.println(e.getMessage());

        }
    }
}
