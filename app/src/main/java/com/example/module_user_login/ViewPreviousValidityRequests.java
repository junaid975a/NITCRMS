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
import com.example.module_user_login.adapter.PreviousValidityRequestAdapter;
import com.example.module_user_login.models.Complaints;
import com.example.module_user_login.models.Requests;
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
import java.util.Objects;
public class ViewPreviousValidityRequests extends AppCompatActivity {
    private PreviousValidityRequestAdapter previousValidityRequestAdapter;
    private List<ValidityExtend> requestList;
    DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_previous_validity_requests);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Previous Requests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.request_recyclerview);
        reference = FirebaseDatabase.getInstance().getReference("ValidityExtend");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestList = new ArrayList<>();
        previousValidityRequestAdapter = new PreviousValidityRequestAdapter(this,requestList);
        recyclerView.setAdapter(previousValidityRequestAdapter);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = firebaseUser.getEmail();

        readComplaints(email);
    }

    private void readComplaints(String email) {
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestList.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    ValidityExtend requests = snap.getValue(ValidityExtend.class);
                    assert requests != null;
                    if(requests.getEmail().equals(email))
                        requestList.add(requests);

                }
                previousValidityRequestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
