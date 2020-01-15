package com.tetraval.androadsadmin.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.adapters.TVAdapters;
import com.tetraval.androadsadmin.data.models.TVModels;
import com.tetraval.androadsadmin.ui.activities.auth.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class ManageTVFragment extends Fragment {

    private RecyclerView recyclerManage;
    private List<TVModels> tvModelsList;
    private TVAdapters tvAdapters;
    private AlertDialog loadingDialog;

    public ManageTVFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_tv, container, false);

        recyclerManage = view.findViewById(R.id.recyclerManage);
        recyclerManage.setLayoutManager(new LinearLayoutManager(getContext()));

        tvModelsList = new ArrayList<>();
        tvModelsList.clear();

        loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.loading)
                .setMessage("Fetching Data")
                .setCancelable(false)
                .build();

        loadingDialog.show();
        fetchTVs();

        return view;
    }

    private void fetchTVs(){
        DatabaseReference allTVRef = FirebaseDatabase.getInstance().getReference("ALL_TVS");
        allTVRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                tvModelsList.clear();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    TVModels tvModels = new TVModels(
//                     Objects.requireNonNull(dataSnapshot1.child("tv_id").getValue()).toString(),
//                     Objects.requireNonNull(dataSnapshot1.child("tv_cluster").getValue()).toString(),
//                     Objects.requireNonNull(dataSnapshot1.child("tv_uptime").getValue()).toString(),
//                     Objects.requireNonNull(dataSnapshot1.child("tv_status").getValue()).toString()
//                    );
//                    tvModelsList.add(tvModels);
//                }
                tvAdapters = new TVAdapters(tvModelsList, getContext());
                recyclerManage.setAdapter(tvAdapters);
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingDialog.dismiss();
            }
        });
    }

}
