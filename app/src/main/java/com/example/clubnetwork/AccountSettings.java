package com.example.clubnetwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class AccountSettings extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextRegisterNumber;
    private Spinner spinnerClass;
    private Spinner spinnerDepartment;
    private Spinner spinnerYear;
    private ImageView checkMarkImageView;


    Intent intent = getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        editTextName = findViewById(R.id.editTextName);
        editTextRegisterNumber = findViewById(R.id.editTextRegisterNumber);
        spinnerClass = findViewById(R.id.spinnerClass);
        spinnerYear = findViewById(R.id.spinnerYear);
        checkMarkImageView = findViewById(R.id.checkMark);

        intent = getIntent();
        if (intent.hasExtra("user_profile")) {
            UserProfile userProfile = (UserProfile) intent.getSerializableExtra("user_profile");
            // Set data to views in AccountSettings activity
            if (userProfile != null) {
                editTextName.setText(userProfile.getName());
                editTextRegisterNumber.setText(userProfile.getEmail());
                setSpinnerSelection(spinnerClass, userProfile.getClasss());
                setSpinnerSelection(spinnerYear, userProfile.getyear());
            }
        }

        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(this, R.array.class_entries, android.R.layout.simple_spinner_item);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(classAdapter);

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.year_entries, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        checkMarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a UserProfile object with the entered data
                UserProfile updatedUserProfile = new UserProfile();
                updatedUserProfile.setName(editTextName.getText().toString());
                updatedUserProfile.setEmail(editTextRegisterNumber.getText().toString());
                updatedUserProfile.setClasss(spinnerClass.getSelectedItem().toString());
                updatedUserProfile.setYear(spinnerYear.getSelectedItem().toString());

                // Update data in Firebase
                updateDataInFirebase(updatedUserProfile);
            }
        });
    }

    private void updateDataInFirebase(UserProfile updatedUserProfile) {
        // Check if any required fields are empty or null
        if (TextUtils.isEmpty(updatedUserProfile.getName()) ||
                TextUtils.isEmpty(updatedUserProfile.getClasss()) ||
                TextUtils.isEmpty(updatedUserProfile.getyear())) {
            // Handle the case where required fields are empty
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
        } else {
            UserProfile userProfile = getUserProfileFromIntent();
            // Update data in Firebase
            String path = "users/" + userProfile.getRegNo();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(path);

            // Create a map to update only specific fields
            Map<String, Object> updateFields = new HashMap<>();
            updateFields.put("name", updatedUserProfile.getName());
            updateFields.put("classs", updatedUserProfile.getClasss());
            updateFields.put("email", updatedUserProfile.getEmail());
            updateFields.put("year", updatedUserProfile.getyear());

            // Update only specific fields in Firebase
            userRef.updateChildren(updateFields)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Handle success if needed
                            Toast.makeText(AccountSettings.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            // You may want to navigate to a different activity or fragment after successful update
                            navigateToProfileFragment();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle failure if needed
                            Toast.makeText(AccountSettings.this, "Failed to update profile. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void navigateToProfileFragment() {
        // Create a FragmentTransaction
        Fragment fragment;
        // Replace the current fragment with ProfileFragment
        fragment = new ProfileFragment();
        UserProfile userProfile = getUserProfileFromIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user_profile", (Serializable) userProfile);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.account_settings_container,fragment).commit();
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
        Intent intent = getIntent();

        if (intent.hasExtra("user_profile")) {
            return (UserProfile) intent.getSerializableExtra("user_profile");
        } else {
            // Handle the case where the UserProfile is not present in the intent
            return new UserProfile();  // Or return null, depending on your design
        }
    }


}