package com.example.runmaze.stravazpot.activity.request;

import com.example.runmaze.stravazpot.activity.api.ActivityAPI;
import com.example.runmaze.stravazpot.activity.model.Activity;
import com.example.runmaze.stravazpot.activity.rest.ActivityRest;

import retrofit2.Call;

public class GetActivityRequest {

    private final int activityID;
    private final ActivityRest restService;
    private final ActivityAPI api;
    private Boolean includeAllEfforts;

    public GetActivityRequest(int activityID, ActivityRest restService, ActivityAPI api) {
        this.activityID = activityID;
        this.restService = restService;
        this.api = api;
    }

    public GetActivityRequest includeAllEfforts(boolean includeAllEfforts) {
        this.includeAllEfforts = includeAllEfforts;
        return this;
    }

    public Activity execute() {
        Call<Activity> call = restService.getActivity(activityID, includeAllEfforts);
        return api.execute(call);
    }
}
