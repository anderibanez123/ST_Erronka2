package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static EditText editTextDNI; // Cambiado de username a DNI
    private EditText editTextPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDNI = findViewById(R.id.editTextDNI); // Cambiado de username a DNI
        editTextPassword = findViewById(R.id.editTextPassword);
        databaseHelper = new DatabaseHelper(this);
    }

    public void login(View view) {
        String dni = editTextDNI.getText().toString(); // Cambiado de username a DNI
        String password = editTextPassword.getText().toString();

        if (authenticateUser(dni, password)) {
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
        } else {
            Toast.makeText(this, "Erabiltzaile edo pasahitz ez zuzenak", Toast.LENGTH_SHORT).show();
        }
    }
    public void register(View view) {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }

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

    // Otros métodos según sea necesario
}
