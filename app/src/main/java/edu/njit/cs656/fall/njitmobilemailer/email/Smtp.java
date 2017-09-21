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

public class Smtp {

    private static final String TAG = "SMTP";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    private SSLSocket mailServerSocket;

    public boolean ConnectServer() {

        try {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            mailServerSocket = (SSLSocket) socketFactory.createSocket("smtp.gmail.com", 465);

            return mailServerSocket.isConnected();
        } catch ( IOException e ) {
            return false;
        }
    }

    public boolean isConnected() {
        return mailServerSocket.isConnected();
    }

    public String encode(String text) {

        byte[] base64EncodedAuthenticationBytes = Base64.encodeBase64(text.getBytes());

        String encodedData = new String(base64EncodedAuthenticationBytes);

        return encodedData;
    }

    public String decode(String text) {
        byte[] base64DecodedAuthenticationBytes = Base64.decodeBase64(text.getBytes());

        String decodedData = new String(base64DecodedAuthenticationBytes);

        return decodedData;
    }

    private boolean authenticateUser(String username, String password, BufferedWriter writer, BufferedReader reader) {

        try {
            //encode(username), encode(password));

            writer.write(String.format("AUTH LOGIN\r\n"));
            writer.flush();

            String response = reader.readLine();
            System.out.println(response);

            writer.write(String.format("%s\r\n", encode(username)));
            writer.flush();

            response = reader.readLine();
            System.out.println(response);

            writer.write(String.format("%s\r\n", encode(password)));
            writer.flush();

            response = reader.readLine();
            System.out.println(response);

            if(response.indexOf("Accepted") < 0 ) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }
    public String sendMail(Mail email) {
        try {
            InputStream readStream = mailServerSocket.getInputStream();
            InputStreamReader readerStream = new InputStreamReader(readStream);
            BufferedReader reader = new BufferedReader(readerStream);

            OutputStream writeStream = mailServerSocket.getOutputStream();
            OutputStreamWriter writerStream = new OutputStreamWriter(writeStream);
            BufferedWriter writer = new BufferedWriter(writerStream);

            String message = reader.readLine();
            System.out.println(message);

            writer.write("EHLO njit.edu\r\n");
            writer.flush();

            while(((message = reader.readLine()) != null)) {
                System.out.println(message);
                if(!reader.ready()) break;
            }


            // authenticate user
            if(authenticateUser(USERNAME, PASSWORD, writer, reader)) {
                System.out.println("Authentication successful");

                writer.write(String.format("MAIL FROM: <%s>\r\n", email.getFromClient()));
                writer.flush();

                message = reader.readLine();
                System.out.println(message);

                writer.write(String.format("RCPT TO: <%s>\r\n", email.getToClient()));
                writer.flush();

                message = reader.readLine();
                System.out.println(message);

                writer.write("DATA\r\n");
                writer.flush();

                message = reader.readLine();
                System.out.println(message);

                writer.write(email.toString());
                writer.flush();

                message = reader.readLine();
                System.out.println(message);

            } else {
                System.out.println("Authentication failed.");
            }

            reader.close();
            readerStream.close();
            readStream.close();

            writer.close();
            writerStream.close();
            writeStream.close();
        } catch ( IOException e ) {
            return null;
        }
        return null;
    }

    public boolean DestroyServer() {
        try {
            mailServerSocket.close();

            return !mailServerSocket.isConnected();
        } catch ( IOException e ) {
            return false;
        }

    }
}
