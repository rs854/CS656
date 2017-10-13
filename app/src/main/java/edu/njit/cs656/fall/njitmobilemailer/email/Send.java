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
    private static final String username = "et24@njit.edu";
    private static final String password = "test"; // TODO: cannot store password in plain text

    public static void Email(Mail letter) {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", host); // TODO: 10/12/2017 Add SSL/TLS port
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

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
