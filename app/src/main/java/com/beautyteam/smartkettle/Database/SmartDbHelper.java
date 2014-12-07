package com.beautyteam.smartkettle.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SmartDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "smartkettle";

    private static final String TEXT_TYPE = " TEXT";
    private static final String CHAR_TYPE = " CHAR(100)";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String NOT_NULL = " NOT NULL";
    private static final String NULL = " NULL";
    private static final String UNIQUE = " UNIQUE";
    private static final String COMMA_SEP = ", ";
    private static final String FOREIGN_KEY = " FOREIGN KEY(" + NewsContract.NewsEntry.COLUMN_NAME_DEVICE + ") REFERENCES " +
        DevicesContract.DevicesEntry.TABLE_NAME + "("+ DevicesContract.DevicesEntry.COLUMN_NAME_DEVICES_ID + ")";

    private static final String SQL_CREATE_NEWS =
        "CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME + " (" +
            NewsContract.NewsEntry._ID + INTEGER_TYPE + " PRIMARY KEY, " +
            NewsContract.NewsEntry.COLUMN_NAME_NEWS_ID + INTEGER_TYPE + NOT_NULL + UNIQUE + COMMA_SEP +
            NewsContract.NewsEntry.COLUMN_NAME_DEVICE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
            NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE + CHAR_TYPE + NOT_NULL + COMMA_SEP +
            NewsContract.NewsEntry.COLUMN_NAME_EVENT_DATE_BEGIN + CHAR_TYPE + NULL + COMMA_SEP +
            NewsContract.NewsEntry.COLUMN_NAME_SHORT_NEWS + CHAR_TYPE + NOT_NULL + COMMA_SEP +
            NewsContract.NewsEntry.COLUMN_NAME_LONG_NEWS + TEXT_TYPE + NOT_NULL + COMMA_SEP +
            NewsContract.NewsEntry.COLUMN_NAME_TRANSACTION_STATE + INTEGER_TYPE + NULL + COMMA_SEP +
            FOREIGN_KEY +
        ");";

    private static final String SQL_DELETE_NEWS =
        "DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME + ";";

    private static final String SQL_CREATE_DEVICES =
        "CREATE TABLE " + DevicesContract.DevicesEntry.TABLE_NAME + " (" +
            DevicesContract.DevicesEntry._ID + INTEGER_TYPE + " PRIMARY KEY, " +
            DevicesContract.DevicesEntry.COLUMN_NAME_DEVICES_ID + INTEGER_TYPE + NOT_NULL + UNIQUE + COMMA_SEP +
            DevicesContract.DevicesEntry.COLUMN_NAME_TYPE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
            DevicesContract.DevicesEntry.COLUMN_NAME_TITLE + CHAR_TYPE + NOT_NULL + COMMA_SEP +
            DevicesContract.DevicesEntry.COLUMN_NAME_SUMMARY + CHAR_TYPE + NULL + COMMA_SEP +
            DevicesContract.DevicesEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + NULL + COMMA_SEP +
            DevicesContract.DevicesEntry.COLUMN_NAME_TRANSACTION_STATE + INTEGER_TYPE + NULL +
        ");";

    private static final String SQL_DELETE_DEVICES =
        "DROP TABLE IF EXISTS " + DevicesContract.DevicesEntry.TABLE_NAME + ";";

    public SmartDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NEWS);
        db.execSQL(SQL_CREATE_DEVICES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_NEWS);
        db.execSQL(SQL_DELETE_DEVICES);
        onCreate(db);
    }
}
