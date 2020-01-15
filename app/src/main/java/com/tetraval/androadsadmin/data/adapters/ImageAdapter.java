package com.tetraval.androadsadmin.data.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.models.ImageModel;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<ImageModel> imageModelList;
    private Context context;

    public ImageAdapter(List<ImageModel> imageModelList, Context context) {
        this.imageModelList = imageModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {
        final ImageModel imageModel = imageModelList.get(position);
        Glide.with(context).load(imageModel.getMedia_url()).into(holder.imgMedia);
        holder.linearMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("ad_info", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("media_id", imageModel.getMedia_id());
                editor.putString("media_type", imageModel.getMedia_type());
                editor.putString("media_url", imageModel.getMedia_url());
                editor.apply();
                Toast.makeText(context, "Media Selected : "+imageModel.getMedia_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageModelList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMedia;
        TextView txtMediaId;
        LinearLayout linearMedia;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMedia = itemView.findViewById(R.id.imgMedia);
            linearMedia = itemView.findViewById(R.id.linearMedia);

        }
    }
}
