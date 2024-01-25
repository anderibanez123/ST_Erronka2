package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MenuActivity extends AppCompatActivity {

    private static final int BTN_PLAY_ID = R.id.btn_Play;
    private static final int BTN_REFRESH_ID = R.id.btn_refresh;
    private static final int BTN_EXIT_ID = R.id.btn_Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        // Agrega un onTouchListener al layout principal para cerrar el teclado cuando tocas la pantalla
        View mainLayout = findViewById(R.id.menu_activity); // Reemplaza con el ID de tu layout principal
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Cierra el teclado cuando tocas la pantalla
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

        } else if (viewId == R.id.btn_refresh) {
            // Botón "Eguneratu" bidez API deia egin
            DatuakBidali datuakBidali = new DatuakBidali(getApplicationContext());
            datuakBidali.execute();

        } else if (viewId == R.id.btn_Exit) {

            // "Exit" botoiaren kasuan, aplikazioa itxi
            this.finishAffinity();

        }
    }

    // Método para ocultar el teclado virtual
    private void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

}
