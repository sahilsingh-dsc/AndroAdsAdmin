package com.tetraval.androadsadmin.data.models;

public class ClusterModel {
    private String cluster_id;
    private String cluster_name;

    public ClusterModel(String cluster_id, String cluster_name) {
        this.cluster_id = cluster_id;
        this.cluster_name = cluster_name;
    }

    public String getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(String cluster_id) {
        this.cluster_id = cluster_id;
    }

    public String getCluster_name() {
        return cluster_name;
    }

    public void setCluster_name(String cluster_name) {
        this.cluster_name = cluster_name;
    }
}
