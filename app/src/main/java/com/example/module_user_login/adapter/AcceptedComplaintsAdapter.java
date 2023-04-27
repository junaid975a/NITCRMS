package com.example.module_user_login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.models.AcceptedComplaints;

import java.util.List;

public class AcceptedComplaintsAdapter extends RecyclerView.Adapter<AcceptedComplaintsAdapter.ViewHolder> {

    private final Context context;
    private final List<AcceptedComplaints> list;

    public AcceptedComplaintsAdapter(Context context, List<AcceptedComplaints> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.accepted_complaint,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AcceptedComplaints acceptedComplaints = list.get(position);
        holder.accept_date.setText(acceptedComplaints.getAcceptDate());
        holder.complaint_date.setText(acceptedComplaints.getComplaintDate());
        holder.complaint_id.setText(acceptedComplaints.getComplaintID());
        holder.complaint_item.setText(acceptedComplaints.getItem());
        holder.complaint_description.setText(acceptedComplaints.getComplaintDescription());
        holder.complainter_name.setText(acceptedComplaints.getName());
        holder.complainter_email.setText(acceptedComplaints.getEmail());
        holder.complaint_item_id_input.setText(acceptedComplaints.getItemId());
        holder.location_description.setText(acceptedComplaints.getLocation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView complaint_id, complaint_item, complaint_description, complainter_name, complainter_email, complaint_date, accept_date,complaint_item_id_input,location_description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complaint_id = itemView.findViewById(R.id.complaint_id);
            complaint_item = itemView.findViewById(R.id.complaint_item);
            complaint_description = itemView.findViewById(R.id.complaint_description);
            complainter_name = itemView.findViewById(R.id.complainter_name);
            complainter_email = itemView.findViewById(R.id.complainter_email);
            complaint_date = itemView.findViewById(R.id.complaint_date);
            accept_date = itemView.findViewById(R.id.complaint_accept_date);
            complaint_item_id_input=itemView.findViewById(R.id.complaint_item_id_input);
            location_description=itemView.findViewById(R.id.location_description);
        }
    }
}
