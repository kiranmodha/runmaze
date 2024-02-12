package com.example.runmaze.stravazpot.activity.api;

import com.example.runmaze.stravazpot.activity.request.ListActivityPhotosRequest;
import com.example.runmaze.stravazpot.activity.rest.PhotosRest;
import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;

public class PhotoAPI extends StravaAPI{

    public PhotoAPI(StravaConfig config) {
        super(config);
    }

    public ListActivityPhotosRequest listAcivityPhotos(int activityID) {
        return new ListActivityPhotosRequest(activityID, getAPI(PhotosRest.class), this);
    }
}
