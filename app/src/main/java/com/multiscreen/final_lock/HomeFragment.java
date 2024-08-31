package com.multiscreen.final_lock;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<AppDetail> lockedApps;
    private EditText searchEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Simulated data of locked apps (replace with your actual logic to fetch locked apps)
        lockedApps = getLockedApps();

        // Initialize and set up adapter
        homeAdapter = new HomeAdapter(lockedApps);
        recyclerView.setAdapter(homeAdapter);

        // Initialize search EditText
        searchEditText = view.findViewById(R.id.search_edit_text);
        setupSearch();

        return view;
    }

    // Simulated method to fetch locked apps (replace with your actual logic)
    private List<AppDetail> getLockedApps() {
        List<AppDetail> lockedApps = new ArrayList<>();
        // Simulated data
        lockedApps.add(new AppDetail("Discord", getResources().getDrawable(R.drawable.disc), new Time(0, 0, 45)));
        lockedApps.add(new AppDetail("Twitter", getResources().getDrawable(R.drawable.twitter), new Time(0, 0, 45)));
        lockedApps.add(new AppDetail("Facebook", getResources().getDrawable(R.drawable.facebook), new Time(0, 0, 45)));
        lockedApps.add(new AppDetail("Google", getResources().getDrawable(com.google.firebase.storage.R.drawable.googleg_disabled_color_18), new Time(0, 0, 45)));
        // Add more apps as needed
        return lockedApps;
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void filter(String text) {
        ArrayList<AppDetail> filteredList = new ArrayList<>();

        for (AppDetail appDetail : lockedApps) {
            if (appDetail.getLabel().toString().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(appDetail);
            }
        }

        homeAdapter.filterList(filteredList); // Update adapter with filtered list
    }
}
