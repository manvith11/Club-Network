package com.example.clubnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


                if(name.isEmpty() || email.isEmpty() || reg_no.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(SignupActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("email",email);
                    intent.putExtra("registration_number",reg_no);
                    intent.putExtra("password",pass);
                    startActivity(intent);

                    Toast.makeText(SignupActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                }

            }});

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}