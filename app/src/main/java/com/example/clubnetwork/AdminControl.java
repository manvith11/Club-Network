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
            Button btnNavigateStart = findViewById(R.id.btnNavigateStart);
            UserProfile userProfile = getUserProfileFromIntent();

            cardViewUploadPosts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigate to Upload Posts Activity
                    Intent intent = new Intent(AdminControl.this, AdminUpload.class);
                    intent.putExtra("user_profile", userProfile);
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
    private UserProfile getUserProfileFromIntent() {
        // Get UserProfile from intent
        Intent intent = getIntent();

        if (intent.hasExtra("user_profile")) {
            return (UserProfile) intent.getSerializableExtra("user_profile");
        } else {
            // Handle the case where the UserProfile is not present in the intent
            return new UserProfile();  // Or return null, depending on your design
        }
    }
    }
