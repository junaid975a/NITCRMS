package com.example.module_user_login.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.adapter.UserItemsAdapter;
import com.example.module_user_login.models.AllotedResources;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Dashboard extends Fragment {
    private UserItemsAdapter userItemsAdapter;
    private List<AllotedResources> resourceList;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    private String userEmail;

    public Dashboard() {
        // Required empty public constructor
    }



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.allotted_resources_recycler_view);

        reference = FirebaseDatabase.getInstance().getReference("Alloted");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        userEmail = firebaseUser.getEmail();

        resourceList = new ArrayList<>();
        userItemsAdapter = new UserItemsAdapter(getActivity(), resourceList);
        recyclerView.setAdapter(userItemsAdapter);
        userItemsAdapter();

        return view;
    }

    private void userItemsAdapter() {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resourceList.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    AllotedResources resource = snap.getValue(AllotedResources.class);
                    assert resource != null;
                    if(resource.getUserEmail().equals(userEmail)) {
                        resourceList.add(resource);
                    }
                }
                userItemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}