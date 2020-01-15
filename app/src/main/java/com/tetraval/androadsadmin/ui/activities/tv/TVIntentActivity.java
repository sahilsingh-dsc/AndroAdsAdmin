package com.tetraval.androadsadmin.ui.activities.tv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.models.TVModels;

import java.util.Objects;

public class TVIntentActivity extends AppCompatActivity {

    Toolbar toolbarAddTV;
    TextInputEditText txtTVName;
    RadioButton rbOffline, rbOnline;
    MaterialButton btnAddTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvintent);

        final String[] tv_status = new String[1];
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String cluster_id = bundle.getString("cluster_id");
        final String result = bundle.getString("result");
        Toast.makeText(this, ""+result, Toast.LENGTH_SHORT).show();

        toolbarAddTV = findViewById(R.id.toolbarAddTV);
        toolbarAddTV.setTitle("Add TV");
        toolbarAddTV.setTitleTextColor(Color.WHITE);

        txtTVName = findViewById(R.id.txtTVName);
        rbOffline = findViewById(R.id.rbOffline);
        rbOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_status[0] = "0";
            }
        });
        rbOnline = findViewById(R.id.rbOnline);
        rbOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_status[0] = "1";
            }
        });
        rbOnline.setChecked(true);
        tv_status[0] = "1";


        btnAddTv = findViewById(R.id.btnAddTv);
        btnAddTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tv_name = Objects.requireNonNull(txtTVName.getText()).toString();
                if (TextUtils.isEmpty(tv_name)){
                    txtTVName.setError("TV Name Required");
                    return;
                }

                addTv(cluster_id, tv_name, result, tv_status[0]);

            }
        });

    }

    private void addTv(String cluster_id, String tv_name, String result, String tv_status){
        DatabaseReference allTVRef = FirebaseDatabase.getInstance().getReference("ALL_TVS");
        DatabaseReference tvRef = FirebaseDatabase.getInstance().getReference("TVS_ID");

        String tv_id = allTVRef.push().getKey();
        TVModels tvModels = new TVModels(
                cluster_id,
                result,
                tv_name,
                "0",
                tv_status
        );
        assert tv_id != null;
        allTVRef.child(cluster_id).child(tv_id).setValue(tvModels);
        tvRef.child(tv_id).setValue("exist");
        Toast.makeText(this, "TV Added!", Toast.LENGTH_SHORT).show();
        onBackPressed();
        onBackPressed();
        finish();
    }

}
