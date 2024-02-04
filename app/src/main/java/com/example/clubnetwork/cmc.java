package com.example.clubnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cmc extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmc);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final Button joinLeaveButton = findViewById(R.id.joinLeaveButton);
        UserProfile userProfile = getUserProfileFromIntent();
        final String path = "users/" + userProfile.getRegNo() + "/cmc";

        // Fetch the current status from Firebase and set the button text accordingly
        mDatabase.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String status = dataSnapshot.getValue(String.class);
                    if ("joined".equals(status)) {
                        joinLeaveButton.setText(R.string.leave_button);
                    } else {
                        joinLeaveButton.setText(R.string.join_button);
                    }
                } else {
                    // If the literary status does not exist, assume the user has not joined
                    joinLeaveButton.setText(R.string.join_button);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });

        // Set an OnClickListener for the button
        joinLeaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (joinLeaveButton.getText().toString().equals(getString(R.string.join_button))) {
                    // If it's "Join", change it to "Leave" and update Firebase
                    joinLeaveButton.setText(R.string.leave_button);
                    mDatabase.child(path).setValue("joined");
                } else {
                    // If it's "Leave", change it back to "Join" and update Firebase
                    joinLeaveButton.setText(R.string.join_button);
                    mDatabase.child(path).setValue("left");
                }
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