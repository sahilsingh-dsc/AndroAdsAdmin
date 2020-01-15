package com.tetraval.androadsadmin.data.models;

public class TVModels {

    String cluster_id;
    String tv_id;
    String tv_name;
    String tv_uptime;
    String tv_status;

    public TVModels(String cluster_id, String tv_id, String tv_name, String tv_uptime, String tv_status) {
        this.cluster_id = cluster_id;
        this.tv_id = tv_id;
        this.tv_name = tv_name;
        this.tv_uptime = tv_uptime;
        this.tv_status = tv_status;
    }

    public String getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(String cluster_id) {
        this.cluster_id = cluster_id;
    }

    public String getTv_id() {
        return tv_id;
    }

    public void setTv_id(String tv_id) {
        this.tv_id = tv_id;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_uptime() {
        return tv_uptime;
    }

    public void setTv_uptime(String tv_uptime) {
        this.tv_uptime = tv_uptime;
    }

    public String getTv_status() {
        return tv_status;
    }

    public void setTv_status(String tv_status) {
        this.tv_status = tv_status;
    }
}
