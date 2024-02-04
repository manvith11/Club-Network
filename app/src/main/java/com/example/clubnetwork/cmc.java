package com.example.clubnetwork;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class cmc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmc);

        // Get references to views
        TextView eventNameTextView = findViewById(R.id.eventNameTextView);
        ImageView eventImageView = findViewById(R.id.eventImageView);

        // Set event name
        String eventName = "Computer and Multimedia Club Presents...";  // Replace with your actual event name
        eventNameTextView.setText(eventName);

        // Load image related to the event (you can use an image loading library for better performance)
        // Example using a placeholder image, replace with your actual image resource or URL
        eventImageView.setImageResource(R.drawable.memes);
    }
}
