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

    public void ConnectServer() {

        try {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket mailServer = (SSLSocket) socketFactory.createSocket("smtp.gmail.com", 465);

            InputStream readStream = mailServer.getInputStream();
            InputStreamReader readerStream = new InputStreamReader(readStream);
            BufferedReader reader = new BufferedReader(readerStream);
            OutputStream writeStream = mailServer.getOutputStream();

            //Log.d(TAG, "All Streams Established.");

            System.out.println("Streams Established.");


            String firstLine = reader.readLine();
            System.out.println(firstLine);

            readStream.close();
            writeStream.close();
            mailServer.close();

            System.out.println("Streams Closed.");
            //Log.d(TAG, "All Streams Closed.");
        } catch ( IOException e ) {

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

}
