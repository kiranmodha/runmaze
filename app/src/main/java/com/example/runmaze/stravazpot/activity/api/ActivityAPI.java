package com.example.runmaze.stravazpot.activity.api;

import com.example.runmaze.stravazpot.activity.request.CreateActivityRequest;
import com.example.runmaze.stravazpot.activity.request.DeleteActivityRequest;
import com.example.runmaze.stravazpot.activity.request.GetActivityRequest;
import com.example.runmaze.stravazpot.activity.request.ListActivityLapsRequest;
import com.example.runmaze.stravazpot.activity.request.ListActivityZonesRequest;
import com.example.runmaze.stravazpot.activity.request.ListFriendActivitiesRequest;
import com.example.runmaze.stravazpot.activity.request.ListMyActivitiesRequest;
import com.example.runmaze.stravazpot.activity.request.ListRelatedActivitiesRequest;
import com.example.runmaze.stravazpot.activity.request.UpdateActivityRequest;
import com.example.runmaze.stravazpot.activity.rest.ActivityRest;
import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;

public class ActivityAPI extends StravaAPI {

    public ActivityAPI(StravaConfig config) {
        super(config);
    }

    public CreateActivityRequest createActivity(String name) {
        return new CreateActivityRequest(name, getAPI(ActivityRest.class), this);
    }

    public GetActivityRequest getActivity(int activityID) {
        return new GetActivityRequest(activityID, getAPI(ActivityRest.class), this);
    }

    public UpdateActivityRequest updateActivity(int activityID) {
        return new UpdateActivityRequest(activityID, getAPI(ActivityRest.class), this);
    }

    public DeleteActivityRequest deleteActivity(int activityID) {
        return new DeleteActivityRequest(activityID, getAPI(ActivityRest.class), this);
    }

    public ListMyActivitiesRequest listMyActivities() {
        return new ListMyActivitiesRequest(getAPI(ActivityRest.class), this);
    }

    public ListFriendActivitiesRequest listFriendActivities() {
        return new ListFriendActivitiesRequest(getAPI(ActivityRest.class), this);
    }

    public ListRelatedActivitiesRequest listRelatedActivities(int activityID) {
        return new ListRelatedActivitiesRequest(activityID, getAPI(ActivityRest.class), this);
    }

    public ListActivityZonesRequest listActivityZones(int activityID) {
        return new ListActivityZonesRequest(activityID, getAPI(ActivityRest.class), this);
    }

    public ListActivityLapsRequest listActivityLaps(int activityID) {
        return new ListActivityLapsRequest(activityID, getAPI(ActivityRest.class), this);
    }
}
