package com.tetraval.androadsadmin.ui.activities.ads;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.adapters.ViewPagerAdapter;
import com.tetraval.androadsadmin.data.models.AdsModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import static java.security.AccessController.getContext;

public class NewAdsActivity extends AppCompatActivity {

    Toolbar toolbarAds;
    ImageView imgSelectMedia;
    TabLayout tabLayout;
    ViewPager pagerMedia;
    ViewPagerAdapter viewPagerAdapter;
    Calendar calendar;
    MaterialButton button;
    TextInputEditText txtStartDate, txtStartTime, txtEndDate, txtEndTime, txtTextAD;
    int dates = 0;
    int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ads);

        toolbarAds = findViewById(R.id.toolbarAds);
        toolbarAds.setTitle("New Ad");
        toolbarAds.setTitleTextColor(Color.WHITE);

        button = findViewById(R.id.button);

        tabLayout = findViewById(R.id.tabLayout);
        txtTextAD = findViewById(R.id.txtTextAD);
        pagerMedia = findViewById(R.id.pagerMedia);
        assert getFragmentManager() != null;
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerMedia.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(pagerMedia);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        txtStartDate = findViewById(R.id.txtStartDate);
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dates = 1;
                new DatePickerDialog(NewAdsActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        txtStartTime = findViewById(R.id.txtStartTime);
        txtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = 1;
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewAdsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        txtStartTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        txtEndDate = findViewById(R.id.txtEndDate);
        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dates = 2;
                new DatePickerDialog(NewAdsActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        txtEndTime = findViewById(R.id.txtEndTime);
        txtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = 2;
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NewAdsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String AM_PM ;
                        if(selectedHour < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        txtEndTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        calendar = Calendar.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("ad_info", 0);
                String media_id = sharedPreferences.getString("media_id", "");
                String media_type = sharedPreferences.getString("media_type", "");
                String media_url = sharedPreferences.getString("media_url", "");
                Bundle bundle = getIntent().getExtras();
                String tv_id = bundle.getString("tv_id");
                String start_date = txtStartDate.getText().toString();
                String start_time = txtStartTime.getText().toString();
                String end_date = txtEndDate.getText().toString();
                String end_time = txtEndTime.getText().toString();
                String text_ad = txtTextAD.getText().toString();
                String text_ad_status = "0";
                if (TextUtils.isEmpty(media_url)){
                    Toast.makeText(NewAdsActivity.this, "Please select media...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tv_id)){
                    Toast.makeText(NewAdsActivity.this, "TV ID not found...", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(start_date) || TextUtils.isEmpty(start_time) || TextUtils.isEmpty(end_date)  || TextUtils.isEmpty(end_time)){
                    Toast.makeText(NewAdsActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(text_ad)){
                    text_ad_status = "1";
                }


                pushNewAd(tv_id, media_id, media_type, media_url, start_date, start_time ,end_date ,end_time, text_ad, text_ad_status);

            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (dates == 1){
            txtStartDate.setText(sdf.format(calendar.getTime()));
        }else if (dates == 2){
            txtEndDate.setText(sdf.format(calendar.getTime()));
        }

    }

    private void pushNewAd(String tv_id, String media_id, String media_type, String media_url, String start_date, String start_time, String end_date, String end_time, String text_ad, String text_ad_status){
        DatabaseReference adRef = FirebaseDatabase.getInstance().getReference("ALL_ADS");
        String ad_id = adRef.push().getKey();
        AdsModel adsModel = new AdsModel(
                tv_id,
                ad_id,
                media_id,
                media_type,
                media_url,
                text_ad,
                text_ad_status,
                start_date,
                start_time,
                end_date,
                end_time
        );
        assert ad_id != null;
//        adRef.child(tv_id).child(ad_id).setValue(adsModel);
//        Toast.makeText(this, "New Ad Pushed!", Toast.LENGTH_SHORT).show();
//        onBackPressed();
//        finish();
        adRef.child(tv_id).child("1").setValue(adsModel);
        Toast.makeText(this, "New Ad Pushed!", Toast.LENGTH_SHORT).show();
        onBackPressed();
        finish();
    }

}
