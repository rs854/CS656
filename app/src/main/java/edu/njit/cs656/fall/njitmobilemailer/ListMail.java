package edu.njit.cs656.fall.njitmobilemailer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.util.Log;



import org.w3c.dom.Text;


public class ListMail extends AppCompatActivity {

    private static final String TAG = "ListMail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mail);

        //TableLayout tableLayout = new TableLayout(); (TableLayout) findViewById(R.id.list_email_table);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.list_email_table);

        // Loop Start
        final TableRow[] tableRows = new TableRow[10];

        final TableLayout.LayoutParams tableParams =
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        final TableRow.LayoutParams rowParams =
                new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);

        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < 10; i++) {
            tableRows[i] = new TableRow(this);
            final View customView = inflater.inflate(R.layout.data_item, tableRows[i], false);
            ((TextView) customView.findViewById(R.id.view_subject)).setText("SUBJECT");
            tableRows[i].addView(customView, rowParams);

            tableLayout.addView(tableRows[i], tableParams);

            Log.v(TAG, "ADDED INDEX: " + i);
        }
        //TextView textViewEmailBrief = (TextView) findViewById(R.id.email_brief_content);
        //TextView textViewSubject = (TextView) findViewById(R.id.email_subject);
        //TextView textViewFrom = (TextView) findViewById(R.id.email_from);

        //textViewEmailBrief.setText("TEST EMAIL BRIEF");
        //textViewSubject.setText("TEST EMAIL SUBJECT");
        //textViewFrom.setText("TEST EMAIL FROM");

        //tableRow.addView(textViewEmailBrief);
        //tableRow.addView(textViewSubject);
        //tableRow.addView(textViewFrom);

        //tableLayout.addView(tableRow);
        // Loop End


    }
}
