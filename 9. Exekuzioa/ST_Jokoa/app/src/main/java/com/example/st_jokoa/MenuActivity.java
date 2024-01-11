package com.example.st_jokoa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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


    public void main_btn(View view) {
        int viewId = view.getId();

        if (viewId == R.id.btn_Play) {
            startActivity(new Intent(MenuActivity.this, playActivity.class));
        } else if (viewId == R.id.btn_Setting) {
            startActivity(new Intent(MenuActivity.this, settingActivity.class));
        } else if (viewId == R.id.btn_Exit) {
            this.finishAffinity();
        }
    }
}