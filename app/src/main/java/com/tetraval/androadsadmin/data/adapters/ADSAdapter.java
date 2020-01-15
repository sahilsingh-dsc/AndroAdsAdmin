package com.tetraval.androadsadmin.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.models.AdsModel;
import com.tetraval.androadsadmin.ui.activities.ads.ManageAdsActivity;

import java.util.List;

public class ADSAdapter extends RecyclerView.Adapter<ADSAdapter.ADSViewHolder> {

    List<AdsModel> adsModelList;
    Context context;

    public ADSAdapter(List<AdsModel> adsModelList, Context context) {
        this.adsModelList = adsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ADSAdapter.ADSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads_list_item, parent, false);
        return new ADSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADSAdapter.ADSViewHolder holder, int position) {
        final AdsModel adsModel = adsModelList.get(position);
        if (adsModel.getAd_media_type().equals("1")){
            holder.txtMediaType.setText("Image");
            Glide.with(context).load(adsModel.getAd_media_url()).into(holder.imgMediaType);
        }else if (adsModel.getAd_media_type().equals("2")){
            holder.txtMediaType.setText("Video");
            Glide.with(context).load(adsModel.getAd_media_url()).into(holder.imgMediaType);
        }
        holder.txtAdID.setText(String.format("AD ID : %s", adsModel.getAd_id()));
        holder.txtStartDateTime.setText(String.format("%s -- %s", adsModel.getAd_start_date(), adsModel.getAd_start_time()));
        holder.txtEndDateTime.setText(String.format("%s -- %s", adsModel.getAd_end_date(), adsModel.getAd_end_time()));

    }

    @Override
    public int getItemCount() {
        return adsModelList.size();
    }

    public class ADSViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMediaType;
        TextView txtAdID, txtMediaType, txtStartDateTime, txtEndDateTime;

        public ADSViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMediaType = itemView.findViewById(R.id.imgMediaType);
            txtAdID = itemView.findViewById(R.id.txtAdID);
            txtMediaType = itemView.findViewById(R.id.txtMediaType);
            txtStartDateTime = itemView.findViewById(R.id.txtStartDateTime);
            txtEndDateTime = itemView.findViewById(R.id.txtEndDateTime);

        }
    }
}
