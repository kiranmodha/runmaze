package com.example.runmaze.stravazpot.activity.request;

import com.example.runmaze.stravazpot.activity.api.PhotoAPI;
import com.example.runmaze.stravazpot.activity.model.Photo;
import com.example.runmaze.stravazpot.activity.rest.PhotosRest;

import java.util.List;

import retrofit2.Call;

public class ListActivityPhotosRequest {

    private final int activityID;
    private final PhotosRest restService;
    private final PhotoAPI api;

    public ListActivityPhotosRequest(int activityID, PhotosRest restService, PhotoAPI api) {
        this.activityID = activityID;
        this.restService = restService;
        this.api = api;
    }

    public List<Photo> execute() {
        Call<List<Photo>> call = restService.getActivityPhotos(activityID, true);
        return api.execute(call);
    }
}
