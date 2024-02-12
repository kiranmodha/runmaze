package com.example.runmaze.stravazpot.gear.api;

import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;
import com.example.runmaze.stravazpot.gear.request.GetGearRequest;
import com.example.runmaze.stravazpot.gear.rest.GearRest;

public class GearAPI extends StravaAPI{

    public GearAPI(StravaConfig config) {
        super(config);
    }

    public GetGearRequest getGear(String gearID) {
        return new GetGearRequest(gearID, getAPI(GearRest.class), this);
    }
}
