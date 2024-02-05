package com.example.clubnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;

public class StartActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        UserProfile userProfile = (UserProfile) getIntent().getSerializableExtra("user_profile");

        bottomNavigationView = findViewById(R.id.bottomnavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                UserProfile userProfile = getUserProfileFromIntent();
                Bundle bundle = new Bundle();
                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        bundle.putSerializable("user_profile", (Serializable) userProfile);
                        fragment.setArguments(bundle);
                        break;
                    case R.id.nav_search:
                        fragment = new SearchFragment();
                        bundle.putSerializable("user_profile", (Serializable) userProfile);
                        fragment.setArguments(bundle);
                        break;
                    case R.id.nav_notify:
                        fragment = new NotificationFragment();
                        break;
                    case R.id.nav_profile:
                        fragment = new ProfileFragment();
                        bundle.putSerializable("user_profile", (Serializable) userProfile);
                        fragment.setArguments(bundle);

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).commit();
                return true;
            }
        });

    }

    private UserProfile getUserProfileFromIntent() {
        // Get UserProfile from intent
        return (UserProfile) getIntent().getSerializableExtra("user_profile");
    }


}