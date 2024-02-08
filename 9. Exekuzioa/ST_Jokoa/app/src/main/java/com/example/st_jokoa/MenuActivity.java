package com.example.st_jokoa;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private static final int BTN_PLAY_ID = R.id.btn_Play;
    private static final int BTN_REFRESH_ID = R.id.btn_refresh;
    private static final int BTN_EXIT_ID = R.id.btn_Exit;
    private static final int BTN_RANKING_ID = R.id.btn_ranking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btn_ranking_ = findViewById(R.id.btn_ranking);

        // Intent balorea hartu
        int modua = getIntent().getIntExtra("valor", 0);

        // Ranking botoia offline edo online
        if (modua == 1){
            btn_ranking_.setEnabled(false);
            btn_ranking_.setBackgroundColor(ContextCompat.getColor(this, R.color.Textuaren_Kolorea));
        }else{
            btn_ranking_.setEnabled(true);
        }

        // Pantalla ikutzerakoan teklatua itxi
        View mainLayout = findViewById(R.id.menu_activity);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Teklatua itxi
                hideKeyboard();
                return false;
            }
        });


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

        } else if(viewId == R.id.btn_ranking){

            // Ranking ikusteko taula kargatu
            int valor = 1;
            Intent intent = new Intent(MenuActivity.this, ResulteActivity.class);
            intent.putExtra("menutik", valor);
            startActivity(intent);

        }else if (viewId == R.id.btn_refresh) {
            // Botón "Eguneratu" bidez API deia egin
            DatuakBidali datuakBidali = new DatuakBidali(getApplicationContext());
            datuakBidali.execute();

        } else if (viewId == R.id.btn_Exit) {

            // "Exit" botoiaren kasuan, sesioa ixten du
            finish();

        }
    }

    // Teklatua kentzeko metodoa
    private void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }
}
