package edu.njit.cs656.fall.njitmobilemailer.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eugen on 11/2/2017.
 */

public class Update extends SQLiteOpenHelper {
    public Update(Context context) {
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
}
