package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    private static final int BTN_PLAY_ID = R.id.btn_Play;
    private static final int BTN_SETTING_ID = R.id.btn_Setting;
    private static final int BTN_EXIT_ID = R.id.btn_Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    /**
     * Metoda honek botoiak klikatzean gertatuko diren ekintzak kudeatzen ditu.
     * @param view Botoiaren ikuspegiaren arabera identifikatzen duen View objektua.
     */
    public void main_btn(View view) {

        int viewId = view.getId();

        if (viewId == R.id.btn_Play) {

            // "Play" botoiaren kasuan, jolasteko pantaila ireki
            startActivity(new Intent(MenuActivity.this, playActivity.class));

        } else if (viewId == R.id.btn_Setting) {
            // Botón "Eguneratu" bidez API deia egin
            new UpdateDataTask().execute();

        } else if (viewId == R.id.btn_Exit) {

            // "Exit" botoiaren kasuan, aplikazioa itxi
            this.finishAffinity();

        }
    }
}
