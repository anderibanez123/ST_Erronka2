package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
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

/**
 * Klasea jokoen emaitzak eta ranking-a erakusteko.
 */
public class ResulteActivity extends AppCompatActivity {

    ScrollView rankingScrollView; // Ranking-a ikusteko scroll view-a

    TextView puntuazioaView;  // Puntuazioa erakusteko testu bidea
    Button hasieraBTN;  // Berrabiarazi botoia
    TextView adi;  // Mezuak erakusteko testu bidea

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulte);

        // Datuak automatikoki eguneratu
        DatuakBidali datuakBidali = new DatuakBidali(getApplicationContext());
        datuakBidali.execute();

        puntuazioaView = findViewById(R.id.textView);
        rankingScrollView = findViewById(R.id.rankingTaula);
        hasieraBTN = findViewById(R.id.btn_restart);
        adi = findViewById(R.id.adi);

        int score = getIntent().getIntExtra("Emaitza", 0); // "Emaitza" parametroa hartu eta score aldagaian gorde

        // Puntuazioa ez izateko negatiboa
        if (score < 0) {
            puntuazioaView.setText("Puntuazioa : " + 0);
        } else {
            puntuazioaView.setText("Puntuazioa : " + score);
        }

        // Berrabiarazi botoia konfiguratu
        hasieraBTN.setOnClickListener(
                restart -> {
                    finish();
                }
        );

        // Itxi botoia konfiguratu
        findViewById(R.id.btn_itxi).setOnClickListener(
                restart -> {
                    finish();
                }
        );

        int menutikDator = getIntent().getIntExtra("menutik", 0); // "menutik" parametroa hartu eta menutikDator aldagaian gorde

        if (menutikDator == 1) {  // menutikDator aldagaia 1 bada, puntuazioa ezkutatu
            puntuazioaView.setVisibility(View.INVISIBLE);
        }

        // HTTP eskaera burutu
        new FetchDataTask().execute();
    }

    /**
     * Klasea burutzeko datuak eskuratzeko AsyncTask-a.
     */
    private class FetchDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();  // Datuen iturburuaren bidezko karaktere-katea sortu
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

            if (result.isEmpty()) {
                adi.setVisibility(View.VISIBLE); //APIarekin konexiorik ez badu, mezua irakutsi
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(result);  // JSON objektua sortu
                JSONArray jokalariak = jsonObject.getJSONArray("Jokalariak");  // "Jokalariak" gakoarekin JSON arraya hartu
                displayData(jokalariak);  // Datuak erakutsi
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Datuak taulan erakutsi.
     *
     * @param jokalariak Jokalarien datuak daukan JSONArray-a.
     * @throws JSONException JSON datuak prozesatzerakoan errorea badago.
     */
    private void displayData(JSONArray jokalariak) throws JSONException {

        TableLayout tableLayout = new TableLayout(this);  // Taula diseinatu
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        // Goiburuko lerroak
        TableRow headerRow = new TableRow(this);  // Taularen goiburuko lerroa
        TextView izenaTitleTextView = new TextView(this);
        TextView abizenaTitleTextView = new TextView(this);
        TextView puntuaketaTitleTextView = new TextView(this);

        // Zutabeen izenburuak
        izenaTitleTextView.setText("IZENA");
        abizenaTitleTextView.setText("ABIZENA");
        puntuaketaTitleTextView.setText("PUNTUAKETA");

        // Izenean estiloa
        izenaTitleTextView.setTextColor(Color.WHITE);
        abizenaTitleTextView.setTextColor(Color.WHITE);
        puntuaketaTitleTextView.setTextColor(Color.WHITE);

        izenaTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        abizenaTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        puntuaketaTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        izenaTitleTextView.setTypeface(null, Typeface.BOLD);
        abizenaTitleTextView.setTypeface(null, Typeface.BOLD);
        puntuaketaTitleTextView.setTypeface(null, Typeface.BOLD);

        izenaTitleTextView.setPadding(20, 0, 20, 0);
        abizenaTitleTextView.setPadding(20, 0, 20, 0);
        puntuaketaTitleTextView.setPadding(20, 0, 20, 0);

        headerRow.addView(izenaTitleTextView);
        headerRow.addView(abizenaTitleTextView);
        headerRow.addView(puntuaketaTitleTextView);

        tableLayout.addView(headerRow);

        // Datuak
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

            izenaTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            abizenaTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            puntuaketaTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            // Establecer espaciado entre columnas
            izenaTextView.setPadding(20, 0, 20, 0);
            abizenaTextView.setPadding(20, 0, 20, 0);
            puntuaketaTextView.setPadding(20, 0, 0, 0);

            row.addView(izenaTextView);
            row.addView(abizenaTextView);
            row.addView(puntuaketaTextView);

            tableLayout.addView(row);

        }

        rankingScrollView.removeAllViews();
        rankingScrollView.addView(tableLayout);
    }




}
