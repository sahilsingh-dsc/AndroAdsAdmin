package com.tetraval.androadsadmin.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tetraval.androadsadmin.R;
import com.tetraval.androadsadmin.data.models.ClusterModel;
import com.tetraval.androadsadmin.ui.activities.tv.ManageTVActivity;

import java.util.ArrayList;
import java.util.List;

public class ClusterAdapter extends RecyclerView.Adapter<ClusterAdapter.ClusterViewHolder> {

    List<ClusterModel> clusterModelList;
    Context context;

    public ClusterAdapter(List<ClusterModel> clusterModelList, Context context) {
        this.clusterModelList = clusterModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClusterAdapter.ClusterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cluster_list_item, parent, false);
        return new ClusterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClusterAdapter.ClusterViewHolder holder, int position) {
        final ClusterModel clusterModel = clusterModelList.get(position);
        holder.txtClusterName.setText(clusterModel.getCluster_name());
        holder.txtClusterId.setText(String.format("Cluster ID : %s", clusterModel.getCluster_id()));
        holder.cardCluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ManageTVActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cluster_id", clusterModel.getCluster_id());
                bundle.putString("cluster_name", clusterModel.getCluster_name());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
//        holder.imgDeleteCluster.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatabaseReference clusterRef = FirebaseDatabase.getInstance().getReference("ALL_CLUSTERS");
//                clusterRef.child(clusterModel.getCluster_id()).removeValue();
//                Toast.makeText(context, ""+clusterModel.getCluster_name()+ " Removed!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return clusterModelList.size();
    }

    public void setfilter(List<ClusterModel> newlist) {
        clusterModelList = new ArrayList<>();
        clusterModelList.addAll(newlist);
        notifyDataSetChanged();
    }


    public class ClusterViewHolder extends RecyclerView.ViewHolder {

        TextView txtClusterName, txtClusterId;
        ConstraintLayout cardCluster;

        public ClusterViewHolder(@NonNull View itemView) {
            super(itemView);

            txtClusterName = itemView.findViewById(R.id.txtClusterName);
            txtClusterId = itemView.findViewById(R.id.txtClusterId);
            cardCluster = itemView.findViewById(R.id.cardCluster);

        }
    }
}
