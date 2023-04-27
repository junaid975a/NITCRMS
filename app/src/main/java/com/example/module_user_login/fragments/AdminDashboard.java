package com.example.module_user_login.fragments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.module_user_login.ElectronicsAdmin;
import com.example.module_user_login.FurnitureAdmin;
import com.example.module_user_login.R;
import com.example.module_user_login.ViewTable;
import com.example.module_user_login.models.Resources;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AdminDashboard extends Fragment {
    public AdminDashboard() {
        // Required empty public constructor
    }
    Button fur_btn,ele_btn,view_item_table;
    DatabaseReference database;
    MaterialTextView tableAvailable,tableAllocated,tableTotal;
    MaterialTextView chairAvailable,chairAllocated,chairTotal;
    MaterialTextView computerAvailable,computerAllocated,computerTotal;
    MaterialTextView projectorAvailable,projectorAllocated,projectorTotal;

    int tav=0,tal=0,tto=0;
    int chav=0,chal=0,chto=0;
    int coav=0,coal=0,coto=0;
    int prav=0,pral=0,prto=0;




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        fur_btn=view.findViewById(R.id.furniture_btn);
        ele_btn=view.findViewById(R.id.electronic_btn);
        view_item_table =view.findViewById(R.id.view_table_btn);
        fur_btn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), FurnitureAdmin.class);
            startActivity(intent);
        });

        ele_btn.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), ElectronicsAdmin.class);
            startActivity(intent);
        });

        //item stats
        view_item_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ViewTable.class));
            }
        });
        return view;
    }
}
