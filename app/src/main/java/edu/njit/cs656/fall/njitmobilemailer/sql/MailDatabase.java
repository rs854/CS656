package edu.njit.cs656.fall.njitmobilemailer.sql;

/**
 * Created by Eugen on 11/2/2017.
 */

import android.provider.BaseColumns;

public class MailDatabase implements BaseColumns {
    public static final String SUBJECT = "subject";
    public static final String TABLE_NAME = "emails";

    public static final String CREATE_QUERY = "create table if not exists " + TABLE_NAME + " ( " +
            _ID + " INTEGER, " +
            SUBJECT + " TEXT )";

    public static final String DROP_QUERY = "drop table " + TABLE_NAME;
    public static final String SElECT_QUERY = "select * from " + TABLE_NAME;
    public static final String INSERT_QUERY = "insert into " + TABLE_NAME + " (" + SUBJECT + ") values (?)";

}
