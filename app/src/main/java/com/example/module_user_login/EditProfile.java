package com.example.module_user_login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.module_user_login.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

public class EditProfile extends AppCompatActivity {

    // String[] userType = {"Student", "Faculty"};
    //Spinner spinner_user_type;
    Button btn_change_password;
    Button btn_edit_profile;

    MaterialEditText username, email, contactNo;
    //String user_type_value;
    String userID;

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        spinner_user_type = (Spinner)findViewById(R.id.spinner_user_type);
//        // spinner_user_type.setOnItemSelectedListener(this);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfile.this, android.R.layout.simple_spinner_item,userType);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_user_type.setAdapter(adapter);


        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        contactNo = findViewById(R.id.contactNo);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users user = snapshot.getValue(Users.class);
                    assert user != null;
                    username.setText(user.getUsername());
                    email.setText(user.getEmail());
                    email.setEnabled(false);
                    contactNo.setText(user.getContact());
                    userID = user.getId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        spinner_user_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                user_type_value = adapterView.getItemAtPosition(i).toString();
//                // Toast.makeText(EditProfile.this, user_type_value, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_edit_profile.setOnClickListener(view -> {
            String username_str = username.getText().toString();
            //String email_str = email.getText().toString();
            String contact_str = contactNo.getText().toString();
            if(contact_str.length()!=10){
                Toast.makeText(EditProfile.this, "Contact nuo should be 10 digit", Toast.LENGTH_SHORT).show();
            } else {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference();

                reference.child("Users").child(userID).child("username").setValue(username_str);
                // reference.child("Users").child(userID).child("email").setValue(email_str);
                reference.child("Users").child(userID).child("contact").setValue(contact_str);
                //reference.child("Users").child(userID).child("usertype").setValue(user_type_value);
                Toast.makeText(EditProfile.this, "Updated.", Toast.LENGTH_SHORT).show();
            }
        });


        btn_change_password = findViewById(R.id.btn_change_password);
        btn_change_password.setOnClickListener(view -> startActivity(new Intent(EditProfile.this, ChangePassword.class)));
    }
}