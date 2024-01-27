package com.example.clubnetwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        spinnerYear = findViewById(R.id.spinnerYear);
        checkMarkImageView = findViewById(R.id.checkMark);

        Intent intent = getIntent();
        if (intent.hasExtra("user_profile")) {
            UserProfile userProfile = (UserProfile) intent.getSerializableExtra("user_profile");

            // Set data to views in AccountSettings activity
            if (userProfile != null) {
                editTextName.setText(userProfile.getName());
                editTextRegisterNumber.setText(userProfile.getRegNo());
                // Assuming you have setters for the following fields in your UserProfile class
                // You can set these data accordingly

                setSpinnerSelection(spinnerClass, userProfile.getClasss());
                setSpinnerSelection(spinnerYear, userProfile.getyear());

                // spinnerClass.setSelection(...);
                // spinnerDepartment.setSelection(...);
                // spinnerYear.setSelection(...);
            }
        }

        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(this, R.array.class_entries, android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(classAdapter);

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.year_entries, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        if (intent.hasExtra("user_profile")) {
            UserProfile userProfile = (UserProfile) intent.getSerializableExtra("user_profile");

            // Set data to views in AccountSettings activity
            if (userProfile != null) {
                editTextName.setText(userProfile.getName());
                editTextRegisterNumber.setText(userProfile.getRegNo());

                // Set selected items in spinners
                setSpinnerSelection(spinnerClass, userProfile.getClasss());
                setSpinnerSelection(spinnerYear, userProfile.getyear());
            }
        }

        checkMarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                // Create a UserProfile object with the entered data
                UserProfile updatedUserProfile = new UserProfile();
                updatedUserProfile.setName(editTextName.getText().toString());
                updatedUserProfile.setRegNo(editTextRegisterNumber.getText().toString());

                updatedUserProfile.setClasss(spinnerClass.getSelectedItem().toString());
                updatedUserProfile.setYear(spinnerYear.getSelectedItem().toString());

                updateDataInFirebase(updatedUserProfile);



                // Create an Intent to pass data to ProfileFragment
                fragment = new ProfileFragment();
                UserProfile userProfile = getUserProfileFromIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user_profile", (Serializable) userProfile);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.account_settings_container,fragment).commit();


            }
        });
    }

    private void updateDataInFirebase(UserProfile updatedUserProfile) {
        // Update data in Firebase
        String path = "users/" + updatedUserProfile.getRegNo();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(path);
        userRef.setValue(updatedUserProfile);
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        if (value != null) {
            ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
            int position = adapter.getPosition(value);
            spinner.setSelection(position);
        }
    }

    private UserProfile getUserProfileFromIntent() {
        // Get UserProfile from intent
        return (UserProfile) getIntent().getSerializableExtra("user_profile");
    }

}