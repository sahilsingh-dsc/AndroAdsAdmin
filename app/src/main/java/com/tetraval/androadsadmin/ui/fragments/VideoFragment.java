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
import com.tetraval.androadsadmin.data.adapters.VideoAdapter;
import com.tetraval.androadsadmin.data.models.ImageModel;
import com.tetraval.androadsadmin.data.models.VideoModel;
import com.tetraval.androadsadmin.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class VideoFragment extends Fragment {

    RecyclerView recyclerVideo;
    List<VideoModel> videoModelList;
    VideoAdapter videoAdapter;
    View view;
    private android.app.AlertDialog loadingDialog;

    int spanCount = 2;
    int spacing = 50;

    public VideoFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_video, container, false);

        loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.loading)
                .setMessage("Please Wait")
                .setCancelable(false)
                .build();

        recyclerVideo = view.findViewById(R.id.recyclerVideo);
        recyclerVideo.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerVideo.addItemDecoration(new SpacesItemDecoration(spanCount, spacing, true));
        videoModelList = new ArrayList<>();
        videoModelList.clear();
        loadingDialog.show();
        DatabaseReference mediaRef = FirebaseDatabase.getInstance().getReference("ALL_MEDIA");
        mediaRef.child("2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                videoModelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    VideoModel videoModel = new VideoModel(
                            Objects.requireNonNull(dataSnapshot1.child("media_id").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot1.child("media_type").getValue()).toString(),
                            Objects.requireNonNull(dataSnapshot1.child("media_url").getValue()).toString()
                    );
                    videoModelList.add(videoModel);
                }

                videoAdapter = new VideoAdapter(videoModelList, getContext());
                recyclerVideo.setAdapter(videoAdapter);
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
