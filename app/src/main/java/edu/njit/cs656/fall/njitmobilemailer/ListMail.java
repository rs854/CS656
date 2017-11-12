package edu.njit.cs656.fall.njitmobilemailer;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import edu.njit.cs656.fall.njitmobilemailer.auth.Authentication;
import edu.njit.cs656.fall.njitmobilemailer.email.Mail;
import edu.njit.cs656.fall.njitmobilemailer.email.MailComparator;


public class ListMail extends AppCompatActivity {

    private static final String TAG = "ListMail";
    private TableRow[] tableRows; // TODO need to convert this from TableRow to something more appropriate
    private TableLayout.LayoutParams tableParams;
    private TableRow.LayoutParams rowParams;
    private LayoutInflater inflater;
    private LinearLayout linearLayout;
    private ListMail reference;
    private List<Mail> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mail);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout_emails);

        reference = this;
        inflater = LayoutInflater.from(this);

        linearLayout.post(new Runnable() {
            @Override
            public void run() {
                new Updater().execute(new Authentication());
            }
        });
    }

    protected void setMessages(List<Mail> messages) {
        this.messages = messages;
    }

    protected void drawMessages(final List<Mail> messages) {
        tableRows = new TableRow[messages.size()];
        tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        rowParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);

        for (int i = 0; i < messages.size(); i++) {
            tableRows[i] = new TableRow(this);
            View customView = inflater.inflate(R.layout.data_item, tableRows[i], false);
            TextView textView = new TextView(this);
            textView.setHeight(200);
            final int temp = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ReadMail.class);
                    intent.putExtra("subject", messages.get(temp).getSubject());
                    startActivity(intent);
                }
            });
            textView.setPadding(10, 5, 10, 5);
            textView.setTextColor((i % 2 == 0) ? Color.DKGRAY : Color.BLACK);
            textView.setText(messages.get(i).getSubject() + "\n" + "Received on: " + messages.get(i).getDate().toString());
            //((TextView) customView.findViewById(R.id.view_subject)).setText(messages.get(i).getSubject());
            tableRows[i].setBackgroundColor((i % 2 == 0) ? Color.LTGRAY : Color.GRAY);

            tableRows[i].addView(textView, rowParams);
            linearLayout.addView(tableRows[i], tableParams);
        }
    }
    public class Updater extends AsyncTask<Authentication, Integer, List<Mail>> {

        @Override
        protected void onPostExecute(List<Mail> messages) {
            super.onPostExecute(messages);
            drawMessages(messages);
        }

        @Override
        protected List<Mail> doInBackground(Authentication... authentication) {

            try {
                Session emailSession = Session.getDefaultInstance(authentication[0].getIMAPProperties());

                Store store = emailSession.getStore("imaps");

                store.connect("imap.gmail.com", authentication[0].getUsername(), authentication[0].getPassword());

                Folder emailFolder = store.getFolder("INBOX");

                emailFolder.open(Folder.READ_WRITE);

                Message[] messages = emailFolder.getMessages();
                List<Mail> messageList = new ArrayList<Mail>();


                for (int i = 0; i < messages.length; i++) {
                    Mail mail = new Mail();
                    try {
                        mail.setSubject(messages[i].getSubject());
                        mail.setMessage(messages[i].getContent().toString());
                        mail.setDate(messages[i].getReceivedDate());
                    } catch (MessagingException | IOException e) {
                        e.printStackTrace();
                    }
                    messageList.add(mail);
                }


                emailFolder.close(false);
                store.close();

                Collections.sort(messageList, new MailComparator());
                Collections.reverse(messageList);
                return messageList;
            } catch (MessagingException e) {
                return null;
            }
        }
    }
}
