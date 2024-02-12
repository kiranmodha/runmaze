package com.example.runmaze.stravazpot.athlete.request;

import com.example.runmaze.stravazpot.athlete.api.AthleteAPI;
import com.example.runmaze.stravazpot.athlete.model.Athlete;
import com.example.runmaze.stravazpot.athlete.rest.AthleteRest;

import retrofit2.Call;

public class AthleteRequest {
    private final int athleteID;
    private final AthleteRest restService;
    private final AthleteAPI api;

    public AthleteRequest(int athleteID, AthleteRest restService, AthleteAPI api) {
        this.athleteID = athleteID;
        this.restService = restService;
        this.api = api;
    }

    public Athlete execute() {
        Call<Athlete> call = restService.getAthlete(athleteID);
        return api.execute(call);
    }
}
