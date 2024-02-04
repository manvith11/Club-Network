package com.example.clubnetwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminControl extends AppCompatActivity {



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_control);

            CardView cardViewUploadPosts = findViewById(R.id.cardViewUploadPosts);
            CardView cardViewEditAchievements = findViewById(R.id.cardViewEditAchievements);
            Button btnNavigateStart = findViewById(R.id.btnNavigateStart);

            cardViewUploadPosts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to Upload Posts Activity
                    Intent intent = new Intent(AdminControl.this, AdminUpload.class);
                    startActivity(intent);
                }
            });

            cardViewEditAchievements.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to Edit Achievements Activity
                    Intent intent = new Intent(AdminControl.this, EditAchievements.class);
                    startActivity(intent);
                }
            });

            btnNavigateStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate back to the Start Activity
                    Intent intent = new Intent(AdminControl.this, StartActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
