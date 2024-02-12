package com.example.runmaze.stravazpot.activity.api;

import com.example.runmaze.stravazpot.activity.request.ListActivityKudoersRequest;
import com.example.runmaze.stravazpot.activity.rest.KudosRest;
import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;

public class KudosAPI extends StravaAPI {

    public KudosAPI(StravaConfig config) {
        super(config);
    }

    public ListActivityKudoersRequest listActivityKudoers(int activityID) {
        return new ListActivityKudoersRequest(activityID, getAPI(KudosRest.class), this);
    }
}
