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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.models.TVModels;
import com.tetraval.androadsadmin.ui.activities.ads.ManageAdsActivity;

import java.util.List;

public class TVAdapters extends RecyclerView.Adapter<TVAdapters.TVViewHolder> {

    List<TVModels> tvModelsList;
    Context context;

    public TVAdapters(List<TVModels> tvModelsList, Context context) {
        this.tvModelsList = tvModelsList;
        this.context = context;
    }

    @NonNull
    @Override
    public TVAdapters.TVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_item_layout, parent, false);
        return new TVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVAdapters.TVViewHolder holder, int position) {
        final TVModels tvModels = tvModelsList.get(position);
        holder.txtClusterName.setText(tvModels.getTv_name());
        holder.txtTVId.setText(String.format("TV ID : %s", tvModels.getTv_id()));
        holder.txtUpTime.setText(String.format("Uptime : %s Hours", tvModels.getTv_uptime()));
        if (tvModels.getTv_status().equals("1")){
            holder.imgTVStatus.setImageResource(R.drawable.ic_live_tv_online_24dp);
        }else if (tvModels.getTv_status().equals("0")){
            holder.imgTVStatus.setImageResource(R.drawable.ic_live_tv_offline_24dp);
        }

        holder.cardTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ManageAdsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tv_id", tvModels.getTv_id());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvModelsList.size();
    }

    public class TVViewHolder extends RecyclerView.ViewHolder {

        TextView txtClusterName, txtTVId, txtUpTime;
        ImageView imgTVStatus;
        ConstraintLayout cardTV;

        public TVViewHolder(@NonNull View itemView) {
            super(itemView);

            txtClusterName = itemView.findViewById(R.id.txtClusterName);
            txtTVId = itemView.findViewById(R.id.txtTVId);
            txtUpTime = itemView.findViewById(R.id.txtUpTime);
            imgTVStatus = itemView.findViewById(R.id.imgTVStatus);
            cardTV = itemView.findViewById(R.id.cardTV);

        }
    }
}
