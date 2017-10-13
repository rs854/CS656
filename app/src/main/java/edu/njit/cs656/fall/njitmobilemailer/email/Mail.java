package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugen on 9/20/2017.
 */

import java.util.*;

public class Mail {

    private String fromClient;
    private String toClient;
    private String subject;
    private List<String> ccClient;
    private String message;

    public String getFromClient() {

        return fromClient;
    }

    public void setFromClient(String fromClient) {

        this.fromClient = fromClient;
    }


    public String getToClient() {

        return toClient;
    }

    public void setToClient(String toClient) {
        this.toClient = toClient;
    }

    public List<String> getCcClient() {

        return ccClient;
    }

    public void addCcClient(String ccClient) {

        this.ccClient.add(ccClient);
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
