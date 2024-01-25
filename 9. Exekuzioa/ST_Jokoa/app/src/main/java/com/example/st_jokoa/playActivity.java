package com.example.st_jokoa;

import static com.example.st_jokoa.MainActivity.editTextDNI;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class playActivity extends AppCompatActivity {
    private static final int POINTS_PER_CORRECT_ANSWER = 100;
    private static final int MAX_SECONDS_PER_QUESTION = 30;
    private CountDownTimer globalTimer;
    private long totalTimeElapsedMillis = 0;

    DatabaseHelper dbHelper;
    TextView cpt_question, text_question, timerTextView;
    Button btn_choose1, btn_choose2, btn_choose3, btn_choose4, btn_next;

    ImageView atzera_botoia;

    int currentQuestion = 0;
    int scorePlayer = 0;
    boolean isclickBtn = false;
    String valueChoose = "";
    Button btn_click;
    Galdera currentGaldera;
    CountDownTimer countDownTimer;
    int maxQuestionsToShow = 10;  // Definir galdera kopuru maximoa
    int currentSecondsRemaining = MAX_SECONDS_PER_QUESTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        cpt_question = findViewById(R.id.cpt_question);
        text_question = findViewById(R.id.text_question);
        timerTextView = findViewById(R.id.timerTextView);

        btn_choose1 = findViewById(R.id.btn_choose1);
        btn_choose2 = findViewById(R.id.btn_choose2);
        btn_choose3 = findViewById(R.id.btn_choose3);
        btn_choose4 = findViewById(R.id.btn_choose4);
        btn_next = findViewById(R.id.btn_next);

        // Atzera botoia
        atzera_botoia = findViewById(R.id.image_back);

        dbHelper = new DatabaseHelper(this);

        // Jokoa hasi
        startGlobalTimer();

        // Atzera botoiari sakatzerakoan hau egingo du
        atzera_botoia.setOnClickListener(view -> {

            // Denbora gelditu
            stopGlobalTimer();

            // Partidako denbora gelditu
            countDownTimer.cancel();

            // Leixue itxi
            finish();

        });

        fillData();

        btn_next.setOnClickListener(view -> {
            if (isclickBtn) {
                isclickBtn = false;

                if (valueChoose.equals(currentGaldera.getErantzunZuzena())) {
                    //Toast.makeText(playActivity.this, "Ondo", Toast.LENGTH_LONG).show();
                    btn_click.setBackgroundColor(ContextCompat.getColor(this, R.color.erantzun_Zuzena));
                    scorePlayer += POINTS_PER_CORRECT_ANSWER;
                } else {
                    //Toast.makeText(playActivity.this, "Gaizki", Toast.LENGTH_LONG).show();
                    btn_click.setBackgroundColor(ContextCompat.getColor(this, R.color.Botoien_kolorea));

                    if (btn_choose1.getText().equals(currentGaldera.getErantzunZuzena())){

                        btn_choose1.setBackgroundColor(ContextCompat.getColor(this, R.color.erantzun_Zuzena));

                    }else if (btn_choose2.getText().equals(currentGaldera.getErantzunZuzena())){

                        btn_choose2.setBackgroundColor(ContextCompat.getColor(this, R.color.erantzun_Zuzena));

                    }else if (btn_choose3.getText().equals(currentGaldera.getErantzunZuzena())){

                        btn_choose3.setBackgroundColor(ContextCompat.getColor(this, R.color.erantzun_Zuzena));

                    }else if (btn_choose4.getText().equals(currentGaldera.getErantzunZuzena())){

                        btn_choose4.setBackgroundColor(ContextCompat.getColor(this, R.color.erantzun_Zuzena));

                    }
                }

                new Handler().postDelayed(() -> {
                    // Jokoaren logika jarraitu (galdera aldatu, eta abar)
                    nextQuestion();
                }, 1000);
            } else {
                Toast.makeText(playActivity.this, "Erantzun bat hautatu behar duzu", Toast.LENGTH_LONG).show();
            }
        });

        // Temporizadorea hasi
        startCountdownTimer();
    }

    private void startGlobalTimer() {
        globalTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                totalTimeElapsedMillis += 1000;
            }

            @Override
            public void onFinish() {
                // Kasu honetan ez du egin behar
            }
        }.start();
    }

    private void stopGlobalTimer() {
        if (globalTimer != null) {
            globalTimer.cancel();
        }
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(MAX_SECONDS_PER_QUESTION * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Denbora kounter-a eguneratu
                timerTextView.setText(String.valueOf(currentSecondsRemaining));

                // Denbora bera amaitzean puntuak kendu
                scorePlayer -= calculatePointsDeductionPerSecond();

                currentSecondsRemaining--;
            }

            @Override
            public void onFinish() {
                // Denbora amaitu da, egin beharreko ekintzak burutu
                handleTimeout();
            }
        }.start();
    }

    private int calculatePointsDeductionPerSecond() {
        // Puntuak segundu bakoitzeko kalkulatzeko logika pertsonalizatu
        return 1;  // Adibidez, segundo bakoitzeko puntu bat kendu
    }

    private void handleTimeout() {
        // Puntuak kendu edo beste ekintzak burutu
        Toast.makeText(playActivity.this, "Denbora agortu da", Toast.LENGTH_LONG).show();
        deductPointsDueToTimeout();

        // Jokoaren logika jarraitu (galdera aldatu, eta abar)
        nextQuestion();
    }

    private void deductPointsDueToTimeout() {
        // Jokalariaren puntuak behar bezala kentzeko
        scorePlayer -= calculatePointsDeduction();
    }

    private int calculatePointsDeduction() {
        // Puntuak kentzeko logika pertsonalizatu
        return 10;  // Adibidez, denboraren amaitzean 10 puntu kentzen ditu
    }

    private void nextQuestion() {
        // Temporizadorea gelditu
        countDownTimer.cancel();

        // Temporizadorea eta segundu kounter-a berrabiatu
        currentSecondsRemaining = MAX_SECONDS_PER_QUESTION;
        startCountdownTimer();

        btn_choose1.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color));
        btn_choose2.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color));
        btn_choose3.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color));
        btn_choose4.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color));

        // Jokoaren logika jarraitu (galdera aldatu, eta abar)
        if (currentQuestion < maxQuestionsToShow - 1) {
            currentQuestion++;
            fillData();
            valueChoose = "";
        } else {

            // Datuak automatikoki aktualizatzen zaiatu
            DatuakBidali datuakBidali = new DatuakBidali(getApplicationContext());
            datuakBidali.execute();

            // Galderak desiratuta, ekintzak burutu (adibidez, emaitzen pantaila ireki)
            Intent intent = new Intent(playActivity.this, ResulteActivity.class);
            intent.putExtra("Emaitza", scorePlayer);
            startActivity(intent);

            // Temporizadore globala gelditu
            stopGlobalTimer();
            countDownTimer.cancel();
            String dni = editTextDNI.getText().toString();
            // Txapelketa taula puntu eta denbora guztiekin eguneratu
            dbHelper.updateTxapelketaTable(scorePlayer, totalTimeElapsedMillis / 1000, dni);

            finish();
        }
    }


    void fillData() {
        // Aldaketak hemen
        cpt_question.setText((currentQuestion + 1) + "/" + maxQuestionsToShow);

        List<Galdera> galderaList = dbHelper.getRandomGalderak(maxQuestionsToShow);

        if (currentQuestion < galderaList.size()) {
            currentGaldera = galderaList.get(currentQuestion);
            text_question.setText(currentGaldera.getGaldera());

            // Erantzunak hartu eta nahastu
            List<String> erantzunak = Arrays.asList(
                    currentGaldera.getErantzunZuzena(),
                    currentGaldera.getErantzunOkerra1(),
                    currentGaldera.getErantzunOkerra2(),
                    currentGaldera.getErantzunOkerra3()
            );

            // Nahastutako erantzunak botoietan ezarri
            List<Button> botoiak = Arrays.asList(btn_choose1, btn_choose2, btn_choose3, btn_choose4);
            Collections.shuffle(botoiak);

            for (int i = 0; i < botoiak.size(); i++) {
                botoiak.get(i).setText(erantzunak.get(i));
            }
        }
    }

    public void ClickChoose(View view) {
        btn_click = (Button) view;

        if (isclickBtn) {
            btn_choose1.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color));
            btn_choose2.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color));
            btn_choose3.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color));
            btn_choose4.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color));
        }
        chooseBtn();
    }

    void chooseBtn() {
        btn_click.setBackgroundColor(ContextCompat.getColor(this, R.color.Textuaren_Kolorea));
        isclickBtn = true;
        valueChoose = btn_click.getText().toString();
    }
}

