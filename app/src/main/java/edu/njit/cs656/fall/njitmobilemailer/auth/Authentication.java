package edu.njit.cs656.fall.njitmobilemailer.auth;

import java.util.Properties;

/**
 * Created by Eugen on 10/28/2017.
 */

public class Authentication {
    // Temporary class to handle password/username retrieval

    private static final String USERNAME = "rsmith8917@gmail.com";
    private static final String PASSWORD = "m^aK%8Qhn5B6CV%^pg";

    private Properties smtpProperties;
    private Properties pop3Properties;
    private Properties imapProperties;

    public Authentication() {
        smtpProperties = new Properties();
        smtpProperties.setProperty("mail.smtp.host", "smtp.gmail.com"); // TODO: 10/12/2017 Add SSL/TLS port
        smtpProperties.setProperty("mail.smtp.auth", "true");
        smtpProperties.setProperty("mail.smtp.starttls.enable", "true");
        smtpProperties.setProperty("mail.smtp.port", "587");

        imapProperties = new Properties();
        imapProperties.setProperty("mail.imaps.connectiontimeout", "10000");
        imapProperties.setProperty("mail.imaps.timeout", "10000");
        imapProperties.setProperty("mail.imaps.host", "imap.gmail.com");
        imapProperties.setProperty("mail.imaps.port", "993");
        imapProperties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        imapProperties.setProperty("mail.imap.socketFactory.fallback", "false");

        pop3Properties = new Properties();
        pop3Properties.setProperty("mail.pop3.host", "pop.gmail.com");
        pop3Properties.setProperty("mail.pop3.port", "995");
        pop3Properties.setProperty("mail.pop3.starttls.enable", "true");
    }

    public String getUsername() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public Properties getPOP3Properties() {
        return pop3Properties;
    }

    public Properties getSMTPProperties() {
        return smtpProperties;
    }
    public Properties getIMAPProperties() {
        return imapProperties;
    }
}
