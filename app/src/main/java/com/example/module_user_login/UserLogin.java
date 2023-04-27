package com.example.module_user_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

public class UserLogin extends AppCompatActivity {
    MaterialEditText email, password;
        Button btn_login;
        FirebaseAuth firebaseAuth;
        TextView forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot_password);

        forgot_password.setOnClickListener(view -> startActivity(new Intent(UserLogin.this, ResetPasswordActivity.class)));

        firebaseAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(view -> {
            String txt_email = email.getText().toString();
            String txt_password = password.getText().toString();


            if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                Toast.makeText(UserLogin.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                firebaseAuth.signInWithEmailAndPassword(txt_email,txt_password)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()) {
                                if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()) {
                                    Intent intent;
                                    if(txt_email.equals("arjun_m210683ca@nitc.ac.in") || txt_email.equals("junaid_m210662ca@nitc.ac.in") || txt_email.equals("rakhi_m210692ca@nitc.ac.in")) {
                                        intent = new Intent(UserLogin.this, MainActivityAdmin.class);
                                    }
                                    else {
                                        intent = new Intent(UserLogin.this, MainActivity.class);
                                    }
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    firebaseAuth.signOut();
                                    Toast.makeText(UserLogin.this,"Please verify email first!!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(UserLogin.this,"Authentication failed", Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });
    }
}