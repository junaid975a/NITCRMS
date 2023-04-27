package com.example.module_user_login.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.module_user_login.R;
import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.ViewPreviousValidityRequests;
import com.example.module_user_login.adapter.ReallocationUserAdapter;
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


public class UserReallocationRequest extends Fragment {

    private ReallocationUserAdapter reallocationUserAdapter;
    private List<AllotedResources> list;
    AppCompatButton previous_requests;
    DatabaseReference databaseReference;

    FirebaseUser firebaseUser;
    public UserReallocationRequest() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_reallocation, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.reallocate_recycler_view);
        previous_requests = view.findViewById(R.id.btn_prev_requests);
        databaseReference = FirebaseDatabase.getInstance().getReference("Alloted");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        list = new ArrayList<>();
        reallocationUserAdapter = new ReallocationUserAdapter(getActivity(),list);
        recyclerView.setAdapter(reallocationUserAdapter);
        readRequests();


        previous_requests.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ViewPreviousValidityRequests.class);
            startActivity(intent);
        });


        return view;
    }

    private void readRequests() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = firebaseUser.getEmail();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    AllotedResources resources = snap.getValue(AllotedResources.class);
                    assert resources != null;
                    if(resources.getUserEmail().equals(email)) {
                        list.add(resources);
                    }
                }
               reallocationUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}