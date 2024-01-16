package com.example.st_jokoa;

import android.content.Intent;
import android.os.Bundle;
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
    DatabaseHelper dbHelper;
    TextView cpt_question, text_question;
    Button btn_choose1, btn_choose2, btn_choose3, btn_choose4, btn_next;

    int currentQuestion = 0;
    int scorePlayer = 0;
    boolean isclickBtn = false;
    String valueChoose = "";
    Button btn_click;
    Galdera currentGaldera;  // Agregado para almacenar la pregunta actual
    int maxQuestionsToShow = 10;  // Definir el número máximo de preguntas para mostrar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        cpt_question = findViewById(R.id.cpt_question);
        text_question = findViewById(R.id.text_question);

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
                if (!valueChoose.equals(currentGaldera.getErantzunZuzena())) {
                    Toast.makeText(playActivity.this, "gaizki", Toast.LENGTH_LONG).show();
                    btn_click.setBackgroundResource(R.drawable.background_btn_erreur);
                } else {
                    Toast.makeText(playActivity.this, "ondo", Toast.LENGTH_LONG).show();
                    btn_click.setBackgroundResource(R.drawable.background_btn_correct);
                    scorePlayer++;
                }

                new Handler().postDelayed(() -> {
                    if (currentQuestion < maxQuestionsToShow - 1) {
                        currentQuestion++;
                        remplirData();
                        valueChoose = "";
                        btn_choose1.setBackgroundResource(R.drawable.background_btn_choose);
                        btn_choose2.setBackgroundResource(R.drawable.background_btn_choose);
                        btn_choose3.setBackgroundResource(R.drawable.background_btn_choose);
                        btn_choose4.setBackgroundResource(R.drawable.background_btn_choose);
                    } else {
                        // Cambios aquí
                        Intent intent = new Intent(playActivity.this, ResulteActivity.class);
                        intent.putExtra("Emaitza", scorePlayer);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            } else {
                Toast.makeText(playActivity.this, "Erantzun bat aukeratu behar duzu", Toast.LENGTH_LONG).show();
            }
        });
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

