package com.tetraval.androadsadmin.ui.activities.tv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.adapters.TVAdapters;
import com.tetraval.androadsadmin.data.models.TVModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class ManageTVActivity extends AppCompatActivity {

    private RecyclerView recyclerManage;
    private List<TVModels> tvModelsList;
    private TVAdapters tvAdapters;
    private AlertDialog loadingDialog;
    String c_id;
    MaterialButton btnAddTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_tv);

        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String cluster_id = bundle.getString("cluster_id");
        String cluster_name = bundle.getString("cluster_name");
        c_id = cluster_id;


        Toolbar toolbarTV = findViewById(R.id.toolbarTV);
        setSupportActionBar(toolbarTV);
        getSupportActionBar().setTitle("("+cluster_name+")"+" Manage TV");
        toolbarTV.setTitleTextColor(Color.WHITE);
        toolbarTV.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

        btnAddTV = findViewById(R.id.btnAddTV);
        btnAddTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTVActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("cluster_id", cluster_id);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        recyclerManage = findViewById(R.id.recyclerManage);
        recyclerManage.setLayoutManager(new LinearLayoutManager(this));

        tvModelsList = new ArrayList<>();
        tvModelsList.clear();

        loadingDialog = new SpotsDialog.Builder().setContext(ManageTVActivity.this)
                .setTheme(R.style.loading)
                .setMessage("Fetching Data")
                .setCancelable(false)
                .build();

        loadingDialog.show();
        fetchTVs(cluster_id);

    }

    private void fetchTVs(String cluster_id){
        DatabaseReference allTVRef = FirebaseDatabase.getInstance().getReference("ALL_TVS");
        allTVRef.child(cluster_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvModelsList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    TVModels tvModels = new TVModels(
                            Objects.requireNonNull(dataSnapshot1.child("cluster_id").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot1.child("tv_id").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot1.child("tv_name").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot1.child("tv_uptime").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot1.child("tv_status").getValue()).toString()
                    );
                    tvModelsList.add(tvModels);
                }
                tvAdapters = new TVAdapters(tvModelsList, ManageTVActivity.this);
                recyclerManage.setAdapter(tvAdapters);
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_tv_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_cluster_menuitem){
                DatabaseReference tvRef = FirebaseDatabase.getInstance().getReference("ALL_TVS");
                tvRef.child(c_id).removeValue();
                DatabaseReference clusterRef = FirebaseDatabase.getInstance().getReference("ALL_CLUSTERS");
                clusterRef.child(c_id).removeValue();
                Toast.makeText(this, "Removed!", Toast.LENGTH_SHORT).show();
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
