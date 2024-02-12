package com.example.runmaze.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;
import com.example.runmaze.stravazpot.common.model.Coordinates;
import com.example.runmaze.stravazpot.common.model.ResourceState;

import java.util.Date;
import java.util.HashMap;

public class Photo {
    @SerializedName("unique_id") private String uniqueID;
    @SerializedName("id") private int ID;
    @SerializedName("activity_id") private int activityID;
    @SerializedName("resource_state") private ResourceState resourceState;
    @SerializedName("urls") private HashMap<String, String> urls;
    @SerializedName("caption") private String caption;
    @SerializedName("source") private PhotoSource source;
    @SerializedName("uploaded_at") private Date uploadedAt;
    @SerializedName("created_at") private Date createdAt;
    @SerializedName("location") private Coordinates location;
    @SerializedName("ref") private String ref;
    @SerializedName("uid") private String uid;
    @SerializedName("type") private String type;

    public String getUniqueID() {
        return uniqueID;
    }

    public int getID() {
        return ID;
    }

    public int getActivityID() {
        return activityID;
    }

    public ResourceState getResourceState() {
        return resourceState;
    }

    public HashMap<String, String> getUrls() {
        return urls;
    }

    public String getCaption() {
        return caption;
    }

    public PhotoSource getSource() {
        return source;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Coordinates getLocation() {
        return location;
    }

    public String getRef() {
        return ref;
    }

    public String getUid() {
        return uid;
    }

    public String getType() {
        return type;
    }
}
