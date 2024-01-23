package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNewUsername;
    private EditText editTextNewDNI;
    private EditText editTextNewPassword;
    private EditText editTextAbizena;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextAbizena =findViewById(R.id.Abizena);
        editTextNewUsername = findViewById(R.id.editTextNewUsername);
        editTextNewDNI = findViewById(R.id.editTextNewDNI);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        databaseHelper = new DatabaseHelper(this);
    }

    // Erregistroa egin nahi duzun DNIaren kontua existitzen bada
    public boolean konprobatuDNI(String newDNI){



        //existitzen bada true bueltatu
        return false;

    }

    public void register(View view) {

        String newUsername = editTextNewUsername.getText().toString().toUpperCase();
        String newAbizena = editTextAbizena.getText().toString().toUpperCase();
        String newDNI = editTextNewDNI.getText().toString().toUpperCase();
        String newPassword = editTextNewPassword.getText().toString();

        // COMPROBAR AQUI SI ESTA REGISTRADO O NO, Y DESPUES MOSTRAR EL MENSAJE CORRESPONDIENTE
        boolean existe = konprobatuDNI(newDNI);

        if (existe){

            

        }else {

            if (registerUser(newUsername, newDNI, newPassword)) {

                Toast.makeText(this, "Erabiltzailea erregistratu da.", Toast.LENGTH_SHORT).show();
                registerTxapelketa(newUsername,newAbizena,newDNI);

                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();

            } else {

                Toast.makeText(this, "Errorea erabiltzailea erregistratzen", Toast.LENGTH_SHORT).show();

            }
        }

    }

    // Erabiltzailea datu base barruan sartu
    private boolean registerUser(String username, String dni, String password) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(databaseHelper.getUsernameColumnName(), username);
        values.put(databaseHelper.getColumnNan(), dni);
        values.put(databaseHelper.getPasswordColumnName(), password);


        long result = db.insert(databaseHelper.getTableName(), null, values);

        db.close();

        return result != -1;
    }


    private boolean registerTxapelketa(String izena,String abizena, String dni) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(databaseHelper.getUsernameColumnName(),izena);
        values.put(databaseHelper.getColumnNan(), dni);
        values.put(databaseHelper.getUsernameColumnName2(),abizena);



        long result = db.insert(databaseHelper.getTableName2(), null, values);

        db.close();

        return result != -1;
    }
}
