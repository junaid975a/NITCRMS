package com.example.module_user_login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.module_user_login.R;
import com.example.module_user_login.models.Resources;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.itemViewHolder>{


    Context context;

    ArrayList<Resources> list;

    public ItemAdapter(Context context, ArrayList<Resources> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        Resources item = list.get(position);
        holder.id.setText(item.getId());
        holder.item.setText(item.getItem());
        holder.category.setText(item.getCategory());
        holder.alloted.setText(item.getAlloted());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class itemViewHolder extends RecyclerView.ViewHolder {

        TextView id,item,category,alloted;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.tvitemid);
            item=itemView.findViewById(R.id.tvitemName);
            category=itemView.findViewById(R.id.tvitemCategory);
            alloted=itemView.findViewById(R.id.tvitemAlloted);
        }
    }
}
