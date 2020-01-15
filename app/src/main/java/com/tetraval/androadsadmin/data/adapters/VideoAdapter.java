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
import com.tetraval.androadsadmin.data.models.VideoModel;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ImageViewHolder> {

    private List<VideoModel> videoModelList;
    private Context context;

    public VideoAdapter(List<VideoModel> videoModelList, Context context) {
        this.videoModelList = videoModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ImageViewHolder holder, int position) {
        final VideoModel videoModel = videoModelList.get(position);
        Glide.with(context).load(videoModel.getMedia_url()).into(holder.imgMedia);
        holder.linearMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("ad_info", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("media_id", videoModel.getMedia_id());
                editor.putString("media_type", videoModel.getMedia_type());
                editor.putString("media_url", videoModel.getMedia_url());
                editor.apply();
                Toast.makeText(context, "Media Selected : "+videoModel.getMedia_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
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
