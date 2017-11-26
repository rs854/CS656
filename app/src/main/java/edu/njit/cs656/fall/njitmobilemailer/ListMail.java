package edu.njit.cs656.fall.njitmobilemailer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import edu.njit.cs656.fall.njitmobilemailer.auth.Authentication;
import edu.njit.cs656.fall.njitmobilemailer.email.Mail;
import edu.njit.cs656.fall.njitmobilemailer.interfaces.Listener;

public class ListMail extends AppCompatActivity {

    public static final String TAG = "ListMail";
    private List<Mail> localMail = new ArrayList<Mail>();
    private List<Mail> remoteMail = new ArrayList<Mail>();
    private List<TextView> elementList = new ArrayList<TextView>();
    private LinearLayout linearLayout;

    // callback for setting up indices
    // instead of using final int
    public void setUpListener(int index, Listener listener) {
        listener.setUp(index);
    }


    public void reDrawLocalMail() {
        ListView list = new ListView(this);
        ListView.LayoutParams listView = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT);

        localMail.addAll(remoteMail);
        for (int i = 0; i < remoteMail.size(); i++) {
            elementList.add(new TextView(this));
            setUpListener(((localMail.size() - remoteMail.size()) + i), new Listener() {
                @Override
                public void setUp(int index) {
                    // gets the element at the offset
                    TextView textView = elementList.get(index);
                    textView.setHeight(200);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), ReadMail.class);
                            intent.putExtra("subject", localMail.get(index).getSubject());
                            intent.putExtra("content", localMail.get(index).getMessage()); // TODO change this to content
                            intent.putExtra("from", localMail.get(index).getFromClient());
                            startActivity(intent);
                        }
                    });
                    textView.setPadding(10, 5, 10, 5);
                    textView.setTextColor(Color.BLACK);
                    textView.setText(localMail.get(index).getSubject().toString() + "\n" + "Received on: " + localMail.get(index).getDate().toString());

                    linearLayout.addView(textView, 0, listView);

                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mail);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout_emails);
        LayoutInflater inflater = LayoutInflater.from(this);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_BEGINNING | LinearLayout.SHOW_DIVIDER_END);
        Window window = this.getWindow();
        window.setStatusBarColor(Color.RED);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean check = false;
                while (true) {
                    try {
                        remoteMail = getMessages(new Authentication(), check);

                        // The check is to get all initial emails loaded into the structure
                        // Then it is set to true so that only future unread emails are loaded
                        // to improve throughput.
                        if (!check) check = true;

                        if (remoteMail.size() > 0) {

                            // Add drawing step here
                            linearLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    reDrawLocalMail();
                                }
                            });
                        }

                        // Thread sleep time of 5 seconds
                        // TODO we should change this to a more appropriate number for production
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        Log.v(TAG, e.getMessage());
                    }
                }
            }
        }).start();

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
                    messages[i].setFlag(Flags.Flag.SEEN, true);
                    mail.setSubject(messages[i].getSubject());

                    // need to check what type of content we have
                    // TODO potentially this can be refactored to a recursive function.
                    if (messages[i].isMimeType("text/plain") || messages[i].isMimeType("text/html")) {
                        mail.setMessage(messages[i].getContent().toString());
                    } else if (messages[i].isMimeType("multipart/*")) {

                        // extract the mime-multipart content
                        MimeMultipart mimeContent = (MimeMultipart) messages[i].getContent();
                        StringBuilder tmp = new StringBuilder();
                        for (int k = 0; k < mimeContent.getCount(); k++) {
                            BodyPart bodyContent = mimeContent.getBodyPart(k);
                            if (bodyContent.isMimeType("text/plain")) {
                                tmp.append(bodyContent.getContent().toString());
                                break;
                            } else if (bodyContent.isMimeType("text/html")) {
                                tmp.append(Jsoup.parse(bodyContent.getContent().toString()).text());
                            }
                        }
                        mail.setMessage(tmp.toString());
                    } else {
                        Log.v(TAG, "Message(" + i + ") is a type: " + messages[i].getContentType());
                        mail.setMessage("NULL");
                    }

                    mail.setDate(messages[i].getReceivedDate());
                } catch (MessagingException | IOException e) {
                    Log.v(TAG, e.getMessage());
                }
                messageList.add(mail);
            }

            emailFolder.close(true);
            store.close();

            return messageList;
        } catch (Exception e) {
            Log.v(TAG, e.getMessage());
            return null;
        }
    }

}
