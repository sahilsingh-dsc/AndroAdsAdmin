package com.tetraval.androadsadmin.ui.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.adapters.ImageAdapter;
import com.tetraval.androadsadmin.data.models.ImageModel;
import com.tetraval.androadsadmin.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class ImageFragment extends Fragment {

    RecyclerView recyclerImage;
    List<ImageModel> imageModelList;
    ImageAdapter imageAdapter;
    View view;
    private android.app.AlertDialog loadingDialog;

    int spanCount = 2;
    int spacing = 50;

    public ImageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image, container, false);

        loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();

        recyclerImage = view.findViewById(R.id.recyclerImage);
        recyclerImage.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerImage.addItemDecoration(new SpacesItemDecoration(spanCount, spacing, true));
        imageModelList = new ArrayList<>();
        imageModelList.clear();
        loadingDialog.show();
        DatabaseReference mediaRef = FirebaseDatabase.getInstance().getReference("ALL_MEDIA");
        mediaRef.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageModelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ImageModel imageModel = new ImageModel(
                            Objects.requireNonNull(dataSnapshot1.child("media_id").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot1.child("media_type").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot1.child("media_url").getValue()).toString()
                    );
                    imageModelList.add(imageModel);
                }

                imageAdapter = new ImageAdapter(imageModelList, getContext());
                recyclerImage.setAdapter(imageAdapter);
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
