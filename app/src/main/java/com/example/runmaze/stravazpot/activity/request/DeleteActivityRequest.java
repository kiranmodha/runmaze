package com.example.runmaze.stravazpot.activity.request;

import com.example.runmaze.stravazpot.activity.api.ActivityAPI;
import com.example.runmaze.stravazpot.activity.rest.ActivityRest;

import retrofit2.Call;

public class DeleteActivityRequest {

    private final int activityID;
    private final ActivityRest restService;
    private final ActivityAPI api;

    public DeleteActivityRequest(int activityID, ActivityRest restService, ActivityAPI api) {
        this.activityID = activityID;
        this.restService = restService;
        this.api = api;
    }

    public void execute() {
        Call<Void> call = restService.deleteActivity(activityID);
        api.execute(call);
    }
}
