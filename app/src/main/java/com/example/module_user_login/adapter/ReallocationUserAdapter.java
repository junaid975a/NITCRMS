package com.example.module_user_login.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.models.AllotedResources;
import com.example.module_user_login.models.ValidityExtend;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ReallocationUserAdapter extends RecyclerView.Adapter<ReallocationUserAdapter.ViewHolder> {
    private final Context context;
    private final List<AllotedResources> list;
    String editDate="";


    public ReallocationUserAdapter(Context context, List<AllotedResources> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_reallocation, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllotedResources resource = list.get(position);
        holder.item.setText(resource.getItemName());
        holder.item_id.setText(resource.getItemID());
        holder.request_date.setText((resource.getRequestDate()));
        holder.request_accept_date.setText(resource.getAllotmentDate());
        holder.old_upto_date.setText(resource.getUpto_date());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        holder.choose_date.setOnClickListener(view13 -> {
            @SuppressLint("SetTextI18n") DatePickerDialog dialog = new DatePickerDialog(view13.getContext(), (datePicker, day1, month1, year1) -> {

                calendar.set(Calendar.YEAR, day1);
                calendar.set(Calendar.MONTH, month1);
                calendar.set(Calendar.DAY_OF_MONTH, year1);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                editDate = format.format(calendar.getTime());
                holder.new_upto_date.setText(editDate);

               // new_upto_date.setText("New Validity Period : "+editDate);
            }, year, month, day);
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() + 24*60*60*1000);
            dialog.show();
        });

        holder.request_btn.setOnClickListener(view -> {
            String item_name = resource.getItemName();
            String item_id_txt = resource.getItemID();
            String new_upto_dt = String.valueOf(holder.new_upto_date.getText());
            String req_date=resource.getRequestDate();
            String req_accept_date=resource.getAllotmentDate();
            String old_upto_dt=resource.getUpto_date();
            String status = "pending";
            String description="none";
            String email=resource.getUserEmail();
            String name=resource.getUserName();

            //id
            //date
            @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String uniqueID = UUID.randomUUID().toString();
            String lastFour = uniqueID.substring(uniqueID.length()-4);
            String req_id = "V"+item_name.charAt(0)+lastFour;


            if(TextUtils.isEmpty(new_upto_dt)) {
                Toast.makeText(view.getContext(), "new date  is required", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();

                ValidityExtend validityExtend = new ValidityExtend(item_name,item_id_txt,new_upto_dt,req_date,req_accept_date,old_upto_dt,status,date,req_id,description,email,name);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        databaseReference.child("ValidityExtend").child(req_id).setValue(validityExtend);
                        Toast.makeText(view.getContext(), "Request sent successfully", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getActivity(), MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(view.getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            holder.new_upto_date.setText("");
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView item, item_id, request_date,request_accept_date,old_upto_date,new_upto_date,choose_date;

        AppCompatButton request_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.requested_item);
            item_id = itemView.findViewById(R.id.item_id);
            request_date=itemView.findViewById(R.id.request_date);
            request_accept_date = itemView.findViewById(R.id.request_accept_date);
            old_upto_date = itemView.findViewById(R.id.old_upto_date);
            choose_date = itemView.findViewById(R.id.choose_date);
            request_btn=itemView.findViewById(R.id.request_btn);
            new_upto_date=itemView.findViewById(R.id.new_upto_date);

        }
    }
}