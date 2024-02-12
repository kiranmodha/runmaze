package com.example.runmaze.stravazpot.activity.request;

import com.example.runmaze.stravazpot.activity.api.ActivityAPI;
import com.example.runmaze.stravazpot.activity.model.Activity;
import com.example.runmaze.stravazpot.activity.rest.ActivityRest;
import com.example.runmaze.stravazpot.common.model.Time;

import java.util.List;

import retrofit2.Call;

public class ListFriendActivitiesRequest {

    private final ActivityRest restService;
    private final ActivityAPI api;
    private Time before;
    private Integer page;
    private Integer perPage;

    public ListFriendActivitiesRequest(ActivityRest restService, ActivityAPI api) {
        this.restService = restService;
        this.api = api;
    }

    public ListFriendActivitiesRequest before(Time before) {
        this.before = before;
        return this;
    }

    public ListFriendActivitiesRequest inPage(int page) {
        this.page = page;
        return this;
    }

    public ListFriendActivitiesRequest perPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public List<Activity> execute() {
        Call<List<Activity>> call = restService.getFriendsActivities(before, page, perPage);
        return api.execute(call);
    }

}
