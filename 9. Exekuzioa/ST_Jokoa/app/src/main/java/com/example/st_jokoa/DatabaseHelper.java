package com.example.st_jokoa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// DatabaseHelper.java
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "erabiltzaileak";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "erabiltzailea";
    private static final String COLUMN_PASSWORD = "pasahitza";
    private static final String COLUMN_NAN = "nan";


    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_NAN + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public String getUsernameColumnName() {
        return COLUMN_USERNAME;
    }
    public String getColumnNan() {
        return COLUMN_NAN;
    }

    public String getPasswordColumnName() {
        return COLUMN_PASSWORD;
    }
    public String getTableName() {
        return TABLE_NAME;
    }
}



