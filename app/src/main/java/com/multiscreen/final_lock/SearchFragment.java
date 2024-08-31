package com.multiscreen.final_lock;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements RecyclerViewInterface {

    private RecyclerView recyclerView;
    private AppAdapter appAdapter;
    private ArrayList<AppDetail> appDetails;
    private EditText searchEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        searchEditText = view.findViewById(R.id.search_edit_text);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        appDetails = new ArrayList<>();

        // Initialize RecyclerView with all installed apps
        setupRecyclerView();

        // Setup search functionality
        setupSearch();

        return view;
    }

    private void setupRecyclerView() {
        PackageManager packageManager = requireActivity().getPackageManager();
        List<ApplicationInfo> installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo appInfo : installedApps) {
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                // Installed by user
                CharSequence label = packageManager.getApplicationLabel(appInfo);
                Drawable icon = packageManager.getApplicationIcon(appInfo);
                appDetails.add(new AppDetail(label, icon));
            }
        }

        appAdapter = new AppAdapter(getActivity(), appDetails, this);
        recyclerView.setAdapter(appAdapter);
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

        for (AppDetail appDetail : appDetails) {
            if (appDetail.getLabel().toString().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(appDetail);
            }
        }

        appAdapter.filterList(filteredList);
    }

    @Override
    public void onItemClick(int pos) {
        AppDetail appDetail = appDetails.get(pos);
        Intent intent = new Intent(getActivity(), SelectApp.class);
        intent.putExtra("AppDetail", appDetail); // Passing the Parcelable object
        startActivity(intent);
    }



}
