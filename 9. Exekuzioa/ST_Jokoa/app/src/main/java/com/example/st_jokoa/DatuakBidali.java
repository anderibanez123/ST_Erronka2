package com.example.st_jokoa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.st_jokoa.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatuakBidali extends AsyncTask<Void, Void, Void> {


    private final Context context;

    public DatuakBidali(Context context) {
        this.context = context;
    }

    @SuppressLint("Range")
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // URL de tu API
            String apiUrl = "http://10.23.28.190:8012/datuak_berritu";

            // Crea una instancia de la clase SQLite
            DatabaseHelper dbHelper = new DatabaseHelper(context);

            // Abre la base de datos en modo lectura
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT id, izena, abizena, nan, puntuaketa, denbora FROM " + DatabaseHelper.TABLE_TXAPELKETA, null);

            // Crear un array JSON para almacenar los datos
            JSONArray jsonArray = new JSONArray();

            // Formatea los datos como un array JSON
            while (cursor.moveToNext()) {
                JSONObject jsonObject = new JSONObject();

                // Agregar los datos al objeto JSON
                jsonObject.put("id", cursor.getInt(cursor.getColumnIndex("id")));
                jsonObject.put("izena", cursor.getString(cursor.getColumnIndex("izena")));
                jsonObject.put("abizena", cursor.getString(cursor.getColumnIndex("abizena")));
                jsonObject.put("nan", cursor.getString(cursor.getColumnIndex("nan")));
                jsonObject.put("puntuaketa", cursor.getInt(cursor.getColumnIndex("puntuaketa")));
                jsonObject.put("denbora", cursor.getInt(cursor.getColumnIndex("denbora")));

                // Agregar el objeto JSON al array
                jsonArray.put(jsonObject);
            }

            cursor.close();
            db.close();

            // Guardar el JSON en un archivo local
            saveJsonToFile(jsonArray.toString());

            // Establecer la conexión
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);

            // Enviar los datos como cuerpo de la solicitud
            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonArray.toString().getBytes());
            os.flush();
            os.close();

            // Obtener la respuesta si es necesario
            int responseCode = urlConnection.getResponseCode();

            // Desconectar
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void saveJsonToFile(String jsonString) {
        try {
            File file = new File(context.getFilesDir(), "json_data.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonString);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
