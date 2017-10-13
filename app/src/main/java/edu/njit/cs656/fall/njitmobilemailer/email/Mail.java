package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugen on 9/20/2017.
 */

import org.apache.commons.validator.routines.EmailValidator;

import java.util.*;

public class Mail { // TODO: I might delete this later on due to javax already having Message class

    private String fromClient;
    private String toClient;
    private String subject;
    private List<String> ccClient;
    private String message;

    public String getFromClient() {

        return fromClient;
    }

    public void setFromClient(final String fromClient)  throws Exception {
        EmailValidator validator = EmailValidator.getInstance();
        if(validator.isValid(fromClient)) {
            this.fromClient = fromClient;
        } else {
            throw new Exception("ERR: Invalid email.");
        }
    }


    public String getToClient() {

        return toClient;
    }

    public void setToClient(final String toClient)  throws Exception  {
        EmailValidator validator = EmailValidator.getInstance();
        if(validator.isValid(toClient)) {
            this.toClient = toClient;
        } else {
            throw new Exception("ERR: Invalid email.");
        }
    }

    public List<String> getCcClient() {

        return ccClient;
    }

    public void addCcClient(final String ccClient) throws Exception {
        EmailValidator validator = EmailValidator.getInstance();
        if(validator.isValid(toClient)) {
            this.ccClient.add(ccClient);
        } else {
            throw new Exception("ERR: Invalid email.");
        }
    }


    public String getSubject() {

        return subject;
    }

    public void setSubject(String subject) {

        this.subject = subject;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Subject: %s\r\n"
                            +"%s\r\n"
                            +"\r\n.\r\n", this.getSubject(), this.getMessage());
    }

}
