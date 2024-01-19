package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResulteActivity extends AppCompatActivity {

    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulte);
        textView = findViewById(R.id.textView);

        int score = getIntent().getIntExtra("Emaitza",0);

        // Puntuazioa negatiboa ez izateko
        if (score < 0){
            textView.setText("Puntuazioa : " + 0);
        }else {
            textView.setText("Puntuazioa : " + score);
        }


        findViewById(R.id.btn_restart).setOnClickListener(
                restart->{
                    Intent intent  = new Intent(ResulteActivity.this , MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
        );
    }
}