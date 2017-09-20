package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugene on 9/19/2017.
 */

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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

    public void sendMessage() {

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
