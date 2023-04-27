package com.example.module_user_login;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.adapter.AcceptedComplaintsAdapter;
import com.example.module_user_login.models.AcceptedComplaints;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewAcceptedComplaints extends AppCompatActivity {
    private AcceptedComplaintsAdapter acceptedComplaintsAdapter;
    private List<AcceptedComplaints> acceptedComplaintsList;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accepted_complaints);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Accepted Complaints");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.complaint_recycler_view);
        reference = FirebaseDatabase.getInstance().getReference("Accepted Complaints");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        acceptedComplaintsList = new ArrayList<>();
        acceptedComplaintsAdapter = new AcceptedComplaintsAdapter(this,acceptedComplaintsList);
        recyclerView.setAdapter(acceptedComplaintsAdapter);

        readComplaints();
    }

    private void readComplaints() {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                acceptedComplaintsList.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    AcceptedComplaints acceptedComplaints = snap.getValue(AcceptedComplaints.class);
                    assert acceptedComplaints != null;

                        acceptedComplaintsList.add(acceptedComplaints);

                }
                acceptedComplaintsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}