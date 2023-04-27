package com.example.module_user_login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.models.Requests;
import com.example.module_user_login.models.Resources;

import java.util.List;

public class PreviousRequestsAdapter extends RecyclerView.Adapter<PreviousRequestsAdapter.ViewHolder>{

    private final Context context;
    private final List<Requests> list;

    public PreviousRequestsAdapter(Context context, List<Requests> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PreviousRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.previous_requests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousRequestsAdapter.ViewHolder holder, int position) {
        Requests requests = list.get(position);
        holder.requested_item.setText(requests.getRequestedItem());
        holder.request_date.setText(requests.getDate());
        holder.request_status.setText(requests.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView requested_item,request_date, request_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //request_id = itemView.findViewById(R.id.request_id);
            requested_item = itemView.findViewById(R.id.requested_item);
            request_date = itemView.findViewById(R.id.request_date);
            request_status = itemView.findViewById(R.id.request_status);
        }
    }
}
