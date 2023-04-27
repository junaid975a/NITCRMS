package com.example.module_user_login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.module_user_login.fragments.Dashboard;
import com.example.module_user_login.fragments.UserReallocationRequest;
import com.example.module_user_login.fragments.RequestResource;
import com.example.module_user_login.fragments.ResourceComplaint;
import com.example.module_user_login.fragments.UserNotification;
import com.example.module_user_login.fragments.UserProfile;
import com.example.module_user_login.models.AllotedResources;
import com.example.module_user_login.models.Complaints;
import com.example.module_user_login.models.Requests;
import com.example.module_user_login.models.Users;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    CircleImageView drawer_header_img;
    TextView drawer_header_username, drawer_header_desc;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        drawerLayout = findViewById(R.id.drawerLayoutUser);
        navigationView = findViewById(R.id.navigation_view_user);
        //toolbar = findViewById(R.id.my_toolbar);




        View view = navigationView.getHeaderView(0);
        drawer_header_username = view.findViewById(R.id.drawer_header_username);
        drawer_header_desc = view.findViewById(R.id.drawer_header_desc);
        drawer_header_img = view.findViewById(R.id.drawer_header_img);




        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Users user = snapshot.getValue(Users.class);
                    assert user != null;
                    drawer_header_username.setText(user.getUsername());
                    drawer_header_desc.setText(user.getUsertype());
                    if(!user.getImageURL().equals("default")) {
                        try {
                            //Picasso.get().load(user.getImageURL()).into(drawer_header_img);
                            Glide.with(MainActivity.this).load(user.getImageURL()).into(drawer_header_img);
                        } catch (Exception e) {
                            drawer_header_img.setImageResource(R.mipmap.ic_launcher);
                        }
                    } else {
                        drawer_header_img.setImageResource(R.mipmap.ic_launcher);
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openDrawer,
                R.string.closeDrawer
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();







        // handling the alloted items
        // if upto_date is reached , making that deallocated
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        ArrayList<Integer> curDate  = getDate(date);
        removeFromRequestes(curDate);
        reference = FirebaseDatabase.getInstance().getReference("Alloted");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    AllotedResources resources = snap.getValue(AllotedResources.class);
                    assert resources != null;
                    String upto_date = resources.getUpto_date();
                    ArrayList<Integer> finDate = getDate(upto_date);

                    if(finDate.get(2) < curDate.get(2) || (Objects.equals(finDate.get(2), curDate.get(2)) && finDate.get(1) < curDate.get(1)) ||
                            (Objects.equals(finDate.get(2), curDate.get(2)) && Objects.equals(finDate.get(1), curDate.get(1)) && finDate.get(0) <= curDate.get(0)) ) {
                        // deallocate-->>>>>>>>>
                        // remove from allocated
                        removeFromAlloted(resources.getAllotmentID());
                        // mark in resources as available
                        markAvailable(resources.getItemID());
                        // remove from complaint
                        removeFromComplaint(resources.getItemID());
                        // send notification
                        sendNotification(resources.getUserEmail(), resources.getItemName());
                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
















        loadFragment(new Dashboard());


        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.dashboard) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
                loadFragment(new Dashboard());
            } else if(id == R.id.request_resource) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Request Resource");
                loadFragment(new RequestResource());
            } else if(id == R.id.complaint) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Complaint Resource");
                loadFragment(new ResourceComplaint());
            } else if(id == R.id.profile) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");
                loadFragment(new UserProfile());
                //Toast.makeText(MainActivity.this, "fragment 4", Toast.LENGTH_SHORT).show();
            } else if(id == R.id.notification){
                Objects.requireNonNull(getSupportActionBar()).setTitle("Notification");
                loadFragment(new UserNotification());
            } else if(id == R.id.reallocation){
                Objects.requireNonNull(getSupportActionBar()).setTitle("Reallocation Status");
                loadFragment(new UserReallocationRequest());
            }else if(id == R.id.logout) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, UserFirstPage.class);
                startActivity(intent);
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });
    }



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_user, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private ArrayList<Integer> getDate(String date) {
        ArrayList<Integer> ans = new ArrayList<>();
        int x = 0;
        for(int i=0;i<date.length();i++) {
            if(date.charAt(i)>='0' && date.charAt(i)<='9') {
                x = x*10+date.charAt(i)-'0';
            } else {
                ans.add(x);
                x = 0;
            }
        }
        ans.add(x);
        // System.out.println("date"+ans.get(0)+"month"+ans.get(1)+"year"+ans.get(2));
        return ans;
    }

    private void removeFromAlloted(String allotementID) {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Alloted").child(allotementID);
        reference1.removeValue();
    }

    private void markAvailable(String itemID) {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Resources").child(itemID).child("alloted");
        reference1.setValue("No");
    }

    private void removeFromComplaint(String itemID) {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Complaints");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Complaints complaints = snap.getValue(Complaints.class);
                    assert complaints != null;
                    if(complaints.getItemId().equals(itemID)) {
                        reference1.child(complaints.getComplaintID()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification(String userMail, String itemName) {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Notification");
        String uniqueID = UUID.randomUUID().toString();
        String lastFour = uniqueID.substring(uniqueID.length()-4);
        String id = "N"+itemName.charAt(0)+lastFour;
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());


        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("status", "Item deallocated");
        hashMap.put("item",itemName);
        hashMap.put("date",date);
        hashMap.put("id", id);
        hashMap.put("email",userMail);
        reference1.child(id).setValue(hashMap);
    }

    private void removeFromRequestes(ArrayList<Integer> curDate) {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Requests");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    Requests requests = snap.getValue(Requests.class);
                    assert requests != null;
                    String upto_date = requests.getUpto_date();
                    ArrayList<Integer> finDate = getDate(upto_date);

                    if(finDate.get(2) < curDate.get(2) || (Objects.equals(finDate.get(2), curDate.get(2)) && finDate.get(1) < curDate.get(1)) ||
                            (Objects.equals(finDate.get(2), curDate.get(2)) && Objects.equals(finDate.get(1), curDate.get(1)) && finDate.get(0) <= curDate.get(0)) ) {
                        reference1.child(requests.getRequestID()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}