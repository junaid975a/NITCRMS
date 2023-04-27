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

public class UserItemsAdapter extends RecyclerView.Adapter<UserItemsAdapter.ViewHolder> {
    private final Context context;
    private final List<AllotedResources> list;

    public UserItemsAdapter(Context context, List<AllotedResources> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemsAdapter.ViewHolder holder, int position) {
        AllotedResources allotedResources = list.get(position);
        //holder.request_id.setText(allotedResources.getRequestID());
        holder.requested_item.setText(allotedResources.getItemName());
        holder.item_id.setText(allotedResources.getItemID());
        holder.request_date.setText(allotedResources.getRequestDate());
        holder.request_accept_date.setText(allotedResources.getAllotmentDate());
        // holder.duration.setText(allotedResources.getForDuration());
        holder.upto_date.setText(allotedResources.getUpto_date());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView requested_item, item_id,
                request_date, request_accept_date, duration, upto_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // request_id = itemView.findViewById(R.id.request_id);
            requested_item = itemView.findViewById(R.id.requested_item);
            item_id = itemView.findViewById(R.id.item_id);
            request_date = itemView.findViewById(R.id.request_date);
            request_accept_date = itemView.findViewById(R.id.request_accept_date);
            // duration = itemView.findViewById(R.id.duration);
            upto_date=itemView.findViewById(R.id.upto_date);
        }
    }
}
