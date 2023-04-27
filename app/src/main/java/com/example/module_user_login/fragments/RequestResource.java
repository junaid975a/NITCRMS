package com.example.module_user_login.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.module_user_login.R;
import com.example.module_user_login.ViewPreviousRequests;
import com.example.module_user_login.models.Requests;
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
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class RequestResource extends Fragment  {
    MaterialEditText name;
    MaterialEditText email;
    MaterialEditText location;
    TextView date;
    AppCompatButton choose_date;
    Button btn_request,btn_view_request_status;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;

    String[] request_item = {"Table", "Chair", "Computer", "Projector"};
    Spinner spinner_request_item;
    String request_item_value;
    String request_id;
    String editDate="";




    public RequestResource() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_resource, container, false);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        //request_resource = view.findViewById(R.id.request_resource);
        btn_request = view.findViewById(R.id.btn_request);
        btn_view_request_status = view.findViewById(R.id.btn_view_request_status);
        location=view.findViewById(R.id.location_description);
        date = view.findViewById(R.id.date);
        choose_date = view.findViewById(R.id.choose_date);



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(isAdded()) {
                    Users user = snapshot.getValue(Users.class);
                    assert user != null;
                    name.setText(user.getUsername());
                    email.setText(user.getEmail());
                    name.setEnabled(false);
                    email.setEnabled(false);
                    // request_id = user.getId();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinner_request_item = view.findViewById(R.id.request_resource);
        // spinner_user_type.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, request_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_request_item.setAdapter(adapter);

        spinner_request_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                request_item_value = adapterView.getItemAtPosition(i).toString();
                // Toast.makeText(getContext(), request_item_value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);



        choose_date.setOnClickListener(view13 -> {
            @SuppressLint("SetTextI18n") DatePickerDialog dialog = new DatePickerDialog(view13.getContext(), (datePicker, day1, month1, year1) -> {
                calendar.set(Calendar.YEAR, day1);
                calendar.set(Calendar.MONTH, month1);
                calendar.set(Calendar.DAY_OF_MONTH, year1);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                editDate = format.format(calendar.getTime());
                date.setText("Upto : "+editDate);
            }, year, month, day);
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() + 24*60*60*1000);
            dialog.show();
        });



        btn_request.setOnClickListener(view1 -> {
            String senderName = name.getText().toString();
            String senderEmail = email.getText().toString();
            String locationn= location.getText().toString();
            String upto_date = editDate;
            String uniqueID = UUID.randomUUID().toString();
            String lastFour = uniqueID.substring(uniqueID.length()-4);
            request_id = "R"+request_item_value.charAt(0)+lastFour; // eg CC1234, C is for complaint and C is for chair
            //String req_res_name = request_resource.getText().toString();
            @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            if(TextUtils.isEmpty(senderName) || TextUtils.isEmpty(senderEmail) || TextUtils.isEmpty(request_item_value)|| TextUtils.isEmpty(locationn) || TextUtils.isEmpty(upto_date)) {
                Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();

                Requests request = new Requests(senderName,senderEmail,request_item_value,request_id,date,locationn,"Pending", upto_date);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.child("Requests").child(request_id).setValue(request);
                        Toast.makeText(getContext(), "Request sent successfully", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getActivity(), MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btn_view_request_status.setOnClickListener(view12 -> startActivity(new Intent(getActivity(), ViewPreviousRequests.class)));
        return view;
    }


}