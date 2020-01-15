package com.tetraval.androadsadmin.data.models;

public class AdsModel {

    String tv_id;
    String ad_id;
    String ad_media_id;
    String ad_media_type;
    String ad_media_url;
    String ad_media_text;
    String ad_media_text_status;
    String ad_start_date;
    String ad_start_time;
    String ad_end_date;
    String ad_end_time;

    public AdsModel(String tv_id, String ad_id, String ad_media_id, String ad_media_type, String ad_media_url, String ad_media_text, String ad_media_text_status, String ad_start_date, String ad_start_time, String ad_end_date, String ad_end_time) {
        this.tv_id = tv_id;
        this.ad_id = ad_id;
        this.ad_media_id = ad_media_id;
        this.ad_media_type = ad_media_type;
        this.ad_media_url = ad_media_url;
        this.ad_media_text = ad_media_text;
        this.ad_media_text_status = ad_media_text_status;
        this.ad_start_date = ad_start_date;
        this.ad_start_time = ad_start_time;
        this.ad_end_date = ad_end_date;
        this.ad_end_time = ad_end_time;
    }

    public String getTv_id() {
        return tv_id;
    }

    public void setTv_id(String tv_id) {
        this.tv_id = tv_id;
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_media_id() {
        return ad_media_id;
    }

    public void setAd_media_id(String ad_media_id) {
        this.ad_media_id = ad_media_id;
    }

    public String getAd_media_type() {
        return ad_media_type;
    }

    public void setAd_media_type(String ad_media_type) {
        this.ad_media_type = ad_media_type;
    }

    public String getAd_media_url() {
        return ad_media_url;
    }

    public void setAd_media_url(String ad_media_url) {
        this.ad_media_url = ad_media_url;
    }

    public String getAd_media_text() {
        return ad_media_text;
    }

    public void setAd_media_text(String ad_media_text) {
        this.ad_media_text = ad_media_text;
    }

    public String getAd_media_text_status() {
        return ad_media_text_status;
    }

    public void setAd_media_text_status(String ad_media_text_status) {
        this.ad_media_text_status = ad_media_text_status;
    }

    public String getAd_start_date() {
        return ad_start_date;
    }

    public void setAd_start_date(String ad_start_date) {
        this.ad_start_date = ad_start_date;
    }

    public String getAd_start_time() {
        return ad_start_time;
    }

    public void setAd_start_time(String ad_start_time) {
        this.ad_start_time = ad_start_time;
    }

    public String getAd_end_date() {
        return ad_end_date;
    }

    public void setAd_end_date(String ad_end_date) {
        this.ad_end_date = ad_end_date;
    }

    public String getAd_end_time() {
        return ad_end_time;
    }

    public void setAd_end_time(String ad_end_time) {
        this.ad_end_time = ad_end_time;
    }
}
