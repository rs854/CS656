package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugen on 10/13/2017.
 */


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Receive {

    private static final String host = "smtp.gmail.com";
    private static final String username = "et24@njit.edu";
    private static final String password = "test"; // TODO: cannot store password in plain text

    public static Mail Email() {

        return new Mail();
    }
}
