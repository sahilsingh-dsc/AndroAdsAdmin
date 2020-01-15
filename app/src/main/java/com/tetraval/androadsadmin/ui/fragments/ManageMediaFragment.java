package com.tetraval.androadsadmin.ui.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.adapters.ViewPagerAdapter;
import com.tetraval.androadsadmin.data.models.MediaModel;

import java.util.Objects;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;

public class ManageMediaFragment extends Fragment {

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private android.app.AlertDialog loadingDialog;
    private String media = "0";
    TabLayout tabLayout;
    ViewPager pagerMedia;
    ViewPagerAdapter viewPagerAdapter;

    public ManageMediaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_media, container, false);

        loadingDialog = new SpotsDialog.Builder().setContext(getContext())
                .setTheme(R.style.loading)
                .setMessage("Uploading Media")
                .setCancelable(false)
                .build();

        tabLayout = view.findViewById(R.id.tabLayout);
        pagerMedia = view.findViewById(R.id.pagerMedia);
        assert getFragmentManager() != null;
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        pagerMedia.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(pagerMedia);

        MaterialButton btnUploadMedia = view.findViewById(R.id.btnUploadMedia);
        btnUploadMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                @SuppressLint("InflateParams") View alertLayout = inflater.inflate(R.layout.media_type_alert, null);
                TextView txtImage = alertLayout.findViewById(R.id.txtImage);
                TextView txtVideo = alertLayout.findViewById(R.id.txtVideo);

                AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                alert.setView(alertLayout);
                final AlertDialog dialog = alert.create();
                txtImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Image", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                        media = "1";
                        dialog.dismiss();
                    }
                });
                txtVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Video", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setType("video/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_IMAGE_REQUEST);
                        media = "2";
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            if (media.equals("1")) {
                uploadImage();
            } else if (media.equals("2")) {
                uploadVideo();
            }
        }

    }

    private void uploadImage() {

        if (filePath != null) {
            FirebaseStorage storage;
            StorageReference storageReference;
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();


            if (filePath != null) {
                loadingDialog.show();

                final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                loadingDialog.dismiss();
                                Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        DatabaseReference mediaRef = FirebaseDatabase.getInstance().getReference("ALL_MEDIA");
                                        String media_id = mediaRef.push().getKey();
                                        MediaModel mediaModel = new MediaModel(
                                                media_id,
                                                media,
                                                uri.toString()

                                        );
                                        assert media_id != null;
                                        mediaRef.child("1").child(media_id).setValue(mediaModel);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialog.dismiss();
                                Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }

    private void uploadVideo() {

        if (filePath != null) {
            FirebaseStorage storage;
            StorageReference storageReference;
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();


            if (filePath != null) {
                loadingDialog.show();

                final StorageReference ref = storageReference.child("videos/" + UUID.randomUUID().toString());
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                loadingDialog.dismiss();
                                Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        DatabaseReference mediaRef = FirebaseDatabase.getInstance().getReference("ALL_MEDIA");
                                        String media_id = mediaRef.push().getKey();
                                        MediaModel mediaModel = new MediaModel(
                                                media_id,
                                                media,
                                                uri.toString()

                                        );
                                        assert media_id != null;
                                        mediaRef.child("2").child(media_id).setValue(mediaModel);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingDialog.dismiss();
                                Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }

}