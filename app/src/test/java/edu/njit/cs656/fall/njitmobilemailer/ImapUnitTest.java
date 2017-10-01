package edu.njit.cs656.fall.njitmobilemailer;

import org.junit.Before;
import org.junit.Test;

import edu.njit.cs656.fall.njitmobilemailer.email.Mail;
import edu.njit.cs656.fall.njitmobilemailer.email.Imap;

/**
 * Created by eugeneturgil on 9/23/17.
 */

public class ImapUnitTest {

    private Imap imap;
    private Mail email;

    @Before
    public void initialize() {
        imap = new Imap();
        email = new Mail();
    }

    @Test
    public void testMail() {
        try {
            imap.listEmails();
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }
}
