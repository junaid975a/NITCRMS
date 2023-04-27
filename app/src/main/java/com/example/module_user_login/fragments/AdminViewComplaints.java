package com.example.module_user_login.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.ViewAcceptedComplaints;
import com.example.module_user_login.adapter.ComplaintAdapter;
import com.example.module_user_login.models.Complaints;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AdminViewComplaints extends Fragment {

    private ComplaintAdapter complaintAdapter;
    private List<Complaints> complaintsList;
    Button view_accepted_complaints;
    DatabaseReference reference;

    public AdminViewComplaints() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_view_complaints, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.complaint_recycler_view);
        view_accepted_complaints = view.findViewById(R.id.view_accepted_complaints);
        reference = FirebaseDatabase.getInstance().getReference("Complaints");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        complaintsList = new ArrayList<>();
        complaintAdapter = new ComplaintAdapter(getActivity(), complaintsList);
        recyclerView.setAdapter(complaintAdapter);
        readComplaints();


        view_accepted_complaints.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ViewAcceptedComplaints.class);
            startActivity(intent);
        });

        return view;
    }

    private void readComplaints() {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaintsList.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Complaints complaint = snap.getValue(Complaints.class);
                    assert complaint != null;
                    if(complaint.getStatus().equals("pending")) {
                        complaintsList.add(complaint);
                    }
                }
                complaintAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}