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
import com.example.module_user_login.models.ValidityExtend;

import java.util.List;
public class PreviousValidityRequestAdapter extends RecyclerView.Adapter<PreviousValidityRequestAdapter.ViewHolder> {
    private final Context context;
    private final List<ValidityExtend> list;

    public PreviousValidityRequestAdapter(Context context, List<ValidityExtend> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.previous_reallocation_requests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ValidityExtend requests = list.get(position);
        holder.itemName.setText(requests.getItemName());
        holder.itemId.setText(requests.getItemId());
        holder.oldUptoDate.setText(requests.getOldUptoDate());
        holder.newUptoDate.setText(requests.getNewUptoDate());
        holder.status.setText(requests.getStatus());
        holder.validityReqDate.setText(requests.getValidityReqDate());
        holder.description.setText(requests.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemId,oldUptoDate,newUptoDate,validityReqDate,status,description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemId = itemView.findViewById(R.id.item_id);
            oldUptoDate = itemView.findViewById(R.id.old_upto_date);
            newUptoDate=itemView.findViewById(R.id.new_upto_date);
            status = itemView.findViewById(R.id.validity_status);
            validityReqDate=itemView.findViewById(R.id.validity_request_date);
            description=itemView.findViewById(R.id.validity_description);
        }
    }
}
