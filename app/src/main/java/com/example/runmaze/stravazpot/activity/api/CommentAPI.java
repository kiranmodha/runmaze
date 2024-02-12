package com.example.runmaze.stravazpot.activity.api;

import com.example.runmaze.stravazpot.activity.request.ListActivityCommentsRequest;
import com.example.runmaze.stravazpot.activity.rest.CommentsRest;
import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;

public class CommentAPI extends StravaAPI {

    public CommentAPI(StravaConfig config) {
        super(config);
    }

    public ListActivityCommentsRequest listActivityComments(int activityID) {
        return new ListActivityCommentsRequest(activityID, getAPI(CommentsRest.class), this);
    }
}
