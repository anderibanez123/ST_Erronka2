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

    // Eraikitzailea: DatuakBidali klasearen instantzia sortzeko erabiliko den metodoa
    public DatuakBidali(Context context) {
        this.context = context;
    }

    @SuppressLint("Range")
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // URL API barrotik sartzeko
            String apiUrl = "http://10.23.28.190:8012/datuak_berritu";

            // URL API kanpotik sartzeko
            //String apiUrl = "https://www.pythonanywhere.com:8012/datuak_berritu";

            // SQLite klasearen instantzia sortu
            DatabaseHelper dbHelper = new DatabaseHelper(context);

            // Datu-basea irakurtzeko moduan ireki
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Txapelketako datu guztiak hautatu
            Cursor cursor = db.rawQuery("SELECT izena, abizena, nan, puntuaketa, denbora FROM " + DatabaseHelper.TABLE_TXAPELKETA, null);

            // JSON array bat sortu datuak gordetzeko
            JSONArray jsonArray = new JSONArray();

            // Datuak JSON array-era formatu egiten du
            while (cursor.moveToNext()) {
                JSONObject jsonObject = new JSONObject();

                // Datuak JSON objektuan sartu
                //jsonObject.put("id", cursor.getInt(cursor.getColumnIndex("id")));
                jsonObject.put("izena", cursor.getString(cursor.getColumnIndex("izena")));
                jsonObject.put("abizena", cursor.getString(cursor.getColumnIndex("abizena")));
                jsonObject.put("nan", cursor.getString(cursor.getColumnIndex("nan")));
                jsonObject.put("puntuaketa", cursor.getInt(cursor.getColumnIndex("puntuaketa")));
                jsonObject.put("denbora", cursor.getInt(cursor.getColumnIndex("denbora")));

                // JSON array-era objektua gehitu
                jsonArray.put(jsonObject);
            }

            // Cursor eta datu-basea ixteko
            cursor.close();
            db.close();

            // JSON-a fitxategi batean gorde
            saveJsonToFile(jsonArray.toString());

            // Konexioa ezarri
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);

            // JSON-a bidaltzeko
            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonArray.toString().getBytes());
            os.flush();
            os.close();

            // Erantzuna behar bada, lortu
            int responseCode = urlConnection.getResponseCode();

            // Konexioa itxi
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // JSON-a fitxategi batera gordeko duen metodoa
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
