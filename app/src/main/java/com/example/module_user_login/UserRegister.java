package com.example.module_user_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.module_user_login.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Objects;

public class UserRegister extends AppCompatActivity {

    MaterialEditText usernameText, emailText, passwordText, confirmPasswordText, contactNoText;
    RadioGroup userType;
    RadioButton type;
    Button btn_radio;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userTypeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        usernameText = findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        confirmPasswordText = findViewById(R.id.confirmPasswordText);
        contactNoText = findViewById(R.id.contactNoText);
        userType = (RadioGroup) findViewById(R.id.userType);
        btn_radio = findViewById(R.id.btn_register);



        firebaseAuth = FirebaseAuth.getInstance();

        btn_radio.setOnClickListener(view -> {
            String username = usernameText.getText().toString();
            String email = emailText.getText().toString();
            String contact = contactNoText.getText().toString();
            String password = passwordText.getText().toString();
            String confirmPassword = confirmPasswordText.getText().toString();




            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(UserRegister.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            } else if(contact.length()!=10) {
                Toast.makeText(UserRegister.this, "Enter 10 digit phone number", Toast.LENGTH_SHORT).show();
            }else {
                // username check.
                int x = 0;
                for (int i = 0; i < username.length(); i++) {
                    if ((username.charAt(i) >= 'a' && username.charAt(i) <= 'z') || (username.charAt(i) >= 'A' && username.charAt(i) <= 'Z') || username.charAt(i) == ' ' || username.charAt(i) == '_') {
                        continue;
                    } else {
                        x++;
                        break;
                    }
                }
                if (x > 0) {
                    Toast.makeText(UserRegister.this, "username can contain alphabets, space and _ only.", Toast.LENGTH_SHORT).show();
                }else {
                    // verification of email.
                    int len = email.length();
                    int start = len - 11;
                    String suffix = email.substring(start, len);
                    if (len <= 11 || !suffix.equals("@nitc.ac.in")) {
                        Toast.makeText(UserRegister.this, "You can register with NITC mail only", Toast.LENGTH_SHORT).show();
                    } else {
                        int selectedID = userType.getCheckedRadioButtonId();
                        type = (RadioButton) findViewById(selectedID);
                        if (selectedID == -1) {
                            Toast.makeText(UserRegister.this, "Select User Type", Toast.LENGTH_SHORT).show();
                        } else {
                            userTypeText = type.getText().toString();
                            if (password.length() < 6) {
                                Toast.makeText(UserRegister.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                            } else if (!password.equals(confirmPassword)) {
                                Toast.makeText(UserRegister.this, "Enter same password!!!", Toast.LENGTH_SHORT).show();
                            } else {
                                final int[] flag = {0};
                                databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snap : snapshot.getChildren()) {
                                            Users user = snap.getValue(Users.class);
                                            assert user != null;
                                            if (user.getEmail().equals(email)) {
                                                flag[0] = 1;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                if (flag[0] == 1) {
                                    Toast.makeText(UserRegister.this, "User already exist", Toast.LENGTH_SHORT).show();
                                } else
                                    register(username, email, password, contact);
                            }
                        }
                    }
                }
            }
        });
    }

    private void register(String username, String email, String password, String contact) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        // emailverification
                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(UserRegister.this, "User Registered Successfully.Please check your email", Toast.LENGTH_LONG).show();

                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                                    assert  firebaseUser != null;
                                    String userID = firebaseUser.getUid();

                                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
                                    HashMap<String,String> hashMap = new HashMap<>();
                                    hashMap.put("id", userID);
                                    hashMap.put("username",username);
                                    hashMap.put("email",email);
                                    hashMap.put("password",password);
                                    hashMap.put("contact",contact);
                                    hashMap.put("usertype", userTypeText);
                                    hashMap.put("imageURL","default");

                                    databaseReference.setValue(hashMap).addOnCompleteListener(task1 -> {
                                        if(task1.isSuccessful()) {
                                            firebaseAuth.signOut();
                                            startActivity(new Intent(UserRegister.this, UserLogin.class));
                                        }
                                    });
                                } else {
                                    Toast.makeText(UserRegister.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
    }
}