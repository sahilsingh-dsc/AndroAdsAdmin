package com.tetraval.androadsadmin.ui.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.models.UserModel;
import com.tetraval.androadsadmin.ui.activities.auth.LoginActivity;
import com.tetraval.androadsadmin.ui.activities.controller.MainActivity;

public class ManageBranchFragment extends Fragment {


    DatabaseReference userRef;
    TextInputEditText txtEmail, txtPassword;
    MaterialButton btnLogin;

    public ManageBranchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_branch, container, false);

        txtEmail = view.findViewById(R.id.txtEmail);
        txtPassword = view.findViewById(R.id.txtPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        userRef = FirebaseDatabase.getInstance().getReference("USER_DATA");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                addUser(email, password);
            }
        });

        return view;
    }

    public void addUser(String email, String password) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "User Added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "User Not Added!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}