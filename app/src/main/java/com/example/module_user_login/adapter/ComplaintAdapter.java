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
import com.example.module_user_login.models.Complaints;
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

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {
    private final Context context;
    private final List<Complaints> list;
    public ComplaintAdapter(Context context, List<Complaints>list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_complaint_admin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaints complaint = list.get(position);
        holder.complaint_id.setText(complaint.getComplaintID());
        holder.complaint_item.setText(complaint.getComplainedItem());
        holder.complaint_date.setText(complaint.getDate());
        holder.complaint_item_id_input.setText(complaint.getItemId());
        holder.complaint_description.setText(complaint.getComplaintDescription());
        holder.location_description.setText(complaint.getLocation());
        holder.complainter_name.setText(complaint.getName());
        holder.complainter_email.setText(complaint.getEmail());
        holder.complaint_status.setText(complaint.getStatus());
        // holder.accept_btn.setText("Fix this");


        holder.accept_btn.setOnClickListener(view -> {
            String name = complaint.getName();
            String email = complaint.getEmail();
            String item = complaint.getComplainedItem();
            String complaint_id = complaint.getComplaintID();
            String complaint_date = complaint.getDate();
            String complaint_description = complaint.getComplaintDescription();
            String location_description=complaint.getLocation();
            String itemId=complaint.getItemId();


            String uniqueID = UUID.randomUUID().toString();
            String lastFour = uniqueID.substring(uniqueID.length()-4);
            String id = "A"+item.charAt(0)+lastFour; // eg AC1234 is for alloted and C is for chair
            @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            //reference1 is to get access of resources table, and reference 2 is for general alloted table uploading purpose
            //DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("Complaints");
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Complaints");
            DatabaseReference finalReference1 = reference;
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    finalReference1.child(complaint_id).child("status").setValue("Complaint Accepted");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            reference = FirebaseDatabase.getInstance().getReference("Accepted Complaints");
            DatabaseReference finalReference = reference;
            String finalId1 = id;
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("name", name);
                    hashMap.put("email", email);
                    hashMap.put("item", item);
                    hashMap.put("itemId",itemId);
                    hashMap.put("complaintID", complaint_id);
                    hashMap.put("complaintDate", complaint_date);
                    hashMap.put("complaintDescription", complaint_description);
                    hashMap.put("location",location_description);
                    hashMap.put("acceptDate", date);
                    hashMap.put("id",finalId1);
                    finalReference.child(finalId1).setValue(hashMap);
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
                    hashMap.put("status", "Complaint accepted");
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

            Toast.makeText(view.getContext(), "Complaint Accepted", Toast.LENGTH_SHORT).show();
            Toast.makeText(view.getContext(), "Notification sent", Toast.LENGTH_SHORT).show();
        });


        // reject complaint
        holder.reject_btn.setOnClickListener(view -> {
            String email = complaint.getEmail();
            String item = complaint.getComplainedItem();
            String complaint_id = complaint.getComplaintID();


            String uniqueID;
            String lastFour;
            String id; // eg AC1234 is for alloted and C is for chair
            @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            //reference1 is to get access of resources table, and reference 2 is for general alloted table uploading purpose
            //DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("Complaints");
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Complaints");
            DatabaseReference finalReference1 = reference;
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    finalReference1.child(complaint_id).child("status").setValue("Complaint Rejected");
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
                    hashMap.put("status", "Complaint Rejected");
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

            Toast.makeText(view.getContext(), "Complaint Rejected", Toast.LENGTH_SHORT).show();
            Toast.makeText(view.getContext(), "Notification sent", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView complaint_id, complaint_item, complaint_description, complainter_name, complainter_email, complaint_date, complaint_status,complaint_item_id_input,location_description;
        Button accept_btn, reject_btn;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            complaint_id = itemView.findViewById(R.id.complaint_id);
            complaint_item = itemView.findViewById(R.id.complaint_item);
            complaint_description = itemView.findViewById(R.id.complaint_description);
            location_description=itemView.findViewById(R.id.location_description);
            complainter_name = itemView.findViewById(R.id.complainter_name);
            complainter_email = itemView.findViewById(R.id.complainter_email);
            complaint_date = itemView.findViewById(R.id.complaint_date);
            complaint_status = itemView.findViewById(R.id.complaint_status);
            accept_btn = itemView.findViewById(R.id.accept_btn);
            reject_btn = itemView.findViewById(R.id.reject_btn);
            complaint_item_id_input=itemView.findViewById(R.id.complaint_item_id_input);
        }
    }
}
