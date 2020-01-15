package com.tetraval.androadsadmin.ui.fragments;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.ui.activities.tv.ManageTVActivity;

import dmax.dialog.SpotsDialog;

public class DashboardFragment extends Fragment {

    View view;
    TextView txtTVOnline, txtTotalAds;
    private AlertDialog loadingDialog;
    int adcount = 0, tvcount = 0;

    public DashboardFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        txtTVOnline = view.findViewById(R.id.txtTVOnline);
        txtTotalAds = view.findViewById(R.id.txtTotalAds);

        loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();

        loadingDialog.show();
        DatabaseReference allTVRef = FirebaseDatabase.getInstance().getReference("ALL_TVS");
        allTVRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    tvcount = (int) (dataSnapshot1.getChildrenCount() + tvcount);
                }
                txtTVOnline.setText(tvcount+"");
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingDialog.dismiss();
            }
        });

        loadingDialog.show();
        DatabaseReference allADSRef = FirebaseDatabase.getInstance().getReference("ALL_ADS");
        allADSRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    adcount = (int) (dataSnapshot1.getChildrenCount() + adcount);
                }
                txtTotalAds.setText(adcount+"");
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingDialog.dismiss();
            }
        });




        return view;
    }

}
