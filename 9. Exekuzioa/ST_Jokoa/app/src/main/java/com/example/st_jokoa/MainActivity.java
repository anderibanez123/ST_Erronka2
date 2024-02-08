package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static EditText editTextDNI;
    private EditText editTextPassword;
    private DatabaseHelper databaseHelper;
    private Button laguntza;
    private TextView laguntza_textua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDNI = findViewById(R.id.editTextDNI);
        editTextPassword = findViewById(R.id.editTextPassword);
        laguntza = findViewById(R.id.btn_laguntza); // laguntza botoia
        laguntza_textua = findViewById(R.id.laguntza_textua);

        databaseHelper = new DatabaseHelper(this);

        // Laguntza textua irakusteko funtzioari hots egiteko botoia
        laguntza.setOnClickListener(view -> {

            showLaguntzaText();

        });

        View mainLayout = findViewById(R.id.main_activity);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Teklatua itxi pantaila ikutzerakoan
                hideKeyboard();
                return false;
            }
        });

    }

    // Laguntza textua irakusteko funtzioa
    private void showLaguntzaText() {

        // TextView sakatzerakoan
        laguntza_textua.setVisibility(View.VISIBLE);

        // 8 segundo eta gero invisible egiteko
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                laguntza_textua.setVisibility(View.INVISIBLE);

            }

        }, 5000);

    }

    // Login egiteko funtzioa
    public void login(View view) {
        String dni = editTextDNI.getText().toString().toUpperCase();
        String password = editTextPassword.getText().toString();

        // Testerako erabiltzailearekin sartu
        if (dni.equals("") && password.equals("") || dni.contains("test") && password.contains("test") || dni.contains("Test") && password.contains("Test")){
            startActivity(new Intent(MainActivity.this, MenuActivity.class));

        } // Erabiltzaileekin sartu
        else{

            if (authenticateUser(dni, password)) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            } else {
                Toast.makeText(this, "Erabiltzaile edo pasahitz ez zuzenak", Toast.LENGTH_SHORT).show();
            }

        }

    }

    // Erabiltzaileak registratzeko pantaila ireki
    public void register(View view) {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

    // Erabiltzailea ondo al dagoen konprobatu
    private boolean authenticateUser(String nan, String pasahitza) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(
                databaseHelper.getTableName(),
                null,
                databaseHelper.getColumnNan() + "=? AND " + databaseHelper.getPasswordColumnName() + "=?",
                new String[]{nan, pasahitza},
                null,
                null,
                null
        );

        boolean result = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return result;
    }

    // Teklatua ixteko pantaila barruan ikutu
    private void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

}
