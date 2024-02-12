package com.example.runmaze.stravazpot.athlete.request;

import com.example.runmaze.stravazpot.athlete.api.AthleteAPI;
import com.example.runmaze.stravazpot.athlete.model.Zones;
import com.example.runmaze.stravazpot.athlete.rest.AthleteRest;

import retrofit2.Call;

public class GetZonesRequest {

    private final AthleteRest restService;
    private final AthleteAPI api;

    public GetZonesRequest(AthleteRest restService, AthleteAPI api) {
        this.restService = restService;
        this.api = api;
    }

    public Zones execute() {
        Call<Zones> call = restService.getAthleteZones();
        return api.execute(call);
    }
}
