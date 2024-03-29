package com.example.clubnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;


public class ProfileFragment extends Fragment {

    private TextView Name;
    private TextView departmentTextView;
    private TextView yearTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Initialize your TextViews
        Name = view.findViewById(R.id.Name);
        departmentTextView = view.findViewById(R.id.departmentTextView);
        yearTextView = view.findViewById(R.id.yearTextView);
        // Find the "My Achievements" button by its ID
        View myAchievementsButton = view.findViewById(R.id.button);
        View accountSettingsButton = view.findViewById(R.id.button2);
        Intent intent = getActivity().getIntent();
        UserProfile userProfile = (UserProfile) intent.getSerializableExtra("user_profile");
        if (userProfile != null) {
            // Set the details in the TextViews
            Name.setText("" + userProfile.getName());
            departmentTextView.setText("" + userProfile.getRegNo());
            yearTextView.setText("" + userProfile.getEmail());
        }


        myAchievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ButtonClick", "Button clicked");

                // Create a new instance of AchievementsFragment
                AchievementsFragment achievementsFragment = AchievementsFragment.newInstance("param1", "param2");

                // Get the FragmentManager and start a transaction
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Add the AchievementsFragment to the container
                fragmentTransaction.add(R.id.container, achievementsFragment);

                // Add the transaction to the back stack so the user can navigate back
                fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
            }
        });

        accountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ButtonClick", "Button clicked");
                Intent intent = new Intent(getActivity(), AccountSettings.class);
                intent.putExtra("user_profile", (Serializable) userProfile);

                // Start the activity
                startActivity(intent);
            }
        });
        return view;
    }
}
