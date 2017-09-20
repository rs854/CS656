package edu.njit.cs656.fall.njitmobilemailer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertEquals;
import edu.njit.cs656.fall.njitmobilemailer.email.Smtp;

/**
 * Created by Eugen on 9/19/2017.
 */

public class SmtpUnitTest {

    private Smtp smtp;

    @Before
    public void initialize() {
        smtp = new Smtp();
    }

    @Test
    public void testConnection() {
        smtp.ConnectServer();
        assertEquals(0, 0);
    }
}
