package com.example.module_user_login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.module_user_login.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class UserFirstPage extends AppCompatActivity {

    Button btn_register, btn_login;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    ImageView college_logo;


    @Override
    protected void onStart() {
        super.onStart();
        college_logo = findViewById(R.id.college_logo);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/rms1-43e61.appspot.com/o/uploads%2Fclg_logo.png?alt=media&token=6194994a-bf81-45c1-a247-03bedb032b63").into(college_logo);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        if(firebaseUser != null) {

                // changes----------------------->
                reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users user = snapshot.getValue(Users.class);
                        assert user != null;
                        String email = user.getEmail();
                        Intent intent;
                        if(email.equals("arjun_m210683ca@nitc.ac.in") || email.equals("junaid_m210662ca@nitc.ac.in") || email.equals("rakhi_m210692ca@nitc.ac.in")) {
                            intent = new Intent(UserFirstPage.this, MainActivityAdmin.class);
                        } else {
                            intent = new Intent(UserFirstPage.this, MainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_first_page);





        // checking if user is already logged-in
        if(firebaseUser != null) {
            // changes----------------------->

                reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users user = snapshot.getValue(Users.class);
                        assert user != null;
                        String email = user.getEmail();
                        Intent intent;
                        if (email.equals("arjun_m210683ca@nitc.ac.in") || email.equals("junaid_m210662ca@nitc.ac.in") || email.equals("rakhi_m210692ca@nitc.ac.in")) {
                            intent = new Intent(UserFirstPage.this, MainActivityAdmin.class);
                        } else {
                            intent = new Intent(UserFirstPage.this, MainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        }


        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(view -> startActivity(new Intent(UserFirstPage.this, UserLogin.class)));

        btn_register.setOnClickListener(view -> startActivity(new Intent(UserFirstPage.this, UserRegister.class)));
    }
}