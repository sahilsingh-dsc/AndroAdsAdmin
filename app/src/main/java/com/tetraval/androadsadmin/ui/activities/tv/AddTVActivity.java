package com.tetraval.androadsadmin.ui.activities.tv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.models.ClusterModel;

import java.util.Objects;

public class AddTVActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tv);

        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String cluster_id = bundle.getString("cluster_id");
        Toast.makeText(this, ""+cluster_id, Toast.LENGTH_SHORT).show();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_CAMERA);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
        }

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(Objects.requireNonNull(AddTVActivity.this), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(AddTVActivity.this, "" + result.getText(), Toast.LENGTH_SHORT).show();
                        LayoutInflater inflater = getLayoutInflater();
                        @SuppressLint("InflateParams") View alertLayout = inflater.inflate(R.layout.add_cluster_alert, null);
                        MaterialButton btnAddCluster = alertLayout.findViewById(R.id.btnAddCluster);
                        final TextInputEditText txtClusterName = alertLayout.findViewById(R.id.txtClusterName);

//                        AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getApplication()));
//                        alert.setTitle("Add Cluster");
//                        alert.setMessage(getString(R.string.cluster_alert_message));
//                        alert.setView(alertLayout);
//                        final AlertDialog dialog = alert.create();
//
//                        btnAddCluster.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                String cluster_name = Objects.requireNonNull(txtClusterName.getText()).toString();
//                                if (TextUtils.isEmpty(cluster_name)) {
//                                    txtClusterName.setError("Cluster Name Required");
//                                    return;
//                                }
//                                DatabaseReference clusterRef = FirebaseDatabase.getInstance().getReference("ALL_CLUSTERS");
//                                String cluster_id = clusterRef.push().getKey();
//                                assert cluster_id != null;
//                                ClusterModel clusterModel = new ClusterModel(
//                                        cluster_id,
//                                        cluster_name
//                                );
//                                clusterRef.child(cluster_id).setValue(clusterModel);
//                                Toast.makeText(getApplicationContext(), "Cluster Added!", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                        });
//
//                        dialog.show();
                        Intent intent = new Intent(getApplicationContext(), TVIntentActivity.class);
                        Bundle bundleADD = new Bundle();
                        bundleADD.putString("cluster_id", cluster_id);
                        bundleADD.putString("result", result.getText());
                        intent.putExtras(bundleADD);
                        startActivity(intent);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

    }
}
