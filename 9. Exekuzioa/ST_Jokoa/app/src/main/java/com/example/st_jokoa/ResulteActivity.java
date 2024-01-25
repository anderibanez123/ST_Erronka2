package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ScrollView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResulteActivity extends AppCompatActivity {

    ScrollView rankingScrollView;

    TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulte);

        // Datuak automatikoki aktualizatzen zaiatu
        DatuakBidali datuakBidali = new DatuakBidali(getApplicationContext());
        datuakBidali.execute();

        textView = findViewById(R.id.textView);

        rankingScrollView = findViewById(R.id.rankingTaula);

        int score = getIntent().getIntExtra("Emaitza",0);

        // Puntuazioa negatiboa ez izateko
        if (score < 0){
            textView.setText("Puntuazioa : " + 0);
        }else {
            textView.setText("Puntuazioa : " + score);
        }

        // Realizar la solicitud HTTP
        new FetchDataTask().execute();
    }

    private class FetchDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://10.23.28.190:8012/lortu_datuak");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jokalariak = jsonObject.getJSONArray("Jokalariak");
                displayData(jokalariak);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayData(JSONArray jokalariak) throws JSONException {
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        // Crear la fila de encabezados
        TableRow headerRow = new TableRow(this);
        TextView izenaTitleTextView = new TextView(this);
        TextView abizenaTitleTextView = new TextView(this);
        TextView puntuaketaTitleTextView = new TextView(this);

        // Establecer los títulos de las columnas
        izenaTitleTextView.setText("Izena");
        abizenaTitleTextView.setText("Abizena");
        puntuaketaTitleTextView.setText("Puntuaketa");

        // Establecer el estilo de los títulos
        izenaTitleTextView.setTextColor(Color.WHITE);
        abizenaTitleTextView.setTextColor(Color.WHITE);
        puntuaketaTitleTextView.setTextColor(Color.WHITE);
        izenaTitleTextView.setPadding(20, 0, 20, 0);
        abizenaTitleTextView.setPadding(20, 0, 20, 0);
        puntuaketaTitleTextView.setPadding(20, 0, 20, 0);

        // Agregar los títulos a la fila de encabezados
        headerRow.addView(izenaTitleTextView);
        headerRow.addView(abizenaTitleTextView);
        headerRow.addView(puntuaketaTitleTextView);

        // Agregar la fila de encabezados a la tabla
        tableLayout.addView(headerRow);

        // Agregar los datos
        for (int i = 0; i < jokalariak.length(); i++) {

            JSONObject jokalaria = jokalariak.getJSONObject(i);
            String izena = jokalaria.getString("izena");
            String abizena = jokalaria.getString("abizena");
            String puntuaketa = jokalaria.getString("puntuaketa");

            TableRow row = new TableRow(this);
            TextView izenaTextView = new TextView(this);
            TextView abizenaTextView = new TextView(this);
            TextView puntuaketaTextView = new TextView(this);

            izenaTextView.setText(izena);
            abizenaTextView.setText(abizena);
            puntuaketaTextView.setText(puntuaketa);

            // Establecer el color del texto en blanco
            izenaTextView.setTextColor(Color.WHITE);
            abizenaTextView.setTextColor(Color.WHITE);
            puntuaketaTextView.setTextColor(Color.WHITE);

            // Establecer espaciado entre columnas
            izenaTextView.setPadding(20, 0, 20, 0);
            abizenaTextView.setPadding(20, 0, 20, 0);
            puntuaketaTextView.setPadding(20, 0, 20, 0);

            row.addView(izenaTextView);
            row.addView(abizenaTextView);
            row.addView(puntuaketaTextView);

            tableLayout.addView(row);
        }

        rankingScrollView.removeAllViews();
        rankingScrollView.addView(tableLayout);
    }




}
