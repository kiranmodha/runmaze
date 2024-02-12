package com.example.runmaze.stravazpot.activity.request;

import com.example.runmaze.stravazpot.activity.api.ActivityAPI;
import com.example.runmaze.stravazpot.activity.model.Activity;
import com.example.runmaze.stravazpot.activity.model.ActivityType;
import com.example.runmaze.stravazpot.activity.rest.ActivityRest;

import retrofit2.Call;

public class UpdateActivityRequest {

    private final int activityID;
    private final ActivityRest restService;
    private final ActivityAPI api;
    private String name;
    private ActivityType type;
    private Boolean isPrivate;
    private Boolean commute;
    private Boolean trainer;
    private String gearID;
    private String description;

    public UpdateActivityRequest(int activityID, ActivityRest restService, ActivityAPI api) {
        this.activityID = activityID;
        this.restService = restService;
        this.api = api;
    }

    public UpdateActivityRequest changeName(String name) {
        this.name = name;
        return this;
    }

    public UpdateActivityRequest changeType(ActivityType type) {
        this.type = type;
        return this;
    }

    public UpdateActivityRequest changePrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
        return this;
    }

    public UpdateActivityRequest changeCommute(Boolean commute) {
        this.commute = commute;
        return this;
    }

    public UpdateActivityRequest changeTrainer(Boolean trainer) {
        this.trainer = trainer;
        return this;
    }

    public UpdateActivityRequest changeGearID(String gearID) {
        this.gearID = gearID;
        return this;
    }

    public UpdateActivityRequest changeDescription(String description) {
        this.description = description;
        return this;
    }

    public Activity execute() {
        Call<Activity> call = restService.updateActivity(activityID, name, type, isPrivate,
                commute, trainer, gearID, description);
        return api.execute(call);
    }
}
