package com.example.module_user_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.module_user_login.adapter.AllotedResourcesAdapter;
import com.example.module_user_login.adapter.PreviousRequestsAdapter;
import com.example.module_user_login.models.AllotedResources;
import com.example.module_user_login.models.Requests;
import com.example.module_user_login.models.Resources;
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

public class ViewPreviousRequests extends AppCompatActivity {

    private PreviousRequestsAdapter previousRequestsAdapter;
    private List<Requests> requestsList;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_previous_requests);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Previous Requests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.resource_recyclerview);
        reference = FirebaseDatabase.getInstance().getReference("Requests");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestsList = new ArrayList<>();
        previousRequestsAdapter = new PreviousRequestsAdapter(this,requestsList);
        recyclerView.setAdapter(previousRequestsAdapter);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = firebaseUser.getEmail();

        readRequests(email);
    }

    private void readRequests(String email) {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestsList.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Requests requests = snap.getValue(Requests.class);
                    assert requests != null;
                    if(requests.getRequesterEmail().equals(email))
                        requestsList.add(requests);

                }
                previousRequestsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}