package edu.njit.cs656.fall.njitmobilemailer.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.widget.EditText;

import edu.njit.cs656.fall.njitmobilemailer.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout relative = new RelativeLayout(this);

        Button submitLogin = new Button(this);
        submitLogin.setId(0);
        submitLogin.setGravity(Gravity.BOTTOM);
        submitLogin.setHeight(120);
        submitLogin.setWidth(240);

        relative.addView(submitLogin);

        setContentView(relative);

    }
}
