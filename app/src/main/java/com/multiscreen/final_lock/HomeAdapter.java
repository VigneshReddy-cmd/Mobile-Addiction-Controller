package com.multiscreen.final_lock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<AppDetail> appDetails;
    private List<AppDetail> appDetailsFull; // Copy of full list for filtering

    // Constructor to initialize with a list of app details
    public HomeAdapter(List<AppDetail> appDetails) {
        this.appDetails = appDetails;
        this.appDetailsFull = new ArrayList<>(appDetails); // Create a copy of the original list
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppDetail appDetail = appDetails.get(position);
        holder.appName.setText(appDetail.getLabel());
        holder.appTime.setText(appDetail.getTime().toString()); // Use actual time value
        holder.appIcon.setImageDrawable(appDetail.getIcon());


    }


    @Override
    public int getItemCount() {
        return appDetails.size();
    }

    // Method to filter RecyclerView based on search query
    public void filterList(ArrayList<AppDetail> filteredList) {
        appDetails = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView appName;
        TextView appTime;
        ImageView appIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.appName);
            appTime = itemView.findViewById(R.id.appTime);
            appIcon = itemView.findViewById(R.id.appIcon);

            // Example: Set background tint for the ImageView
            appIcon.setBackgroundResource(R.drawable.home_adapter_even); // Replace with your drawable resource
        }
    }

}
