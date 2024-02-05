package com.example.clubnetwork;

import android.content.Intent;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ImageAdapter.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseStorage mStorage;
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private ValueEventListener mDBListener;
    private String userRegNo;

    // Placeholder: Replace with actual logic to obtain user's regNo

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home2, container, false);
        Intent intent = getActivity().getIntent();
        UserProfile userProfile = getUserProfileFromIntent();
        userRegNo= userProfile.getRegNo();
        initializeViews(rootView);
        loadUserClubsAndPosts();
        return rootView;
    }

    private void initializeViews(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgressCircle = rootView.findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(HomeFragment.this);
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
    }

    private void loadUserClubsAndPosts() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userRegNo);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ("joined".equals(dataSnapshot.child("cmc").getValue(String.class))) {
                    loadPostsFromClub("Computer_and_Multimedia_Club");
                }
                if ("joined".equals(dataSnapshot.child("literary").getValue(String.class))) {
                    loadPostsFromClub("Literary_Club");
                }
                if ("joined".equals(dataSnapshot.child("ai").getValue(String.class))) {
                    loadPostsFromClub("AI_Club");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPostsFromClub(String clubName) {
        DatabaseReference clubPostsRef = FirebaseDatabase.getInstance().getReference("uploads").child(clubName);
        clubPostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    return; // Early return if no posts
                }
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    if (upload != null) {
                        upload.setKey(postSnapshot.getKey());
                        mUploads.add(upload);
                    }
                }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(getActivity(), "Whatever click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem = mUploads.get(position);
        String selectedKey = selectedItem.getKey();
        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(getActivity(), "Post deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDatabaseRef != null && mDBListener != null) {
            mDatabaseRef.removeEventListener(mDBListener);
        }
    }@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra("user_profile")) {
                UserProfile userProfile = (UserProfile) intent.getSerializableExtra("user_profile");
                // Use userProfile as needed
            }
        }
    }
    private UserProfile getUserProfileFromIntent() {
        // Get UserProfile from intent
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra("user_profile")) {
            return (UserProfile) intent.getSerializableExtra("user_profile");
        } else {
            // Handle the case where the UserProfile is not present in the intent
            return new UserProfile();  // Or return null, depending on your design
        }
    }


}