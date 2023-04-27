package com.example.module_user_login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.models.AllotedResources;

import java.util.List;

public class AllotedResourcesAdapter extends RecyclerView.Adapter<AllotedResourcesAdapter.ViewHolder> {

    private final Context context;
    private final List<AllotedResources> list;

    public AllotedResourcesAdapter(Context context, List<AllotedResources> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alloted_resources,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllotedResources allotedResources = list.get(position);
        holder.request_id.setText(allotedResources.getRequestID());
        holder.requested_item.setText(allotedResources.getItemName());
        holder.item_id.setText(allotedResources.getItemID());
        holder.name.setText(allotedResources.getUserName());
        holder.requester_email.setText(allotedResources.getUserEmail());
        holder.request_date.setText(allotedResources.getRequestDate());
        holder.request_accept_date.setText(allotedResources.getAllotmentDate());
        // holder.duration.setText(allotedResources.getForDuration());
        holder.location_description.setText(allotedResources.getLocation());
        holder.upto_date.setText(allotedResources.getUpto_date());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView request_id, requested_item, item_id, name, requester_email, request_date, request_accept_date, duration,location_description,upto_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            request_id = itemView.findViewById(R.id.request_id);
            requested_item = itemView.findViewById(R.id.requested_item);
            item_id = itemView.findViewById(R.id.item_id);
            name = itemView.findViewById(R.id.name);
            requester_email = itemView.findViewById(R.id.requester_email);
            request_date = itemView.findViewById(R.id.request_date);
            request_accept_date = itemView.findViewById(R.id.request_accept_date);
            // duration = itemView.findViewById(R.id.duration);
            location_description=itemView.findViewById(R.id.location_description);
            upto_date=itemView.findViewById(R.id.upto_date);
        }
    }
}
