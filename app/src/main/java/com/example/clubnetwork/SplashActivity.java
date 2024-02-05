package com.example.clubnetwork;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.splashImageView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                // Apply fade out animation when transitioning to the next activity
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 1500); // Delay of 3 seconds
    }
}