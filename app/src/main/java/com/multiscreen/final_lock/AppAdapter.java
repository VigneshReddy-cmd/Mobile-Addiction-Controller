package com.multiscreen.final_lock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private List<AppDetail> appDetails;
    private Context context;
    private final RecyclerViewInterface recyclerViewInterface;

    public AppAdapter(Context context, List<AppDetail> appDetails, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.appDetails = appDetails;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppDetail appDetail = appDetails.get(position);
        holder.appLabel.setText(appDetail.getLabel());
        holder.appIcon.setImageDrawable(appDetail.getIcon());

        holder.itemView.setOnClickListener(v -> {
            if (recyclerViewInterface != null) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    recyclerViewInterface.onItemClick(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appDetails.size();
    }

    public void filterList(ArrayList<AppDetail> filteredList) {
        appDetails = filteredList; // Update dataset with filtered list
        notifyDataSetChanged(); // Notify adapter of dataset change
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView appLabel;
        ImageView appIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appLabel = itemView.findViewById(R.id.app_label);
            appIcon = itemView.findViewById(R.id.app_icon);
        }
    }
}
