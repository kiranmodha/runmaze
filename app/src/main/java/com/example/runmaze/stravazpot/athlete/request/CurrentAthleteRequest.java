package com.example.runmaze.stravazpot.athlete.request;

import com.example.runmaze.stravazpot.athlete.api.AthleteAPI;
import com.example.runmaze.stravazpot.athlete.model.Athlete;
import com.example.runmaze.stravazpot.athlete.rest.AthleteRest;

import retrofit2.Call;

public class CurrentAthleteRequest {

    private final AthleteRest restService;
    private final AthleteAPI api;

    public CurrentAthleteRequest(AthleteRest restService, AthleteAPI api) {
        this.restService = restService;
        this.api = api;
    }

    public Athlete execute() {
        Call<Athlete> call = restService.getCurrentAthlete();
        return api.execute(call);
    }
}
