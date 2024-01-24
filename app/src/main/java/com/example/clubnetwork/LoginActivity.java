package com.example.clubnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reg_no = loginReg_no.getText().toString().trim();
                String pass = loginPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(reg_no) && !TextUtils.isEmpty(pass)) {
                    // Construct the path in the Firebase database
                    String path = "users/" + reg_no;
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(path);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // User exists, check the password
                                String storedPassword = dataSnapshot.child("password").getValue(String.class);
                                if (pass.equals(storedPassword)) {
                                    // Passwords match, login successful
                                    Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                                    startActivity(intent);
                                } else {
                                    // Wrong password
                                    Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // User does not exist
                                Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors if needed
                            Toast.makeText(LoginActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Fields are empty
                    Toast.makeText(LoginActivity.this, "Please enter registration number and password", Toast.LENGTH_SHORT).show();
                }
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