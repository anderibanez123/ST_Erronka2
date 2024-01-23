package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNewUsername;  // Erabiltzaile izena sartzeko EditText bidezko objektua
    private EditText editTextNewDNI;       // Erabiltzailearen DNIa sartzeko EditText bidezko objektua
    private EditText editTextNewPassword;  // Pasahitza sartzeko EditText bidezko objektua
    private EditText baieztatzePassword;
    private EditText editTextAbizena;     // Abizena sartzeko EditText bidezko objektua
    private DatabaseHelper databaseHelper; // Datu-basea laguntzeko klasea

    TextView dniLetraLabel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextAbizena = findViewById(R.id.Abizena);  // Abizena sartzeko EditText objektua
        editTextNewUsername = findViewById(R.id.editTextNewUsername);  // Erabiltzaile izena sartzeko EditText objektua
        editTextNewDNI = findViewById(R.id.editTextNewDNI);  // Erabiltzailearen DNIa sartzeko EditText objektua
        editTextNewDNI.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8), new DigitsKeyListener()});
        editTextNewPassword = findViewById(R.id.editTextNewPassword);  // Pasahitza sartzeko EditText objektua
        baieztatzePassword = findViewById(R.id.BaieztatuPasahitza);
        databaseHelper = new DatabaseHelper(this);  // Datu-basea laguntzeko klasea sortu
        dniLetraLabel = findViewById(R.id.dniLetraLabel);

        // Agrega un TextWatcher al EditText
        editTextNewDNI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No necesitas hacer nada antes de cambiar el texto
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No necesitas hacer nada mientras el texto está cambiando
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Llama a la función para calcular la letra del DNI y actualiza la etiqueta
                calcularLetraDNI(editable.toString());
            }
        });

    }

    // Función para calcular la letra del DNI y actualizar la etiqueta
    private void calcularLetraDNI(String dniNumeros) {
        if (dniNumeros.length() == 8) {
            // Convierte los primeros 7 caracteres a números y calcula la letra
            int numerosDNI = Integer.parseInt(dniNumeros);
            char letraDNI = calcularLetraDNI(numerosDNI);

            // Actualiza la etiqueta con la letra del DNI calculada
            dniLetraLabel.setText(String.valueOf(letraDNI));

        } else {

        }
    }

    // Función para calcular la letra del DNI
    private char calcularLetraDNI(int numerosDNI) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int indiceLetra = numerosDNI % 23;
        return letras.charAt(indiceLetra);
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

    // Método para capitalizar solo la primera letra
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
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
