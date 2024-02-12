package com.example.runmaze.stravazpot.activity.request;

import com.example.runmaze.stravazpot.activity.api.ActivityAPI;
import com.example.runmaze.stravazpot.activity.model.Activity;
import com.example.runmaze.stravazpot.activity.rest.ActivityRest;

import java.util.List;

import retrofit2.Call;

public class ListRelatedActivitiesRequest {

    private final int activityID;
    private final ActivityRest restService;
    private final ActivityAPI api;
    private Integer page;
    private Integer perPage;

    public ListRelatedActivitiesRequest(int activityID, ActivityRest restService, ActivityAPI api) {
        this.activityID = activityID;
        this.restService = restService;
        this.api = api;
    }

    public ListRelatedActivitiesRequest inPage(int page) {
        this.page = page;
        return this;
    }

    public ListRelatedActivitiesRequest perPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public List<Activity> execute() {
        Call<List<Activity>> call = restService.getRelatedActivities(activityID, page, perPage);
        return api.execute(call);
    }

}
