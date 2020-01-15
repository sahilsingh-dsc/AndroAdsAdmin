package com.tetraval.androadsadmin.ui.activities.controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.ui.activities.auth.LoginActivity;
import com.tetraval.androadsadmin.ui.fragments.DashboardFragment;
import com.tetraval.androadsadmin.ui.fragments.ManageBranchFragment;
import com.tetraval.androadsadmin.ui.fragments.ManageClusterFragment;
import com.tetraval.androadsadmin.ui.fragments.ManageMediaFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
        String title = getString(R.string.dashboard);

        toolbarMain = findViewById(R.id.toolbarMain);

        bottomNavigationView.setSelectedItemId(R.id.menu_dashboard);
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle(title);
        toolbarMain.setTitleTextColor(Color.WHITE);
        toolbarMain.getOverflowIcon().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to AndroAds", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        String title;

        switch (item.getItemId()){

            case R.id.menu_dashboard :
                fragment = new DashboardFragment();
                title = getString(R.string.dashboard);
                toolbarMain.setTitle(title);
                break;

            case R.id.menu_managecluster :
                fragment = new ManageClusterFragment();
                title = getString(R.string.menege_cluster);
                toolbarMain.setTitle(title);
                break;

            case R.id.menu_managemedia :
                fragment = new ManageMediaFragment();
                title = getString(R.string.media);
                toolbarMain.setTitle(title);
                break;

            case R.id.menu_managebranch :
                fragment = new ManageBranchFragment();
                title = getString(R.string.manage_branch);
                toolbarMain.setTitle(title);
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_menuitem){
            Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
