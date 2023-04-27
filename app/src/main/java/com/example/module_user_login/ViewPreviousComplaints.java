package com.example.module_user_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.module_user_login.adapter.PreviousComplaintAdapter;
import com.example.module_user_login.adapter.PreviousRequestsAdapter;
import com.example.module_user_login.models.Complaints;
import com.example.module_user_login.models.Requests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewPreviousComplaints extends AppCompatActivity {
    private PreviousComplaintAdapter previousComplaintAdapter;
    private List<Complaints> complaintsList;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_previous_complaints);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Previous Complaints");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.complaint_recyclerview);
        reference = FirebaseDatabase.getInstance().getReference("Complaints");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        complaintsList = new ArrayList<>();
        previousComplaintAdapter = new PreviousComplaintAdapter(this,complaintsList);
        recyclerView.setAdapter(previousComplaintAdapter);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = firebaseUser.getEmail();

        readComplaints(email);
    }

    private void readComplaints(String email) {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaintsList.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Complaints complaints = snap.getValue(Complaints.class);
                    assert complaints != null;
                    if(complaints.getEmail().equals(email))
                        complaintsList.add(complaints);

                }
                previousComplaintAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}