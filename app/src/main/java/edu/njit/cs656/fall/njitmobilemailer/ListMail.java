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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import edu.njit.cs656.fall.njitmobilemailer.auth.Authentication;
import edu.njit.cs656.fall.njitmobilemailer.email.Mail;

public class ListMail extends AppCompatActivity {

    public static final String TAG = "ListMail";
    private List<Mail> localMail = new ArrayList<Mail>();
    private List<Mail> remoteMail = new ArrayList<Mail>();
    private LinearLayout linearLayout;

    public void reDrawLocalMail() {
        ListView list = new ListView(this);
        ListView.LayoutParams listView = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT);

        localMail.addAll(remoteMail);
        for (int i = 0; i < remoteMail.size(); i++) {
            TextView textView = new TextView(this);
            textView.setHeight(200);
            final int temp = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ReadMail.class);
                    intent.putExtra("subject", remoteMail.get(temp).getSubject());
                    startActivity(intent);
                }
            });
            textView.setPadding(10, 5, 10, 5);
            textView.setTextColor(Color.BLACK);
            textView.setText(remoteMail.get(i).getSubject() + "\n" + "Received on: " + remoteMail.get(i).getDate().toString());

            linearLayout.addView(textView, 0, listView);

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
                    mail.setMessage(messages[i].getContent().toString());
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
