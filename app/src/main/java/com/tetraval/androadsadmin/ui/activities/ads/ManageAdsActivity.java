package com.tetraval.androadsadmin.ui.activities.ads;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.adapters.ADSAdapter;
import com.tetraval.androadsadmin.data.models.AdsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class ManageAdsActivity extends AppCompatActivity {

    RecyclerView recyclerAds;
    List<AdsModel> adsModelList;
    ADSAdapter adsAdapter;
    private AlertDialog loadingDialog;
    Toolbar toolbar;
    MaterialButton btnNewAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ads);

        loadingDialog = new SpotsDialog.Builder().setContext(ManageAdsActivity.this)
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Ads");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String tv_id = bundle.getString("tv_id");

        recyclerAds = findViewById(R.id.recyclerAds);
        recyclerAds.setLayoutManager(new LinearLayoutManager(this));
        adsModelList = new ArrayList<>();
        adsModelList.clear();

        btnNewAd = findViewById(R.id.btnNewAd);
        btnNewAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewAdsActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("tv_id", tv_id);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        loadingDialog.show();
        fetchAds(tv_id);
    }

    private void fetchAds(String tv_id){

        DatabaseReference adsRef = FirebaseDatabase.getInstance().getReference("ALL_ADS");
        adsRef.child(tv_id).child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adsModelList.clear();
                /*for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                      Log.e("manage", "dataSnapshot1=="+dataSnapshot);
                    Log.e("manage", "dataSnapshot1==" + dataSnapshot1.getKey());
                   // Log.e("manage", "dataSnapshot1==" + dataSnapshot1.getValue());
                    Log.e("manage", "dataSnapshot1==" + dataSnapshot1.child("ad_end_date").getValue());
                }*/
                if (dataSnapshot.exists()){
                    AdsModel adsModel = new AdsModel(
                            Objects.requireNonNull(dataSnapshot.child("tv_id").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("ad_id").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("ad_media_id").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("ad_media_type").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("ad_media_url").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("ad_media_text").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("ad_media_text_status").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("ad_start_date").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("ad_start_time").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("ad_end_date").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot.child("ad_end_time").getValue()).toString()
                    );

                    adsModelList.add(adsModel);

                    adsAdapter = new ADSAdapter(adsModelList, ManageAdsActivity.this);
                    recyclerAds.setAdapter(adsAdapter);
                    loadingDialog.dismiss();
                }else{
                    loadingDialog.dismiss();
                    Toast.makeText(ManageAdsActivity.this, "Currently there no ads running.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingDialog.dismiss();
            }
        });

//        DatabaseReference adsRef = FirebaseDatabase.getInstance().getReference("ALL_ADS");
//        adsRef.child(tv_id).child("1").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                adsModelList.clear();
//
//                    AdsModel adsModel = new AdsModel(
//                            Objects.requireNonNull(dataSnapshot.child("tv_id").getValue()).toString(),
//                            Objects.requireNonNull(dataSnapshot.child("ad_id").getValue()).toString(),
//                            Objects.requireNonNull(dataSnapshot.child("ad_media_id").getValue()).toString(),
//                            Objects.requireNonNull(dataSnapshot.child("ad_media_type").getValue()).toString(),
//                            Objects.requireNonNull(dataSnapshot.child("ad_media_url").getValue()).toString(),
//                            Objects.requireNonNull(dataSnapshot.child("ad_media_text").getValue()).toString(),
//                            Objects.requireNonNull(dataSnapshot.child("ad_media_text_status").getValue()).toString(),
//                            Objects.requireNonNull(dataSnapshot.child("ad_start_date").getValue()).toString(),
//                            Objects.requireNonNull(dataSnapshot.child("ad_start_time").getValue()).toString(),
//                            Objects.requireNonNull(dataSnapshot.child("ad_end_date").getValue()).toString(),
//                            Objects.requireNonNull(dataSnapshot.child("ad_end_time").getValue()).toString()
//                    );
//
//                    adsModelList.add(adsModel);
//
//                adsAdapter = new ADSAdapter(adsModelList, ManageAdsActivity.this);
//                recyclerAds.setAdapter(adsAdapter);
//                loadingDialog.dismiss();
//                if (adsModelList.isEmpty()){
//                    Toast.makeText(ManageAdsActivity.this, "Currently there no ads running.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                loadingDialog.dismiss();
//            }
//        });

    }

}