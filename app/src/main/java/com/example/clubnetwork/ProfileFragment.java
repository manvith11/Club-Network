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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements OnDetailsUpdateListener {

    private TextView Name;
    private TextView departmentTextView;
    private TextView yearTextView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDetailsUpdate(String updatedName, String updatedDepartment, String updatedYear) {
        // Update the details in the ProfileFragment
        updateDetails(updatedName, updatedDepartment, updatedYear);
    }

    public void updateDetails(String updatedName, String updatedDepartment, String updatedYear) {
        // Update the TextViews or other UI elements with the new details
        if (Name != null && departmentTextView != null && yearTextView != null) {
            Name.setText(updatedName);
            departmentTextView.setText(updatedDepartment);
            yearTextView.setText(updatedYear);
        }
    }

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
        UserProfile userProfile = (UserProfile) intent.getSerializableExtra("userProfile");

        // Now you can use the userProfile object in this fragment


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
                // Start the activity
                startActivity(intent);
            }
        });

        return view;
    }
}
