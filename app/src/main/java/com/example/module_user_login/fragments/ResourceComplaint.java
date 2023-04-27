package com.example.module_user_login.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.ViewAcceptedComplaints;
import com.example.module_user_login.ViewPreviousComplaints;
import com.example.module_user_login.adapter.ComplaintUserAdapter;
import com.example.module_user_login.models.AllotedResources;
import com.example.module_user_login.models.Complaints;
import com.example.module_user_login.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class ResourceComplaint extends Fragment {


    private ComplaintUserAdapter complaintUserAdapter;
    private List<AllotedResources> list;
    AppCompatButton previous_complaints;
    DatabaseReference databaseReference;


    FirebaseUser firebaseUser;
    public ResourceComplaint() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.complaint_recycler_view);
        previous_complaints = view.findViewById(R.id.btn_prev_complaints);
        databaseReference = FirebaseDatabase.getInstance().getReference("Alloted");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        list = new ArrayList<>();
        complaintUserAdapter = new ComplaintUserAdapter(getActivity(),list);
        recyclerView.setAdapter(complaintUserAdapter);
        readComplaints();


        previous_complaints.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ViewPreviousComplaints.class);
            startActivity(intent);
        });

//        name = view.findViewById(R.id.name);
//        email = view.findViewById(R.id.email);
//        description = view.findViewById(R.id.complaint_description);
//        location=view.findViewById(R.id.location_description);
//        item_id=view.findViewById(R.id.complaint_id_input);
//        btn_complaint = view.findViewById(R.id.btn_complaint);
//        btn_prev_complaints = view.findViewById(R.id.btn_prev_complaints);
//
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(isAdded()) {
//                    Users user = snapshot.getValue(Users.class);
//                    assert user != null;
//                    name.setText(user.getUsername());
//                    email.setText(user.getEmail());
//                    name.setEnabled(false);
//                    email.setEnabled(false);
//                    // complaint_id = user.getId();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        spinner_complaint_item = (Spinner)view.findViewById(R.id.request_resource);
//        // spinner_user_type.setOnItemSelectedListener(this);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, complaint_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_complaint_item.setAdapter(adapter);
//
//        spinner_complaint_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                complaint_item_value = adapterView.getItemAtPosition(i).toString();
//                // Toast.makeText(getContext(), request_item_value, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        btn_complaint.setOnClickListener(view1 -> {
//            String senderName = name.getText().toString();
//            String senderEmail = email.getText().toString();
//            // complaint_id += complaint_item_value;
//            String complaintDescription = description.getText().toString();
//            String locationn= location.getText().toString();
//            String itemId=item_id.getText().toString();
//            @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
//            String uniqueID = UUID.randomUUID().toString();
//            String lastFour = uniqueID.substring(uniqueID.length()-4);
//            String complaint_id = "C"+complaint_item_value.charAt(0)+lastFour; // eg CC1234, C is for complaint and C is for chair
//            //String req_res_name = request_resource.getText().toString();
//
//            if(TextUtils.isEmpty(senderName) || TextUtils.isEmpty(senderEmail) || TextUtils.isEmpty(complaint_item_value)|| TextUtils.isEmpty(itemId)|| TextUtils.isEmpty(locationn)) {
//                Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
//            } else {
//                firebaseDatabase = FirebaseDatabase.getInstance();
//                databaseReference = firebaseDatabase.getReference();
//
//                Complaints complaint = new Complaints(senderName,senderEmail,complaint_item_value,complaintDescription,complaint_id,date,itemId,locationn,"Pending");
//
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        databaseReference.child("Complaints").child(complaint_id).setValue(complaint);
//                        Toast.makeText(getContext(), "Complaint sent successfully", Toast.LENGTH_SHORT).show();
//                        //startActivity(new Intent(getActivity(), MainActivity.class));
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//        btn_prev_complaints.setOnClickListener(view12 -> startActivity(new Intent(getActivity(), ViewPreviousComplaints.class)));
        return view;
    }

    private void readComplaints() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = firebaseUser.getEmail();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {
                    AllotedResources resources = snap.getValue(AllotedResources.class);
                    assert resources != null;
                    if(resources.getUserEmail().equals(email)) {
                        list.add(resources);
                    }
                }
                complaintUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}