package com.example.clubnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    EditText signupName,signupEmail,signup_reg_no,signupPassword;
    TextView loginRedirectText;
    Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupName=findViewById(R.id.signup_name);
        signupEmail=findViewById(R.id.signup_email);
        signup_reg_no=findViewById(R.id.signup_regno);
        signupPassword=findViewById(R.id.signup_password);
        signupButton=findViewById(R.id.signup_button);
        loginRedirectText=findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String reg_no = signup_reg_no.getText().toString();
                String pass = signupPassword.getText().toString();
                String classs="null";
                String year="null";


                if (name.isEmpty() || email.isEmpty() || reg_no.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a reference to the user in the Firebase database
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(reg_no);

                    // Check if the user already exists
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                Toast.makeText(SignupActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                            } else {
                                // User does not exist, create a new user
                                UserProfile newUser = new UserProfile(name, email, reg_no, pass,classs,year);

                                // Set the value in the database
                                userRef.setValue(newUser)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Registration successful
                                                Toast.makeText(SignupActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                                                // Redirect to the login activity
                                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Registration failed
                                                Toast.makeText(SignupActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors if needed
                            Toast.makeText(SignupActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}