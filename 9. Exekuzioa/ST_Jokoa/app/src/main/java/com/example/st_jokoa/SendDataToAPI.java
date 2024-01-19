package com.example.st_jokoa;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendDataToAPI extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // URL de tu API
            URL url = new URL("http://10.23.28.190:8012/datuak_berritu");

            // Objetua sortu
            JSONObject jsonDatos = new JSONObject();

            jsonDatos.put("nan", "123456");
            jsonDatos.put("izena", "John");
            jsonDatos.put("abizena", "Doe");
            jsonDatos.put("denbora", "10");
            jsonDatos.put("puntuaketa", "100");

            // HTTP Ezarri
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);

            // Escribir el JSON en el cuerpo de la solicitud
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.writeBytes(jsonDatos.toString());
            outputStream.flush();
            outputStream.close();

            // Obtener la respuesta del servidor
            InputStream inputStream = urlConnection.getInputStream();

            // Konexioa itxi
            urlConnection.disconnect();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
