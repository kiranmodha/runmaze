package com.example.runmaze.stravazpot.upload.model;

import com.google.gson.annotations.SerializedName;

public class UploadStatus {

    @SerializedName("id") private int id;
    @SerializedName("external_id") private String externalID;
    @SerializedName("error") private String error;
    @SerializedName("status") private String status;
    @SerializedName("activity_id") private Integer activityID;

    public int getId() {
        return id;
    }

    public String getExternalID() {
        return externalID;
    }

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public Integer getActivityID() {
        return activityID;
    }
}
