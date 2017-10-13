package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugen on 10/12/2017.
 */

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class Send {

    private static final String host = "smtp.gmail.com";

    public static void Email(Mail letter) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.user", "");
        properties.setProperty("mail.password", "");

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(letter.getFromClient()));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(letter.getToClient()));

            message.setSubject(letter.getSubject());

            BodyPart messageBody = new MimeBodyPart();

            messageBody.setText(letter.getMessage());

            Transport.send(message);
        } catch(MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
