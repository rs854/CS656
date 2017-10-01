package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugen on 9/20/2017.
 */

public class Imap extends Connection {

    private static final String TAG = Imap.class.getSimpleName();

    public Imap() {
        super();
        setHost("imap.gmail.com");
        setPort(993);
    }

    public void listEmails() throws Exception {
        String response;

        ConnectServer();

        response = readData();

        writeData("a101 LOGIN et24@njit.edu ");

        response = readData();

        if(response.indexOf("Success") < 0) {
            DestroyServer();

            throw new Exception("ERROR: Unable to authenticate.");

        } else {

            writeData("a102 LIST \"\" *");

            response = readData();

            writeData("a103 SELECT INBOX");

            response = readData();

            writeData("a FETCH 40 BODY[]");

            response = readData();

        }

        DestroyServer();

    }
}
