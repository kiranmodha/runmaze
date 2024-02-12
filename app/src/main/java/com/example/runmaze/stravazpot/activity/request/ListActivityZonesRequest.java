package com.example.runmaze.stravazpot.activity.request;

import com.example.runmaze.stravazpot.activity.api.ActivityAPI;
import com.example.runmaze.stravazpot.activity.model.ActivityZone;
import com.example.runmaze.stravazpot.activity.rest.ActivityRest;

import java.util.List;

import retrofit2.Call;

public class ListActivityZonesRequest {

    private final int activityID;
    private final ActivityRest restService;
    private final ActivityAPI api;

    public ListActivityZonesRequest(int activityID, ActivityRest restService, ActivityAPI api) {
        this.activityID = activityID;
        this.restService = restService;
        this.api = api;
    }

    public List<ActivityZone> execute() {
        Call<List<ActivityZone>> call = restService.getActivityZones(activityID);
        return api.execute(call);
    }
}
