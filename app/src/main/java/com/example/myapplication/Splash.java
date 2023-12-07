package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {
    Handler handler;
    Runnable runnable;
    ProgressBar carga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        carga = findViewById(R.id.carga);
        carga.animate().alpha(4000).setDuration(0);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dsp = new Intent(Splash.this,Login.class);
                startActivity(dsp);
                finish();
            }
        }, 4000);
    }
}