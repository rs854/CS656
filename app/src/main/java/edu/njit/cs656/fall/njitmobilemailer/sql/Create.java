package edu.njit.cs656.fall.njitmobilemailer.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.njit.cs656.fall.njitmobilemailer.email.Mail;

/**
 * Created by Eugen on 11/2/2017.
 */

public class Create extends SQLiteOpenHelper {
    public Create(Context context) {
        super(context, "Mail", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MailDatabase.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int prevVersion, int newVersion) {
        sqLiteDatabase.execSQL(MailDatabase.DROP_QUERY);
        sqLiteDatabase.execSQL(MailDatabase.CREATE_QUERY);
    }

    public void createMail(Mail letter) {
        this.getWritableDatabase().rawQuery(MailDatabase.INSERT_QUERY, new String[]{String.valueOf(letter.getSubject())});
    }


}
