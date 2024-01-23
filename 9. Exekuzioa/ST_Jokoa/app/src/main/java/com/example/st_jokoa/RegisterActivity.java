package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNewUsername;  // Erabiltzaile izena sartzeko EditText bidezko objektua
    private EditText editTextNewDNI;       // Erabiltzailearen DNIa sartzeko EditText bidezko objektua
    private EditText editTextNewPassword;  // Pasahitza sartzeko EditText bidezko objektua
    private EditText editTextAbizena;     // Abizena sartzeko EditText bidezko objektua
    private DatabaseHelper databaseHelper; // Datu-basea laguntzeko klasea

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextAbizena = findViewById(R.id.Abizena);  // Abizena sartzeko EditText objektua
        editTextNewUsername = findViewById(R.id.editTextNewUsername);  // Erabiltzaile izena sartzeko EditText objektua
        editTextNewDNI = findViewById(R.id.editTextNewDNI);  // Erabiltzailearen DNIa sartzeko EditText objektua
        editTextNewPassword = findViewById(R.id.editTextNewPassword);  // Pasahitza sartzeko EditText objektua
        databaseHelper = new DatabaseHelper(this);  // Datu-basea laguntzeko klasea sortu
    }

    // Erregistroa egin nahi duzun DNIaren kontua existitzen bada
    public boolean konprobatuDNI(String newDNI) {

        SQLiteDatabase db = databaseHelper.getReadableDatabase();  // Irakurtzeko datu-basea ireki
        boolean existe = false;  // Existitzen al duen ala ez jakiteko erabiliko den boolearra

        // COUNT erabiliz SQL kontsulta definitu
        String query = "SELECT count(*) FROM erabiltzaileak WHERE nan = ?";

        Cursor cursor = null;

        try {
            // Kontsulta exekutatu newDNI direktuaren balioarekin
            cursor = db.rawQuery(query, new String[]{newDNI});

            // Konteoa lortu
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                existe = count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cursor eta datu-basea ixteko finally blokea
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return existe;
    }

    public void register(View view) {

        String newUsername = editTextNewUsername.getText().toString().toUpperCase();  // Erabiltzaile izena lortu
        String newAbizena = editTextAbizena.getText().toString().toUpperCase();  // Abizena lortu
        String newDNI = editTextNewDNI.getText().toString().toUpperCase();  // Erabiltzailearen DNIa lortu
        String newPassword = editTextNewPassword.getText().toString();  // Pasahitza lortu

        // HEMEN EGIAZTU ALA ERREGISTATUTA DAGOEN, ETA ONDOAN JAKIN-MEZUA ERAKUTSI
        boolean existe = konprobatuDNI(newDNI);

        if (existe){

            Toast.makeText(this, "Erabiltzailea existitzen da.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Arazoak badituzu jarri gurekin kontaktuan.", Toast.LENGTH_SHORT).show();

        } else {

            if (registerUser(newUsername, newDNI, newPassword)) {

                Toast.makeText(this, "Erabiltzailea erregistratu da.", Toast.LENGTH_SHORT).show();
                registerTxapelketa(newUsername, newAbizena, newDNI);

                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();

            } else {

                Toast.makeText(this, "Errorea erabiltzailea erregistratzen", Toast.LENGTH_SHORT).show();

            }
        }
    }

    // Erabiltzailea datu-base barruan sartu
    private boolean registerUser(String username, String dni, String password) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();  // Idatzeko datu-basea ireki
        ContentValues values = new ContentValues();
        values.put(databaseHelper.getUsernameColumnName(), username);
        values.put(databaseHelper.getColumnNan(), dni);
        values.put(databaseHelper.getPasswordColumnName(), password);

        long result = db.insert(databaseHelper.getTableName(), null, values);

        db.close();

        return result != -1;
    }

    private boolean registerTxapelketa(String izena, String abizena, String dni) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();  // Idatzeko datu-basea ireki
        ContentValues values = new ContentValues();
        values.put(databaseHelper.getUsernameColumnName(), izena);
        values.put(databaseHelper.getColumnNan(), dni);
        values.put(databaseHelper.getUsernameColumnName2(), abizena);

        long result = db.insert(databaseHelper.getTableName2(), null, values);

        db.close();

        return result != -1;
    }
}
