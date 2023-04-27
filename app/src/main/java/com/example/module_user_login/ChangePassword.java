package com.example.module_user_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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


public class ChangePassword extends AppCompatActivity {

    MaterialEditText current_password, new_password, cnf_new_password;

    DatabaseReference reference;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;

    Button btn_change_password;

    String cur_password, password1, password2, password3,userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        current_password = findViewById(R.id.current_password);
        new_password = findViewById(R.id.new_password);
        cnf_new_password = findViewById(R.id.cnf_new_password);
        btn_change_password = findViewById(R.id.btn_change_password);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                assert user != null;
                cur_password = user.getPassword();
                userID = user.getId();
                //Toast.makeText(ChangePassword.this, cur_password+userID, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_change_password.setOnClickListener(view -> {
            password1 = current_password.getText().toString();
            password2 = new_password.getText().toString();
            password3 = cnf_new_password.getText().toString();

            if(!cur_password.equals(password1)) {
                Toast.makeText(ChangePassword.this, "Enter correct Current Password", Toast.LENGTH_SHORT).show();
            } else {
                if(!password2.equals(password3)) {
                    Toast.makeText(ChangePassword.this, "Reconfirm Password", Toast.LENGTH_SHORT).show();
                } else {
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference();

                    reference.child("Users").child(userID).child("password").setValue(password2);
                    Toast.makeText(ChangePassword.this, "Updated.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}