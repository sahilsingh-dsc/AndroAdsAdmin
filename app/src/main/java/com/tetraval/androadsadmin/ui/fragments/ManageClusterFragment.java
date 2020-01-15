package com.tetraval.androadsadmin.ui.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.adapters.ClusterAdapter;
import com.tetraval.androadsadmin.data.models.ClusterModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class ManageClusterFragment extends Fragment implements androidx.appcompat.widget.SearchView.OnQueryTextListener, SearchView.OnQueryTextListener {

    private RecyclerView recyclerCluster;
    private List<ClusterModel> clusterModelList;
    private ClusterAdapter clusterAdapter;
    private android.app.AlertDialog loadingDialog;
    private SearchView searchCluster;

    public ManageClusterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_cluster, container, false);

        searchCluster = view.findViewById(R.id.searchCluster);
        searchCluster.setOnQueryTextListener(this);

        loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.loading)
                .setMessage("Fetching Data")
                .setCancelable(false)
                .build();

        MaterialButton btnAddCluster = view.findViewById(R.id.btnAddCluster);
        btnAddCluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                @SuppressLint("InflateParams") View alertLayout = inflater.inflate(R.layout.add_cluster_alert, null);
                MaterialButton btnAddCluster = alertLayout.findViewById(R.id.btnAddCluster);
                final TextInputEditText txtClusterName = alertLayout.findViewById(R.id.txtClusterName);

                AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                alert.setTitle("Add Cluster");
                alert.setMessage(getString(R.string.cluster_alert_message));
                alert.setView(alertLayout);
                final AlertDialog dialog = alert.create();

                btnAddCluster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cluster_name = Objects.requireNonNull(txtClusterName.getText()).toString();
                        if (TextUtils.isEmpty(cluster_name)){
                            txtClusterName.setError("Cluster Name Required");
                            return;
                        }
                        DatabaseReference clusterRef = FirebaseDatabase.getInstance().getReference("ALL_CLUSTERS");
                        String cluster_id = clusterRef.push().getKey();
                        assert cluster_id != null;
                        ClusterModel clusterModel = new ClusterModel(
                                cluster_id,
                                cluster_name
                        );
                        clusterRef.child(cluster_id).setValue(clusterModel);
                        Toast.makeText(getContext(), "Cluster Added!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        recyclerCluster = view.findViewById(R.id.recyclerCluster);
        recyclerCluster.setLayoutManager(new LinearLayoutManager(getContext()));
        clusterModelList = new ArrayList<>();
        clusterModelList.clear();

        loadingDialog.show();
        fetchCluster();

        return  view;
    }

    private void fetchCluster(){
        DatabaseReference clusterRef = FirebaseDatabase.getInstance().getReference("ALL_CLUSTERS");
        clusterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clusterModelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ClusterModel clusterModel = new ClusterModel(
                      Objects.requireNonNull(dataSnapshot1.child("cluster_id").getValue()).toString(),
                      Objects.requireNonNull(dataSnapshot1.child("cluster_name").getValue()).toString()
                    );
                    clusterModelList.add(clusterModel);
                }

                clusterAdapter = new ClusterAdapter(clusterModelList, getContext());
                recyclerCluster.setAdapter(clusterAdapter);
                if (clusterModelList.isEmpty()){
                    Toast.makeText(getContext(), "There is no cluster currently.", Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<ClusterModel> clusterModelListNew = new ArrayList<>();
        for (ClusterModel clusterModel : clusterModelList){
            String cluster_name = clusterModel.getCluster_name().toLowerCase().replace(" ", "");
            if (cluster_name.contains(newText))
                clusterModelListNew.add(clusterModel);
        }
        clusterAdapter.setfilter(clusterModelListNew);
        return true;
    }
}
