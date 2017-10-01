package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugene on 9/19/2017.
 */

import org.apache.commons.codec.binary.Base64;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Smtp extends Connection {

    private static final String TAG = Smtp.class.getSimpleName();

    public Smtp() {
        super();
        setHost("smtp.gmail.com");
        setPort(465);
    }

    public void send(Mail mail) throws Exception {
        String response;

        ConnectServer();

        response = readData();

        writeData("EHLO njit.edu");

        response = readData();

        writeData("AUTH LOGIN");

        response = readData();

        writeData(encode("et24@njit.edu"));

        response = readData();

        writeData(encode(""));

        response = readData();

        if(response.indexOf("Accepted") < 0) {
            DestroyServer();

            throw new Exception("ERROR: Unable to authenticate.");

        } else {
            writeData(String.format("MAIL FROM: <%s>", mail.getFromClient()));

            response = readData();

            writeData(String.format("RCPT TO: <%s>", mail.getToClient()));

            response = readData();

            writeData("DATA");

            response = readData();

            writeData(mail.toString());

            response = readData();
        }

        DestroyServer();
    }
}
