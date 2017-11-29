package edu.njit.cs656.fall.njitmobilemailer.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eugen on 11/2/2017.
 */

public class Read extends SQLiteOpenHelper {

    public Read(Context context) {
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

    public Cursor getProductCursor() {
        return this.getWritableDatabase().rawQuery(MailDatabase.SElECT_QUERY, null);
    }
}
