package edu.njit.cs656.fall.njitmobilemailer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import edu.njit.cs656.fall.njitmobilemailer.auth.Authentication;
import edu.njit.cs656.fall.njitmobilemailer.email.Mail;
import edu.njit.cs656.fall.njitmobilemailer.email.MailComparator;

public class ListMailAlternate extends AppCompatActivity {

    public static final String TAG = "ListMailAlternate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread initialDraw = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean check = false;
                while (true) {
                    try {
                        if (check) {
                            Log.v(TAG, "CHECKING EVERY 5 seconds now.");
                            List<Mail> test = getMessages(new Authentication(), check);
                            if (test != null) for (int i = 0; i < test.size(); i++) {
                                Log.v(TAG, test.get(i).toString());
                            }
                        } else {
                            List<Mail> test = getMessages(new Authentication(), check);
                            if (test != null) for (int i = 0; i < test.size(); i++) {
                                Log.v(TAG, test.get(i).toString());
                            }
                            check = true;
                        }
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        Log.v(TAG, e.getMessage());
                    }
                }
            }
        });

        initialDraw.start();
    }

    public List<Mail> getMessages(Authentication authentication, boolean checked) {
        try {
            Session emailSession = Session.getDefaultInstance(authentication.getIMAPProperties());
            Store store = emailSession.getStore("imaps");
            store.connect("imap.gmail.com", authentication.getUsername(), authentication.getPassword());
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);
            Message messages[] = emailFolder.getMessages();
            List<Mail> messageList = new ArrayList<Mail>();
            for (int i = 0; i < messages.length; i++) {
                if (checked && messages[i].isSet(Flags.Flag.SEEN)) continue;

                Mail mail = new Mail();
                try {
                    mail.setSubject(messages[i].getSubject());
                    mail.setMessage(messages[i].getContent().toString());
                    mail.setDate(messages[i].getReceivedDate());
                } catch (MessagingException | IOException e) {
                    Log.v(TAG, e.getMessage());
                }
                messageList.add(mail);
            }
            Collections.sort(messageList, new MailComparator());
            Collections.reverse(messageList);

            emailFolder.close(true);
            store.close();

            return messageList;
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
            return null;
        }
    }

}
