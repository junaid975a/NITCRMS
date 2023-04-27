package com.example.module_user_login.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.models.Complaints;
import com.example.module_user_login.models.ValidityExtend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
public class ReallocationAdminAdapter extends RecyclerView.Adapter<ReallocationAdminAdapter.ViewHolder> {
    private final Context context;
    private final List<ValidityExtend> list;
    public ReallocationAdminAdapter(Context context, List<ValidityExtend>list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_reallocation,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ValidityExtend request = list.get(position);
        holder.requestId.setText(request.getRequestID());
        holder.userName.setText(request.getUserName());
        holder.userEmail.setText(request.getEmail());
        holder.itemName.setText(request.getItemName());
        holder.itemId.setText(request.getItemId());
        holder.allotedOn.setText(request.getRequestAcceptDate());
        holder.oldValidity.setText(request.getOldUptoDate());
        holder.newValidity.setText(request.getNewUptoDate());
        holder.status.setText(request.getStatus());
        String description = String.valueOf(holder.description.getText());

        // holder.accept_btn.setText("Fix this");


        holder.accept_btn.setOnClickListener(view -> {
            String email = request.getEmail();
            String item = request.getItemName();
            String request_id = request.getRequestID();
            String descript=request.getDescription();
            String itemId = request.getItemId();
            String newDate = request.getNewUptoDate();

            if(TextUtils.isEmpty(descript)) {
                descript="none";
            }


            String uniqueID = UUID.randomUUID().toString();
            String lastFour = uniqueID.substring(uniqueID.length()-4);
            String id = "A"+item.charAt(0)+lastFour; // eg AC1234 is for alloted and C is for chair
            @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            //reference1 is to get access of resources table, and reference 2 is for general alloted table uploading purpose
            //DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("ValidityExtend");
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("ValidityExtend");
            DatabaseReference finalReference1 = reference;
            String finalDescript = descript;
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    finalReference1.child(request_id).child("status").setValue("Reallocation Request accepted");
                    if(!holder.description.getText().toString().equals(""))
                        finalReference1.child(request_id).child("description").setValue(holder.description.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                holder.description.setText("");

                            }
                        });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();

            ref1.child("Alloted").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren())
                    {
                        if(ds.child("itemID").getValue().toString().equalsIgnoreCase(itemId))
                        {
                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                            ref1.child("Alloted").child(ds.getKey()).child("upto_date").setValue(newDate);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
            ref2.child("Complaints").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren())
                    {
                        if(ds.child("itemId").getValue().toString().equalsIgnoreCase(itemId))
                        {
                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
                            ref2.child("Complaints").child(ds.getKey()).child("upto_date").setValue(newDate);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            uniqueID = UUID.randomUUID().toString();
            lastFour = uniqueID.substring(uniqueID.length()-4);
            id = "N"+item.charAt(0)+lastFour; // eg AC1234 is for alloted and C is for chair
            reference = FirebaseDatabase.getInstance().getReference("Notification");
            String finalId = id;
            DatabaseReference finalReference2 = reference;
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String,String>hashMap = new HashMap<>();
                    hashMap.put("status", "Reallocation Request accepted");
                    hashMap.put("item",item);
                    hashMap.put("date",date);
                    hashMap.put("id", finalId);
                    hashMap.put("email",email);
                    finalReference2.child(finalId).setValue(hashMap);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Toast.makeText(view.getContext(), "Request Accepted", Toast.LENGTH_SHORT).show();
            Toast.makeText(view.getContext(), "Notification sent", Toast.LENGTH_SHORT).show();
        });


        // reject complaint
        holder.reject_btn.setOnClickListener(view -> {

            if(holder.description.getText().toString().equals("")) {
                Toast.makeText(view.getContext(), "Description/Reason is required", Toast.LENGTH_SHORT).show();
            }
            else
            {




                String email = request.getEmail();
                String item = request.getItemName();
                String request_id = request.getRequestID();



                    String uniqueID;
                    String lastFour;
                    String id; // eg AC1234 is for alloted and C is for chair
                    @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                    //reference1 is to get access of resources table, and reference 2 is for general alloted table uploading purpose
                    //DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("ValidityExtend");


                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("ValidityExtend");
                    DatabaseReference finalReference1 = reference;
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            finalReference1.child(request_id).child("status").setValue("Reallocation Request Rejected");
                            if(!holder.description.getText().toString().equals(""))
                                finalReference1.child(request_id).child("description").setValue(holder.description.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        holder.description.setText("");

                                    }
                                });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });






                uniqueID = UUID.randomUUID().toString();
                    lastFour = uniqueID.substring(uniqueID.length()-4);
                    id = "N"+item.charAt(0)+lastFour; // eg AC1234 is for alloted and C is for chair
                    reference = FirebaseDatabase.getInstance().getReference("Notification");
                    String finalId = id;
                    DatabaseReference finalReference2 = reference;
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<String,String>hashMap = new HashMap<>();
                            hashMap.put("status", "Reallocation Request Rejected");
                            hashMap.put("item",item);
                            hashMap.put("date",date);
                            hashMap.put("id", finalId);
                            hashMap.put("email",email);
                            finalReference2.child(finalId).setValue(hashMap);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Toast.makeText(view.getContext(), "Request Rejected", Toast.LENGTH_SHORT).show();
                    Toast.makeText(view.getContext(), "Notification sent", Toast.LENGTH_SHORT).show();

                }
            });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView requestId,userName,userEmail,itemName,itemId,allotedOn,oldValidity,newValidity,status,description;
        Button accept_btn, reject_btn;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            requestId = itemView.findViewById(R.id.requestid);
            userName = itemView.findViewById(R.id.user_name);
            userEmail = itemView.findViewById(R.id.user_id);
            itemName=itemView.findViewById(R.id.item_name);
            itemId = itemView.findViewById(R.id.item_id);
            allotedOn = itemView.findViewById(R.id.alloted_date);
            oldValidity = itemView.findViewById(R.id.old_validity);
            newValidity = itemView.findViewById(R.id.new_validity);
            status=itemView.findViewById(R.id.status);
            description=itemView.findViewById(R.id.description);
            accept_btn = itemView.findViewById(R.id.allocate_btn);
            reject_btn = itemView.findViewById(R.id.reject_btn);

        }
    }
}

