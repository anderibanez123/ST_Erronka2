package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
    private TextView dniLetraLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDNI = findViewById(R.id.editTextDNI);
        editTextPassword = findViewById(R.id.editTextPassword);
        laguntza = findViewById(R.id.btn_laguntza); // laguntza botoia
        laguntza_textua = findViewById(R.id.laguntza_textua);
        dniLetraLabel = findViewById(R.id.dniLetraLabel);

        databaseHelper = new DatabaseHelper(this);

        // Laguntza textua irakusteko funtzioari hots egiteko botoia
        laguntza.setOnClickListener(view -> {

            showLaguntzaText();

        });

        // Agrega un TextWatcher al EditText del DNI
        editTextDNI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ezer egin beharrezkoa ez da testua aldatu baino lehen
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Ezer egin beharrezkoa ez da testua aldatzen den bitartean
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // DNI-ren letra kalkulatzeko eta etiketa eguneratzeko funtzioa deitu
                calcularLetraDNI(editable.toString());
            }
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

        // Sarrerak hartu
        String dniLetra = dniLetraLabel.getText().toString().toUpperCase();
        String dniZenbakia = editTextDNI.getText().toString().toUpperCase();
        String dni = dniZenbakia + dniLetra; // DNI LETRA ETA DNI ZENBAKIA BATERA JARRI
        String password = editTextPassword.getText().toString();

        // Offline edo online dagoen jakiteko menuaren barruan
        int modua = 1;

        // Testerako erabiltzailearekin sartu
        if (dni.equals("") && password.equals("") || dni.contains("test") && password.contains("test") || dni.contains("Test") && password.contains("Test")){

            // Sarrerak garbitu
            editTextDNI.setText("");
            dniLetraLabel.setText("");
            editTextPassword.setText("");

            modua = 1;
            startActivity(new Intent(MainActivity.this, MenuActivity.class).putExtra("valor", modua));


        } // Erabiltzaileekin sartu
        else{

            if (authenticateUser(dni, password)) {

                // Sarrerak garbitu
                editTextDNI.setText("");
                dniLetraLabel.setText("");
                editTextPassword.setText("");

                modua = 0;
                startActivity(new Intent(MainActivity.this, MenuActivity.class).putExtra("valor", modua));
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

    // DNI-ren letra kalkulatzeko funtzioa eta etiketa eguneratzeko
    private void calcularLetraDNI(String dniZenbakiak) {
        if (dniZenbakiak.length() == 8) {
            // Lehenengo 7 karaktereak zenbaki bihurtu eta letra kalkulatu
            int zenbakiakDNI = Integer.parseInt(dniZenbakiak);
            char letraDNI = calcularLetraDNI(zenbakiakDNI);

            // Etiketa eguneratu kalkulatutako DNI-aren letra batekin
            dniLetraLabel.setText(String.valueOf(letraDNI));

        }
    }

    // DNI-ren letra kalkulatzeko funtzioa
    private char calcularLetraDNI(int numerosDNI) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int indiceLetra = numerosDNI % 23;
        return letras.charAt(indiceLetra);
    }

}
