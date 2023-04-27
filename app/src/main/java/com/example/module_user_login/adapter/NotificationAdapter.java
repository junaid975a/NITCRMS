package com.example.module_user_login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.module_user_login.R;
import com.example.module_user_login.models.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final Context context;
    private final List<Notification>list;

    public NotificationAdapter(Context context, List<Notification> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Notification notification = list.get(position);
        holder.notification_msg_item.setText(notification.getItem());
        holder.notification_msg_date.setText(notification.getDate());
        holder.notification_msg_status.setText(notification.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView notification_msg_item, notification_msg_date, notification_msg_status;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            notification_msg_item = itemView.findViewById(R.id.notification_msg_item);
            notification_msg_date = itemView.findViewById(R.id.notification_msg_date);
            notification_msg_status = itemView.findViewById(R.id.notification_msg_status);
        }
    }
}
