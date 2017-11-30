package edu.njit.cs656.fall.njitmobilemailer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadMail extends AppCompatActivity {

    private Toolbar toolbar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.read_mail_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_mail);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extra = getIntent().getExtras();

        TextView subject = (TextView) findViewById(R.id.subject_textView);
        subject.setText(extra.getString("subject"));

        TextView from = (TextView) findViewById(R.id.from_textView);
        from.setText(extra.getString("from"));

        TextView dateTextView = (TextView) findViewById(R.id.datetime_textView);
        // Convert date from Long > Date > String
        Date date = new Date();
        date.setTime(extra.getLong("date", -1));
        SimpleDateFormat formatter = new SimpleDateFormat("M/d h:mm a");
        String dateString = formatter.format(date);
        dateTextView.setText(dateString);

        WebView content = (WebView) findViewById(R.id.content_webView);
        content.loadData(extra.getString("content"), "text/html; charset=utf-8", "UTF-8");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                // TODO: Add code to delete the email here
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }

        return true;
    }
}
