package com.example.clubnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText loginReg_no,loginPassword;
    Button loginButton;
    TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        String registration_number = intent.getStringExtra("registration_number");
        String Pass = intent.getStringExtra("password");


        loginReg_no = findViewById(R.id.login_regno);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.SignUpRedirectText);
        loginButton = findViewById(R.id.login_button);

        String reg_no = loginReg_no.getText().toString();
        String pass = loginPassword.getText().toString();



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}