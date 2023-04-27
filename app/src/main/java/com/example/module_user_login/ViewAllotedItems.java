package com.example.module_user_login;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.adapter.AllotedResourcesAdapter;
import com.example.module_user_login.models.AllotedResources;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewAllotedItems extends AppCompatActivity {

    private AllotedResourcesAdapter allotedResourcesAdapter;
    private List<AllotedResources> allotedResourcesList;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alloted_items);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Alloted Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.resources_recycler_view);
        reference = FirebaseDatabase.getInstance().getReference("Alloted");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allotedResourcesList = new ArrayList<>();
        allotedResourcesAdapter = new AllotedResourcesAdapter(this,allotedResourcesList);
        recyclerView.setAdapter(allotedResourcesAdapter);

        readComplaints();
    }

    private void readComplaints() {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allotedResourcesList.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    AllotedResources allotedResources = snap.getValue(AllotedResources.class);
                    assert allotedResources != null;

                    allotedResourcesList.add(allotedResources);

                }
                allotedResourcesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}