package com.example.runmaze.stravazpot.athlete.request;

import com.example.runmaze.stravazpot.athlete.api.AthleteAPI;
import com.example.runmaze.stravazpot.athlete.model.Stats;
import com.example.runmaze.stravazpot.athlete.rest.AthleteRest;

import retrofit2.Call;

public class GetTotalsAndStatsRequest {

    private final int athleteID;
    private final AthleteRest restService;
    private final AthleteAPI api;

    public GetTotalsAndStatsRequest(int athleteID, AthleteRest restService, AthleteAPI api) {
        this.athleteID = athleteID;
        this.restService = restService;
        this.api = api;
    }

    public Stats execute() {
        Call<Stats> call = restService.getAthleteStats(athleteID);
        return api.execute(call);
    }
}
