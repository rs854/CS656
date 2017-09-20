package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugene on 9/19/2017.
 */

import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.nio.channels.Pipe;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Smtp {

    private static final String TAG = "SMTP";

    private SSLSocket mailServerSocket;

    public boolean ConnectServer() {

        try {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            mailServerSocket = (SSLSocket) socketFactory.createSocket("smtp.gmail.com", 465);

            return mailServerSocket.isConnected();
        } catch ( IOException e ) {
            return false;
        }

        // connect using ssl socket
        // send EHLO njit.edu
        // auth plain base64_encoded (\000email\@domain.com\000password)
        // mmehvqtechoalpqi
        // mail from: <et24@njit.edu>
        // rcpt to: mail from: <et24@njit.edu>
        // data
        // then data
    }

    private boolean authenticateUser(String username, String password, BufferedWriter writer, BufferedReader reader) {
        StringBuilder authenticationString = new StringBuilder();
        authenticationString.append("\\000");
        authenticationString.append(username.substring(0, username.indexOf('@') -1));
        authenticationString.append(username.substring( username.indexOf('@'), username.length()));
        authenticationString.append("\\000");
        authenticationString.append(password);

        byte[] stringArray = new byte[authenticationString.length()];
        String base64EncodedAuthenticationString = Base64.encodeToString(authenticationString.toString().getBytes(), 0);

        try {
            writer.write(String.format("auth plain %s", base64EncodedAuthenticationString));
            String response = reader.readLine();

            if(response.indexOf("Accepted") < 0 ) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }
    public String sendMail(String to, String from, String data) {
        try {
            InputStream readStream = mailServerSocket.getInputStream();
            InputStreamReader readerStream = new InputStreamReader(readStream);
            BufferedReader reader = new BufferedReader(readerStream);

            OutputStream writeStream = mailServerSocket.getOutputStream();
            OutputStreamWriter writerStream = new OutputStreamWriter(writeStream);
            BufferedWriter writer = new BufferedWriter(writerStream);

            String message = reader.readLine();
            writer.write("EHLO njit.edu");

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
