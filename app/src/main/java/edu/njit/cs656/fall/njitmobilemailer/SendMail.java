package edu.njit.cs656.fall.njitmobilemailer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import edu.njit.cs656.fall.njitmobilemailer.email.Mail;
import edu.njit.cs656.fall.njitmobilemailer.email.Send;

public class SendMail extends AppCompatActivity {

    private Toolbar toolbar;

    private String getTo() {
        EditText item = (EditText) findViewById(R.id.to_editText);
        return item.getText().toString();
    }

    private String getBody() {
        EditText item = (EditText) findViewById(R.id.body_editText);
        return item.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.send_mail_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        View sendButton = findViewById(R.id.action_send);
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_send:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Mail letter = new Mail();
                        letter.setSubject(((EditText) findViewById(R.id.subject_editText)).getText().toString());
                        letter.setMessage(((EditText) findViewById(R.id.body_editText)).getText().toString());
                        try {
                            letter.setFromClient("et24@njit.edu");
                            letter.setToClient(((EditText) findViewById(R.id.to_editText)).getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Send.Email(letter);
                    }
                }).start();
                break;
            default:
                break;
        }

        return true;
    }
}
