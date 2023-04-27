package com.example.module_user_login.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.models.AllotedResources;
import com.example.module_user_login.models.Complaints;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ComplaintUserAdapter extends RecyclerView.Adapter<ComplaintUserAdapter.ViewHolder> {

    private final Context context;
    private final List<AllotedResources> list;
    public ComplaintUserAdapter(Context context, List<AllotedResources>list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_complaint_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllotedResources resource = list.get(position);
        holder.item.setText(resource.getItemName());
        holder.item_id.setText(resource.getItemID());
        holder.location.setText(resource.getLocation());


        holder.complaint_btn.setOnClickListener(view -> {
            String item_name = resource.getItemName();
            String description = String.valueOf(holder.complaint_description.getText());
            String email = resource.getUserEmail();
            String item_id_txt = resource.getItemID();
            String location_txt = resource.getLocation();
            String user_name = resource.getUserName();
            String status = "pending";

            //id
            //date
            @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String uniqueID = UUID.randomUUID().toString();
            String lastFour = uniqueID.substring(uniqueID.length()-4);
            String complaint_id = "C"+item_name.charAt(0)+lastFour;


            if(TextUtils.isEmpty(description)) {
                Toast.makeText(view.getContext(), "description is required", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();

                Complaints complaint = new Complaints(user_name,email,item_name,description,complaint_id,date,item_id_txt,location_txt,status, resource.getUpto_date());

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.child("Complaints").child(complaint_id).setValue(complaint);
                        Toast.makeText(view.getContext(), "Complaint sent successfully", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getActivity(), MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(view.getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.complaint_description.setText("");
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView item, item_id, location;
        EditText complaint_description;
        AppCompatButton complaint_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.requested_item);
            item_id = itemView.findViewById(R.id.item_id);
            location = itemView.findViewById(R.id.location);
            complaint_description = itemView.findViewById(R.id.complaint_description);
            complaint_btn = itemView.findViewById(R.id.complaint_btn);
        }
    }
}
