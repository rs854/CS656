package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugen on 10/12/2017.
 */

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import edu.njit.cs656.fall.njitmobilemailer.auth.Authentication;


public class Send {

    private static final String host = "smtp.gmail.com";

    public static void Email(Mail letter) {
        final Authentication authentication = new Authentication();

        Session session = Session.getDefaultInstance(authentication.getSMTPProperties(),
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(authentication.getUsername(), authentication.getPassword());
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
