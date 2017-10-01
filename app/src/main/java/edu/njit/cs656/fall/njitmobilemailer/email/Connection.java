package edu.njit.cs656.fall.njitmobilemailer.email;

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

/**
 * Created by eugeneturgil on 9/23/17.
 */


public abstract class Connection {

    public static final String TAG = Connection.class.getSimpleName();

    // "smtp.gmail.com:465"
    // "imap.gmail.com:993"

    private String host;
    private int port;

    private SSLSocket serverSocket;
    private InputStream readStream;
    private InputStreamReader readerStream;
    private BufferedReader reader;

    private OutputStream writeStream;
    private OutputStreamWriter writerStream;
    private BufferedWriter writer;

    public Connection() {
    }

    protected String getHost() {
        return this.host;
    }

    protected void setHost(final String host) {
        this.host = host;
    }

    protected int getPort() {
        return this.port;
    }

    protected void setPort(final int port) {
        this.port = port;
    }

    protected boolean ConnectServer() {

        try {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            serverSocket = (SSLSocket) socketFactory.createSocket(getHost(), getPort());

            if(serverSocket.isConnected()) {
                readStream = serverSocket.getInputStream();
                readerStream = new InputStreamReader(readStream);
                reader = new BufferedReader(readerStream);

                writeStream = serverSocket.getOutputStream();
                writerStream = new OutputStreamWriter(writeStream);
                writer = new BufferedWriter(writerStream);

                return true;
            }
        } catch ( IOException e ) {
            return false;
        }
        return false;
    }

    protected boolean DestroyServer() {
        try {
            reader.close();
            readerStream.close();
            readStream.close();

            writer.close();
            writerStream.close();
            writeStream.close();

            serverSocket.close();

            return !serverSocket.isConnected();
        } catch ( IOException e ) {
            return false;
        }
    }

    public boolean isConnected() {
        return serverSocket.isConnected();
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

    protected String readData() {
        String message;
        StringBuilder response = new StringBuilder();

        try {
            while(((message = reader.readLine()) != null)) {
                System.out.println(String.format("READ: %s", message));
                response.append(message);

                if (!reader.ready()) break;
            }
        } catch(IOException e) {
            return null;
        }

        return response.toString();
    }

    protected void writeData(String data) {
        try {
            System.out.println(String.format("WRITE: %s", data));

            writer.write(String.format("%s\r\n", data));
            writer.flush();
        } catch(IOException e) {
            return;
        }
    }
}
