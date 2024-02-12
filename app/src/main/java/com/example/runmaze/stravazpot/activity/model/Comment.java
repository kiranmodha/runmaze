package com.example.runmaze.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;
import com.example.runmaze.stravazpot.common.model.ResourceState;
import com.example.runmaze.stravazpot.athlete.model.Athlete;

import java.util.Date;

public class Comment {
    @SerializedName("id") private int ID;
    @SerializedName("resource_state") private ResourceState resourceState;
    @SerializedName("activity_id") private int activityID;
    @SerializedName("text") private String text;
    @SerializedName("athlete") private Athlete athlete;
    @SerializedName("created_at") private Date createdAt;

    public int getID() {
        return ID;
    }

    public ResourceState getResourceState() {
        return resourceState;
    }

    public int getActivityID() {
        return activityID;
    }

    public String getText() {
        return text;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
