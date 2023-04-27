package com.example.module_user_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.module_user_login.models.Resources;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ViewTable extends AppCompatActivity {
    DatabaseReference database;

    MaterialTextView tableAvailable,tableAllocated,tableTotal;
    MaterialTextView chairAvailable,chairAllocated,chairTotal;
    MaterialTextView computerAvailable,computerAllocated,computerTotal;
    MaterialTextView projectorAvailable,projectorAllocated,projectorTotal;

    int tav=0,tal=0,tto=0;
    int chav=0,chal=0,chto=0;
    int coav=0,coal=0,coto=0;
    int prav=0,pral=0,prto=0;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_table);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Item Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        database= FirebaseDatabase.getInstance().getReference("Resources");


        tableAvailable=findViewById(R.id.tableAvailable);
        tableAllocated=findViewById(R.id.tableAlloted);
        tableTotal=findViewById(R.id.tableTotal);
        chairAvailable=findViewById(R.id.chairAvailable);
        chairAllocated=findViewById(R.id.chairAlloted);
        chairTotal=findViewById(R.id.chairTotal);
        computerAvailable=findViewById(R.id.computerAvailable);
        computerAllocated=findViewById(R.id.computerAlloted);
        computerTotal=findViewById(R.id.computerTotal);
        projectorAvailable=findViewById(R.id.projectorAvailable);
        projectorAllocated=findViewById(R.id.projectorAlloted);
        projectorTotal=findViewById(R.id.projectorTotal);


        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Resources res=dataSnapshot.getValue(Resources.class);
                    assert res != null;
                    if(res.getItem().equals("Table")) {
                        if (res.getAlloted().equals("No")) {
                            tav++;
                        }
                        else if(res.getAlloted().equals("Yes"))
                        {
                            tal++;
                        }
                        tto++;
                    }
                    if(res.getItem().equals("Chair")) {
                        if (res.getAlloted().equals("No")) {
                            chav++;
                        }
                        else if(res.getAlloted().equals("Yes"))
                        {
                            chal++;
                        }
                        chto++;
                    }
                    if(res.getItem().equals("Computer")) {
                        if (res.getAlloted().equals("No")) {
                            coav++;
                        }
                        else if(res.getAlloted().equals("Yes"))
                        {
                            coal++;
                        }
                        coto++;
                    }
                    if(res.getItem().equals("Projector")) {
                        if (res.getAlloted().equals("No")) {
                            prav++;
                        }
                        else if(res.getAlloted().equals("Yes"))
                        {
                            pral++;
                        }
                        prto++;
                    }
                }
                tableAllocated.setText(Integer.toString(tal));
                tableAvailable.setText(Integer.toString(tav));
                tableTotal.setText(Integer.toString(tto));

                chairAllocated.setText(Integer.toString(chal));
                chairAvailable.setText(Integer.toString(chav));
                chairTotal.setText(Integer.toString(chto));

                computerAllocated.setText(Integer.toString(coal));
                computerAvailable.setText(Integer.toString(coav));
                computerTotal.setText(Integer.toString(coto));

                projectorAllocated.setText(Integer.toString(pral));
                projectorAvailable.setText(Integer.toString(prav));
                projectorTotal.setText(Integer.toString(prto));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}