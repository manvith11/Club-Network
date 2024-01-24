package com.example.clubnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.Serializable;


public class AccountSettings extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextRegisterNumber;
    private Spinner spinnerClass;
    private Spinner spinnerDepartment;
    private Spinner spinnerYear;
    private ImageView checkMarkImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        editTextName = findViewById(R.id.editTextName);
        editTextRegisterNumber = findViewById(R.id.editTextRegisterNumber);
        spinnerClass = findViewById(R.id.spinnerClass);
        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        spinnerYear = findViewById(R.id.spinnerYear);
        checkMarkImageView = findViewById(R.id.checkMark);

        Intent intent = getIntent();
        if (intent.hasExtra("userProfile")) {
            UserProfile userProfile = (UserProfile) intent.getSerializableExtra("userProfile");

            // Set data to views in AccountSettings activity


            // Set data to other views (spinners) accordingly
        }

        checkMarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a UserProfile object with the entered data


                // Create an Intent to pass data to ProfileFragment
                Intent intent = new Intent(AccountSettings.this, StartActivity.class);
                // Start the ProfileFragment
                startActivity(intent);
            }
        });
    }
}