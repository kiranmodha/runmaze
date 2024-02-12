package com.example.runmaze.stravazpot.activity.request;

import com.example.runmaze.stravazpot.activity.api.ActivityAPI;
import com.example.runmaze.stravazpot.activity.model.ActivityLap;
import com.example.runmaze.stravazpot.activity.rest.ActivityRest;

import java.util.List;

import retrofit2.Call;

public class ListActivityLapsRequest {

    private final int activityID;
    private final ActivityRest restService;
    private final ActivityAPI api;

    public ListActivityLapsRequest(int activityID, ActivityRest restService, ActivityAPI api) {
        this.activityID = activityID;
        this.restService = restService;
        this.api = api;
    }

    public List<ActivityLap> execute() {
        Call<List<ActivityLap>> call = restService.getActivityLaps(activityID);
        return api.execute(call);
    }
}
