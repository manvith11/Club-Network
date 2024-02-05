package com.example.clubnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class MainActivity extends AppCompatActivity {
    Button loginButton;
    Button SignupButton;
    Button AdminLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        if (!isNetworkAvailable()) {
            showNoInternetConnectionDialog();
        }


        DatabaseReference databaseReference;
        loginButton = findViewById(R.id.start_login_button);
        SignupButton = findViewById(R.id.start_signup_button);
        AdminLogin = findViewById(R.id.login_as_admin_button);




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent=new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
            });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
    private void showNoInternetConnectionDialog() {
        // Create a custom view for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.broadcast, null);

        // Set the message text
        TextView textViewMessage = dialogView.findViewById(R.id.textViewMessage);
        if (textViewMessage != null) {
            textViewMessage.setText("No Internet Connection");
        }

        // Create AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

}
