package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Erabiltzaileak erregistratzeko pantaila.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNewUsername;  // Erabiltzaile izena sartzeko EditText bidezko objektua
    private EditText editTextNewDNI;       // Erabiltzailearen DNIa sartzeko EditText bidezko objektua
    private EditText editTextNewPassword;  // Pasahitza sartzeko EditText bidezko objektua
    private EditText baieztatzePassword;   // Pasahitza baieztatzeko EditText bidezko objektua
    private EditText editTextAbizena;      // Abizena sartzeko EditText bidezko objektua
    private DatabaseHelper databaseHelper;  // Datu-basea laguntzeko klasea
    private TextView dniLetraLabel;        // DNI-ren letra kalkulatzeko etiketa

    private ImageView atzera_botoia;       // Atzera botoia


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextAbizena = findViewById(R.id.Abizena);  // Abizena sartzeko EditText objektua
        editTextAbizena.setFilters(new InputFilter[]{new LettersOnlyInputFilter()});

        editTextNewUsername = findViewById(R.id.editTextNewUsername); // Erabiltzaile izena sartzeko EditText objektua
        editTextNewUsername.setFilters(new InputFilter[]{new LettersOnlyInputFilter()});

        editTextNewDNI = findViewById(R.id.editTextNewDNI);  // Erabiltzailearen DNIa sartzeko EditText objektua
        editTextNewDNI.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8), new DigitsKeyListener()});

        editTextNewPassword = findViewById(R.id.editTextNewPassword);  // Pasahitza sartzeko EditText objektua
        baieztatzePassword = findViewById(R.id.BaieztatuPasahitza);    // Pasahitza baieztatzeko EditText objektua

        atzera_botoia = findViewById(R.id.image_back); // Atzera botoia

        databaseHelper = new DatabaseHelper(this);  // Datu-basea laguntzeko klasea sortu

        dniLetraLabel = findViewById(R.id.dniLetraLabel);

        // Agrega un TextWatcher al EditText del DNI
        editTextNewDNI.addTextChangedListener(new TextWatcher() {
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

        // Agrega un onTouchListener al layout principal para cerrar el teclado cuando tocas la pantalla
        View mainLayout = findViewById(R.id.register_activity);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Itxi teklatua pantaila ukitzen denean
                hideKeyboard();
                return false;
            }
        });

        // Atzera botoiari sakatzerakoan
        atzera_botoia.setOnClickListener(view -> {
            // Pantaila ixteko
            finish();
        });
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

        String newUsername = capitalizeFirstLetter(editTextNewUsername.getText().toString());  // Erabiltzaile izena lortu
        String newAbizena = capitalizeFirstLetter(editTextAbizena.getText().toString());  // Abizena lortu
        String dniZbk = editTextNewDNI.getText().toString().toUpperCase();  // Erabiltzailearen DNIa lortu
        String newPassword = editTextNewPassword.getText().toString(); // Pasahitza lortu
        String baieztatuPassword = baieztatzePassword.getText().toString(); // Baieztatzeko pasahitza

        // DNI Osoa
        String newDNI = dniZbk + dniLetraLabel.getText().toString().toUpperCase();

        if (dniZbk.length() == 8){

            if (newUsername.length() > 1 && newAbizena.length() > 1){

                if (newPassword.length() >= 3){

                    if (newPassword.equals(baieztatuPassword)){

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

                    }else{
                        Toast.makeText(this, "Pasahitzak ez dira berdinak", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Pasahitza gutxienez 3 karaktere izan behar ditu.", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "Izena eta Abizena egokiak sartu behar dira.", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "DNIa ez da egokia.", Toast.LENGTH_SHORT).show();
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

    // Txapelketa erregistratu
    private boolean registerTxapelketa(String izena, String abizena, String dni) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();   // Idatzeko datu-basea ireki
        ContentValues values = new ContentValues();
        values.put(databaseHelper.getUsernameColumnName(), izena);
        values.put(databaseHelper.getColumnNan(), dni);
        values.put(databaseHelper.getUsernameColumnName2(), abizena);

        long result = db.insert(databaseHelper.getTableName2(), null, values);

        db.close();

        return result != -1;
    }


    // FILTROAK ETA KONPROBAKETAK
    private static class LettersOnlyInputFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // Bakarrik letrak baimendu
            for (int i = start; i < end; i++) {
                if (!Character.isLetter(source.charAt(i))) {
                    return "";
                }
            }
            return null; // Aldaketak onartu
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

    // Lehenengo letra maiuskula bihurtzeko metodoa
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    // Teklatu birtuala ezkutatzeko metodoa
    private void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }


}
