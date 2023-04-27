package com.example.module_user_login.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.module_user_login.R;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.module_user_login.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.ViewAcceptedComplaints;
import com.example.module_user_login.ViewPreviousValidityRequests;
import com.example.module_user_login.adapter.ComplaintAdapter;
import com.example.module_user_login.adapter.ReallocationAdminAdapter;
import com.example.module_user_login.adapter.ReallocationUserAdapter;
import com.example.module_user_login.models.AllotedResources;
import com.example.module_user_login.models.Complaints;
import com.example.module_user_login.models.ValidityExtend;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class AdminReallocationRequest extends Fragment {

    private ReallocationAdminAdapter reallocationAdminAdapter;
    private List<ValidityExtend> requestsList;
    DatabaseReference reference;

    public AdminReallocationRequest() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_reallocation_request, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.reallocat_recycler_view);
        reference = FirebaseDatabase.getInstance().getReference("ValidityExtend");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestsList = new ArrayList<>();
        reallocationAdminAdapter = new ReallocationAdminAdapter(getActivity(), requestsList);
        recyclerView.setAdapter(reallocationAdminAdapter);
        readComplaints();


        return view;
    }

    private void readComplaints() {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestsList.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                   ValidityExtend request = snap.getValue(ValidityExtend.class);
                    assert request != null;
                    if(request.getStatus().equals("pending")) {
                        requestsList.add(request);
                    }
                }
                reallocationAdminAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}