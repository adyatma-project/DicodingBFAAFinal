package id.adyatma.dicodingbfaafinal.view;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import id.adyatma.dicodingbfaafinal.R;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar progressBarSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBarSplash = findViewById(R.id.progressBar);
        int waktu_loading = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(home);
                progressBarSplash.setVisibility(View.GONE);
                finish();
            }
        }, waktu_loading);


    }
}