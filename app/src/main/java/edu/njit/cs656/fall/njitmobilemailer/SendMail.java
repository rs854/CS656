package edu.njit.cs656.fall.njitmobilemailer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import edu.njit.cs656.fall.njitmobilemailer.email.Mail;
import edu.njit.cs656.fall.njitmobilemailer.email.Send;

public class SendMail extends AppCompatActivity {


    private String getTo() {
        EditText item = (EditText) findViewById(R.id.to_editText);
        return item.getText().toString();
    }

    private String getBody() {
        EditText item = (EditText) findViewById(R.id.body_editText);
        return item.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        View sendButton = findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Mail letter = new Mail();
                        letter.setSubject("TEST SUBJECT");
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

            }
        });
    }
}
