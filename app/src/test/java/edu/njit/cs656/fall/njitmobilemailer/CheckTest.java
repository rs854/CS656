package edu.njit.cs656.fall.njitmobilemailer;

/**
 * Created by Eugen on 10/28/2017.
 */

import org.junit.Before;
import org.junit.Test;

import edu.njit.cs656.fall.njitmobilemailer.email.Check;

public class CheckTest {

    private Check check;

    @Before
    public void initialize() {
        check = new Check();
    }

    @Test
    public void checkEmail() {

        Check check = new Check();

        check.Email();
    }

}
