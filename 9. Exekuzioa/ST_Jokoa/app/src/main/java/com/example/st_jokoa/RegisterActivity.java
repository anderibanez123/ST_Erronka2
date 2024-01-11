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
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNewUsername = findViewById(R.id.editTextNewUsername);
        editTextNewDNI = findViewById(R.id.editTextNewDNI);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        databaseHelper = new DatabaseHelper(this);
    }

    public void register(View view) {
        String newUsername = editTextNewUsername.getText().toString();
        String newDNI = editTextNewDNI.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();

        if (registerUser(newUsername, newDNI, newPassword)) {
            Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
            // Redirigir a MainActivity después de registrar
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish(); // Cerrar RegisterActivity para que no pueda regresar con el botón de retroceso
        } else {
            Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
        }
    }

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
}
