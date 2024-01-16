package com.example.st_jokoa;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class playActivity extends AppCompatActivity {
    private static final int POINTS_PER_CORRECT_ANSWER = 100;
    private static final int MAX_SECONDS_PER_QUESTION = 30;

    DatabaseHelper dbHelper;
    TextView cpt_question, text_question, timerTextView;
    Button btn_choose1, btn_choose2, btn_choose3, btn_choose4, btn_next;

    int currentQuestion = 0;
    int scorePlayer = 0;
    boolean isclickBtn = false;
    String valueChoose = "";
    Button btn_click;
    Galdera currentGaldera;
    CountDownTimer countDownTimer;
    int maxQuestionsToShow = 10;  // Definir el número máximo de preguntas para mostrar
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

        dbHelper = new DatabaseHelper(this);

        findViewById(R.id.image_back).setOnClickListener(a -> finish());
        remplirData();

        btn_next.setOnClickListener(view -> {
            if (isclickBtn) {
                isclickBtn = false;

                // Cambios aquí
                if (valueChoose.equals(currentGaldera.getErantzunZuzena())) {
                    Toast.makeText(playActivity.this, "ondo", Toast.LENGTH_LONG).show();
                    btn_click.setBackgroundResource(R.drawable.background_btn_correct);
                    scorePlayer += POINTS_PER_CORRECT_ANSWER;
                } else {
                    Toast.makeText(playActivity.this, "gaizki", Toast.LENGTH_LONG).show();
                    btn_click.setBackgroundResource(R.drawable.background_btn_erreur);
                }

                new Handler().postDelayed(() -> {
                    // Continuar con la lógica del juego (cambiar de pregunta, etc.)
                    nextQuestion();
                }, 2000);
            } else {
                Toast.makeText(playActivity.this, "Erantzun bat aukeratu behar duzu", Toast.LENGTH_LONG).show();
            }
        });

        // Iniciar el temporizador
        startCountdownTimer();
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(MAX_SECONDS_PER_QUESTION * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Actualizar el contador de tiempo en tu interfaz si es necesario
                timerTextView.setText(String.valueOf(currentSecondsRemaining));
                currentSecondsRemaining--;
            }

            @Override
            public void onFinish() {
                // El tiempo ha llegado a cero, realiza las acciones necesarias
                handleTimeout();
            }
        }.start();
    }

    private void handleTimeout() {
        // Puedes deducir puntos o realizar otras acciones
        Toast.makeText(playActivity.this, "Se ha agotado el tiempo", Toast.LENGTH_LONG).show();
        deductPointsDueToTimeout();

        // Continuar con la lógica del juego (cambiar de pregunta, etc.)
        nextQuestion();
    }

    private void deductPointsDueToTimeout() {
        // Resta puntos al puntaje del jugador según sea necesario
        scorePlayer -= calculatePointsDeduction();
    }

    private int calculatePointsDeduction() {
        // Puedes personalizar la lógica de deducción de puntos aquí
        return 10;  // Por ejemplo, resta 10 puntos por agotar el tiempo
    }

    private void nextQuestion() {
        // Detener el temporizador actual
        countDownTimer.cancel();

        // Reiniciar el temporizador y el contador de segundos
        currentSecondsRemaining = MAX_SECONDS_PER_QUESTION;
        startCountdownTimer();

        // Continuar con la lógica de cargar la siguiente pregunta (rellenarData, etc.)
        if (currentQuestion < maxQuestionsToShow - 1) {
            currentQuestion++;
            remplirData();
            valueChoose = "";
            btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
            btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);
        } else {
            // Lógica para cuando se haya completado la cantidad de preguntas deseadas
            // Puedes iniciar la actividad de resultados aquí
            Intent intent = new Intent(playActivity.this, ResulteActivity.class);
            intent.putExtra("Emaitza", scorePlayer);
            startActivity(intent);
            finish();
        }
    }

    void remplirData() {
        // Cambios aquí
        cpt_question.setText((currentQuestion + 1) + "/" + maxQuestionsToShow);

        List<Galdera> galderaList = dbHelper.getRandomGalderak(maxQuestionsToShow);

        if (currentQuestion < galderaList.size()) {
            currentGaldera = galderaList.get(currentQuestion);
            text_question.setText(currentGaldera.getGaldera());

            // Obtener las respuestas y barajarlas
            List<String> respuestas = Arrays.asList(
                    currentGaldera.getErantzunZuzena(),
                    currentGaldera.getErantzunOkerra1(),
                    currentGaldera.getErantzunOkerra2(),
                    currentGaldera.getErantzunOkerra3()
            );

            // Establecer las respuestas en los botones después de barajarlas
            List<Button> buttons = Arrays.asList(btn_choose1, btn_choose2, btn_choose3, btn_choose4);
            Collections.shuffle(buttons);

            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setText(respuestas.get(i));
            }
        }
    }

    public void ClickChoose(View view) {
        btn_click = (Button) view;

        if (isclickBtn) {
            btn_choose1.setBackgroundResource(R.drawable.background_btn);
            btn_choose2.setBackgroundResource(R.drawable.background_btn);
            btn_choose3.setBackgroundResource(R.drawable.background_btn);
            btn_choose4.setBackgroundResource(R.drawable.background_btn);
        }
        chooseBtn();
    }

    void chooseBtn() {
        btn_click.setBackgroundResource(R.drawable.background_btn_choose_color);
        isclickBtn = true;
        valueChoose = btn_click.getText().toString();
    }
}

