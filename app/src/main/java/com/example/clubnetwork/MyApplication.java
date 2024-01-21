package com.example.clubnetwork;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyApplication", "FirebaseApp.initializeApp called");
    }
}

