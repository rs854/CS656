package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugen on 10/13/2017.
 */


import android.util.Log;

import java.io.IOException;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import edu.njit.cs656.fall.njitmobilemailer.auth.Authentication;

public class Check {

    private static final String TAG = "CheckMail";

    public void TestEmail() {
        try {
            Authentication authentication = new Authentication();
            System.out.println(TAG + "Authentication set up.");

            Session emailSession = Session.getDefaultInstance(authentication.getPOP3Properties());
            System.out.println(TAG + "Properties set up.");

            Store store = emailSession.getStore("pop3s");
            System.out.println(TAG + "Store set up.");

            store.connect("pop.gmail.com", authentication.getUsername(), authentication.getPassword());
            System.out.println(TAG + "Connection set up.");

            Folder emailFolder = store.getFolder("INBOX");
            System.out.println(TAG + "Inbox set up.");

            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();

            for (int i = 0, n = messages.length; i < n; i++) {
                System.out.println(TAG + "---------------------------------");
                System.out.println(TAG + "Email Number " + (i + 1));
                System.out.println(TAG + "Subject: " + messages[i].getSubject());
                System.out.println(TAG + "From: " + messages[i].getFrom()[0]);
                System.out.println(TAG + "Text: " + messages[i].getContent().toString());
            }

            emailFolder.close(false);
            store.close();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    public void Email() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Authentication authentication = new Authentication();

                    Session emailSession = Session.getDefaultInstance(authentication.getPOP3Properties());

                    Store store = emailSession.getStore("pop3s");

                    store.connect("pop.gmail.com", authentication.getUsername(), authentication.getPassword());

                    Folder emailFolder = store.getFolder("INBOX");

                    emailFolder.open(Folder.READ_ONLY);

                    Message[] messages = emailFolder.getMessages();

                    for (int i = 0, n = messages.length; i < n; i++) {
                        Log.v(TAG, "---------------------------------");
                        Log.v(TAG, "Email Number " + (i + 1));
                        Log.v(TAG, "Subject: " + messages[i].getSubject());
                        Log.v(TAG, "From: " + messages[i].getFrom()[0]);
                        Log.v(TAG, "Text: " + messages[i].getContent().toString());
                    }

                    emailFolder.close(false);
                    store.close();
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
