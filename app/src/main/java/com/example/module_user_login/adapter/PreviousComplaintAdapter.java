package com.example.module_user_login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.models.Complaints;

import java.util.List;

public class PreviousComplaintAdapter extends RecyclerView.Adapter<PreviousComplaintAdapter.ViewHolder> {
    private final Context context;
    private final List<Complaints> list;

    public PreviousComplaintAdapter(Context context, List<Complaints> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.previous_complaints,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaints complaints = list.get(position);
        holder.complaint_item.setText(complaints.getComplainedItem());
        holder.complaint_desc.setText(complaints.getComplaintDescription());
        holder.complaint_item_id.setText(complaints.getItemId());
        holder.date.setText(complaints.getDate());
        holder.status.setText(complaints.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView complaint_item, complaint_item_id, complaint_desc, date, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complaint_item =itemView.findViewById(R.id.complaint_item);
            complaint_item_id = itemView.findViewById(R.id.complaint_item_id_input);
            complaint_desc = itemView.findViewById(R.id.complaint_description);
            date = itemView.findViewById(R.id.complaint_date);
            status = itemView.findViewById(R.id.complaint_status);
        }
    }
}
