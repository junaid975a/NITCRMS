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
import com.example.module_user_login.adapter.NotificationAdapter;
import com.example.module_user_login.models.Notification;
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


public class UserNotification extends Fragment {

    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;
    DatabaseReference reference;


    public UserNotification() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_notification, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.notification_recycler_view);
        reference = FirebaseDatabase.getInstance().getReference("Notification");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(getActivity(), notificationList);
        recyclerView.setAdapter(notificationAdapter);
        readNotifications();

        return view;
    }

    private void readNotifications() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationList.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    Notification notification = snap.getValue(Notification.class);
                    assert notification != null;
                    assert firebaseUser != null;
                    if(Objects.equals(firebaseUser.getEmail(), notification.getEmail())){
                        notificationList.add(notification);
                    }
                }
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}