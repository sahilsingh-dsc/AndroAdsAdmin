package com.tetraval.androadsadmin.data.models;

public class MediaModel {
    String media_id;
    String media_type;
    String media_url;

    public MediaModel(String media_id, String media_type, String media_url) {
        this.media_id = media_id;
        this.media_type = media_type;
        this.media_url = media_url;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }
}
