package com.example.module_user_login.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.models.Requests;
import com.example.module_user_login.models.Resources;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.requestViewHolder> {

    private final Context context;

    private final List<Requests> list;
    DatabaseReference reference;

    public RequestAdapter(Context context, List<Requests> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public requestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_request_admin,parent,false);
        return new requestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull requestViewHolder holder, int position) {
        Requests requests = list.get(position);
        holder.requestID.setText(requests.getRequestID());
        holder.requesterName.setText(requests.getRequesterName());
        holder.requesterEmail.setText(requests.getRequesterEmail());
        holder.requestedItem.setText(requests.getRequestedItem());
        holder.requestDate.setText(requests.getDate());
        holder.requestStatus.setText(requests.getStatus());
        holder.location_description.setText(requests.getLocation());
        holder.upto_date.setText(requests.getUpto_date());


        holder.allocate_btn.setOnClickListener(view -> {
            final int[] flag = {0};
            String requestedItem = requests.getRequestedItem();
            String requesterEmail = requests.getRequesterEmail();
            String location_description=requests.getLocation();
            final String[] id = new String[1];
            final String[] itemID = new String[1];

            // creating unique ID
            final String[] uniqueID = {UUID.randomUUID().toString()};
            final String[] lastFour = {uniqueID[0].substring(uniqueID[0].length() - 4)};
            id[0] = "A"+requestedItem.charAt(0)+ lastFour[0]; // eg AC1234 is for alloted and C is for chair
            @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            reference= FirebaseDatabase.getInstance().getReference("Resources");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap : snapshot.getChildren()) {
                        Resources resource = snap.getValue(Resources.class);

                        assert resource != null;
                        if(resource.getItem().equals(requestedItem) && resource.getAlloted().equals("No")) {
                            itemID[0] = resource.getId();
                            flag[0] = 1;
                        }
                    }

                    if(flag[0] ==0) {
                        Toast.makeText(context.getApplicationContext(), "Unable to Allocate : Item is not available", Toast.LENGTH_SHORT).show();
                    } else {
                        reference.child(itemID[0]).child("alloted").setValue("Yes");
                        Toast.makeText(context.getApplicationContext(), "Allocated Successfully", Toast.LENGTH_SHORT).show();
                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Alloted");
                        DatabaseReference finalReference = reference1;
                        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                HashMap<String,String> userdataMap=new HashMap<>();
                                userdataMap.put("allotmentID", id[0]);
                                userdataMap.put("itemID", itemID[0]);
                                userdataMap.put("itemName",requestedItem);
                                userdataMap.put("userEmail",requesterEmail);
                                userdataMap.put("allotmentDate",date);
                                userdataMap.put("requestDate",requests.getDate());
                                userdataMap.put("requestID",requests.getRequestID());
                                userdataMap.put("userName",requests.getRequesterName());
                                userdataMap.put("location",location_description);
                                userdataMap.put("upto_date", requests.getUpto_date());
                                String duration;
                                if(requestedItem.equals("Projector")) {
                                    duration = "1 Day";
                                } else {
                                    duration = "12months";
                                }
                                userdataMap.put("forDuration",duration);//default

                                finalReference.child(id[0]).setValue(userdataMap);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        reference1 = FirebaseDatabase.getInstance().getReference("Requests");
                        DatabaseReference finalReference1 = reference1;
                        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                finalReference1.child(requests.getRequestID()).child("status").setValue("Request Accepted");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        uniqueID[0] = UUID.randomUUID().toString();
                        lastFour[0] = uniqueID[0].substring(uniqueID[0].length()-4);
                        id[0] = "N"+requests.getRequestedItem().charAt(0)+ lastFour[0];
                        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                        reference1 = FirebaseDatabase.getInstance().getReference("Notification");
                        DatabaseReference finalReference2 = reference1;
                        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                HashMap<String,String>hashMap = new HashMap<>();
                                hashMap.put("status", "Request accepted");
                                hashMap.put("item",requests.getRequestedItem());
                                hashMap.put("date",date);
                                hashMap.put("id", id[0]);
                                hashMap.put("email",requests.getRequesterEmail());
                                finalReference2.child(id[0]).setValue(hashMap);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });


        holder.reject_btn.setOnClickListener(view -> {
            reference= FirebaseDatabase.getInstance().getReference("Requests");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child(requests.getRequestID()).child("status").setValue("Request Rejected");
                    Toast.makeText(context.getApplicationContext(), "Rejected", Toast.LENGTH_SHORT).show();

                    String uniqueID = UUID.randomUUID().toString();
                    String lastFour = uniqueID.substring(uniqueID.length()-4);
                    String id = "N"+requests.getRequestedItem().charAt(0)+lastFour;
                    @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Notification");
                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<String,String>hashMap = new HashMap<>();
                            hashMap.put("status", "Request Rejected");
                            hashMap.put("item",requests.getRequestedItem());
                            hashMap.put("date",date);
                            hashMap.put("id", id);
                            hashMap.put("email",requests.getRequesterEmail());
                            reference1.child(id).setValue(hashMap);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class requestViewHolder extends RecyclerView.ViewHolder
    {
        TextView requestID,requestedItem,requesterEmail,requesterName,requestDate,requestStatus,location_description,upto_date;
        //changed from here ok

        Button allocate_btn,reject_btn;

        public requestViewHolder(@NonNull View itemView) {
            super(itemView);
            requestID=itemView.findViewById(R.id.tvrequestid);
            requestedItem=itemView.findViewById(R.id.tvRequestedItem);
            requesterEmail=itemView.findViewById(R.id.tvRequesterEmail);
            requesterName=itemView.findViewById(R.id.tvRequesterName);
            requestDate = itemView.findViewById(R.id.request_date);
            requestStatus = itemView.findViewById(R.id.request_status);
            allocate_btn = itemView.findViewById(R.id.allocate_btn);
            reject_btn = itemView.findViewById(R.id.reject_btn);
            location_description=itemView.findViewById(R.id.location_description);
            upto_date=itemView.findViewById(R.id.upto_date);
            // itemView.findViewById(R.id.allocate_btn).setOnClickListener(view -> Toast.makeText(context.getApplicationContext(), "Testing baby "+getAdapterPosition(), Toast.LENGTH_SHORT).show());
        }

    }

}