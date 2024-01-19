package com.example.st_jokoa;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.st_jokoa.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
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

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // URL de tu API
            String apiUrl = "http://10.23.28.192:8012/datuak_berritu";

            // Crea una instancia de la clase SQLite
            DatabaseHelper dbHelper = new DatabaseHelper(context);

            // Abre la base de datos en modo lectura
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT izena, abizena, nan, puntuaketa, denbora FROM " + DatabaseHelper.TABLE_TXAPELKETA, null);

            // Crear un array JSON para almacenar los datos
            JSONArray jsonArray = new JSONArray();

            // Formatea los datos como un array JSON
            while (cursor.moveToNext()) {
                JSONObject jsonObject = new JSONObject();

                // Verificar la existencia de la columna "izena"
                int nanIndex = cursor.getColumnIndex("nan");
                if (nanIndex != -1) {
                    jsonObject.put("nan", cursor.getString(nanIndex));
                }

                // Verificar la existencia de la columna "izena"
                int izenaIndex = cursor.getColumnIndex("izena");
                if (izenaIndex != -1) {
                    jsonObject.put("izena", cursor.getString(izenaIndex));
                }

                // Verificar la existencia de la columna "abizena"
                int abizenaIndex = cursor.getColumnIndex("abizena");
                if (abizenaIndex != -1) {
                    jsonObject.put("abizena", cursor.getString(abizenaIndex));
                }

                // Verificar la existencia de la columna "puntuazioa"
                int puntuazioaIndex = cursor.getColumnIndex("puntuaketa");
                if (puntuazioaIndex != -1) {
                    jsonObject.put("puntuaketa", cursor.getInt(puntuazioaIndex));
                }

                // Verificar la existencia de la columna "puntuazioa"
                int denboraIndex = cursor.getColumnIndex("denbora");
                if (denboraIndex != -1) {
                    jsonObject.put("denbora", cursor.getInt(denboraIndex));
                }

                // Agregar el objeto JSON al array
                jsonArray.put(jsonObject);
            }

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
            // Puedes leer la respuesta aquí si es necesario


            // Desconectar
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
