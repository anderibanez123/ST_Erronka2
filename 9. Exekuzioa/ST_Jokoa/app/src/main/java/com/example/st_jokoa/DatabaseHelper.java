package com.example.st_jokoa;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// DatabaseHelper.java
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    //taula "erabiltzaileak"
    private static final String TABLE_USERS = "erabiltzaileak";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "izena";
    private static final String COLUMN_NAME2 = "abizena";
    private static final String COLUMN_PASSWORD = "pasahitza";
    private static final String COLUMN_NAN = "nan";
    //taula "galderak"
    private static final String COLUMN_ID2 = "id";
    private static final String TABLE_GALDERAK = "galderak";
    private static final String COLUMN_GALDERA = "galdera";
    private static final String COLUMN_ERANTZUN_ZUZENA = "erantzunZuzena";
    private static final String COLUMN_ERANTZUN_OKERRA_1 = "erantzunOkerra1";
    private static final String COLUMN_ERANTZUN_OKERRA_2 = "erantzunOkerra2";
    private static final String COLUMN_ERANTZUN_OKERRA_3 = "erantzunOkerra3";
    //taula "txapelketa"
    private static final String TABLE_TXAPELKETA = "txapelketa";
    private static final String COLUMN_ID3 = "id";
    private static final String COLUMN_IZENA = "izena";
    private static final String COLUMN_ABIZENA = "abizena";
    private static final String COLUMN_DENBORA = "denbora";
    private static final String COLUMN_NAN2 = "nan";
    private static final String COLUMN_PUNTUAKETA = "puntuaketa";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_NAME2 + " TEXT, " +
                    COLUMN_NAN + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT);";

    private static final String CREATE_TABLE_GALDERAK =
            "CREATE TABLE " + TABLE_GALDERAK + " (" +
                    COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GALDERA + " TEXT, " +
                    COLUMN_ERANTZUN_ZUZENA + " TEXT, " +
                    COLUMN_ERANTZUN_OKERRA_1 + " TEXT, " +
                    COLUMN_ERANTZUN_OKERRA_2 + " TEXT, " +
                    COLUMN_ERANTZUN_OKERRA_3 + " TEXT);";
    private static final String CREATE_TABLE_TXAPELKETA =
            "CREATE TABLE " + TABLE_TXAPELKETA + " (" +
                    COLUMN_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IZENA + " TEXT, " +
                    COLUMN_ABIZENA + " TEXT, " +
                    COLUMN_NAN2 + " TEXT, " +
                    COLUMN_PUNTUAKETA + " INTEGER, " +
                    COLUMN_DENBORA + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_GALDERAK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GALDERAK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TXAPELKETA);
        onCreate(db);
    }


    @SuppressLint("Range")
    public List<Galdera> getRandomGalderak(int count) {
        List<Galdera> galderaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Random random = new Random();

        // Obtener todas las preguntas
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GALDERAK, null);

        if (cursor.moveToFirst()) {
            do {
                Galdera galdera = new Galdera();
                galdera.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                galdera.setGaldera(cursor.getString(cursor.getColumnIndex(COLUMN_GALDERA)));
                galdera.setErantzunZuzena(cursor.getString(cursor.getColumnIndex(COLUMN_ERANTZUN_ZUZENA)));
                galdera.setErantzunOkerra1(cursor.getString(cursor.getColumnIndex(COLUMN_ERANTZUN_OKERRA_1)));
                galdera.setErantzunOkerra2(cursor.getString(cursor.getColumnIndex(COLUMN_ERANTZUN_OKERRA_2)));
                galdera.setErantzunOkerra3(cursor.getString(cursor.getColumnIndex(COLUMN_ERANTZUN_OKERRA_3)));

                // Establecer aleatoriamente el índice de la respuesta correcta
                int correctAnswerIndex = random.nextInt(4); // 4 opciones posibles
                galdera.setCorrectAnswerIndex(correctAnswerIndex);

                galderaList.add(galdera);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Barajar aleatoriamente la lista y devolver el número especificado de preguntas
        Collections.shuffle(galderaList, random);
        return galderaList.subList(0, count);
    }
    public void updateTxapelketaTable(int puntuaketa, long denbora, String nan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PUNTUAKETA, puntuaketa);
        values.put(COLUMN_DENBORA, denbora);

        // Puedes personalizar la condición WHERE según tus necesidades
        db.update(TABLE_TXAPELKETA, values, COLUMN_NAN2 + "=?", new String[]{nan});
        db.close();
    }






    public String getUsernameColumnName() {
        return COLUMN_NAME;
    }
    public String getUsernameColumnName2() {
        return COLUMN_NAME2;
    }
    public String getColumnNan() {
        return COLUMN_NAN;
    }

    public String getPasswordColumnName() {
        return COLUMN_PASSWORD;
    }
    public String getColumnPuntuaketa(){
        return COLUMN_PUNTUAKETA;
    }
    public String getTableName() {
        return TABLE_USERS;
    }
    public String getTableName2() {
        return TABLE_TXAPELKETA;
    }
    public String getColumnGaldera(int i){
        return COLUMN_GALDERA;
    }
    public String getColumnErantzunZuzena(int i){
        return COLUMN_ERANTZUN_ZUZENA;
    }
    public String getColumnErantzunOkerra1(int i){
        return COLUMN_ERANTZUN_OKERRA_1;
    }
    public String getColumnErantzunOkerra2(int i){
        return COLUMN_ERANTZUN_OKERRA_2;
    }
    public String getColumnErantzunOkerra3(int i){
        return COLUMN_ERANTZUN_OKERRA_3;
    }

    public int getQuestionCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_GALDERAK, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return count;
    }
}



