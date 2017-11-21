package edu.njit.cs656.fall.njitmobilemailer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ReadMail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_mail);

        Bundle extra = getIntent().getExtras();
        TextView subject = (TextView) findViewById(R.id.subject);
        subject.setText(extra.getString("subject"));

        TextView content = (TextView) findViewById(R.id.content);
        content.setText(extra.getString("content"));
    }
}
