package com.tetraval.androadsadmin.ui.activities.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.ui.activities.controller.MainActivity;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

import static com.tetraval.androadsadmin.ui.fragments.AddTVFragment.MY_PERMISSIONS_REQUEST_CAMERA;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbarLogin;
    TextInputEditText txtEmail, txtPassword;
    MaterialButton btnLogin;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                MY_PERMISSIONS_REQUEST_CAMERA);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
        }

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }



        toolbarLogin = findViewById(R.id.toolbarLogin);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        toolbarLogin.setTitle("Admin Login");
        toolbarLogin.setTitleTextColor(Color.WHITE);

        loadingDialog = new SpotsDialog.Builder().setContext(LoginActivity.this)
                .setTheme(R.style.loading)
                .setMessage("Authenticating")
                .setCancelable(false)
                .build();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Objects.requireNonNull(txtEmail.getText()).toString();
                String password = Objects.requireNonNull(txtPassword.getText()).toString();
                if (TextUtils.isEmpty(email)){
                    txtEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    txtPassword.setError("Password is required");
                    return;
                }
                loadingDialog.show();
                performLogin(email, password);
            }
        });

    }

    private void performLogin(String email, String password){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    loadingDialog.dismiss();
                }else {
                    Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }
            }
        });

    }

}
