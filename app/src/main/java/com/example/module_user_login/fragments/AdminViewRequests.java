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
import com.example.module_user_login.ViewAllotedItems;
import com.example.module_user_login.adapter.RequestAdapter;
import com.example.module_user_login.models.Requests;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminViewRequests extends Fragment {
    private RequestAdapter adapter;
    private List<Requests> list;
    Button view_alloted_items;
    DatabaseReference database;
    public AdminViewRequests() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_view_requests, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.requestList);
        view_alloted_items = view.findViewById(R.id.view_alloted_items);
        database= FirebaseDatabase.getInstance().getReference("Requests");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        list=new ArrayList<>();
        adapter=new RequestAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
        readRequests();

        view_alloted_items.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ViewAllotedItems.class);
            startActivity(intent);
        });

        return view;
    }

    private void readRequests() {
        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Requests request=dataSnapshot.getValue(Requests.class);
                    assert request != null;
                    if(request.getStatus().equals("Pending"))
                        list.add(request);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
}
