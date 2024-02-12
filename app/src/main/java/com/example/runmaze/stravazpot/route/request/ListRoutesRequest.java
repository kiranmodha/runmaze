package com.example.runmaze.stravazpot.route.request;

import com.example.runmaze.stravazpot.route.api.RouteAPI;
import com.example.runmaze.stravazpot.route.model.Route;
import com.example.runmaze.stravazpot.route.rest.RouteRest;

import java.util.List;

import retrofit2.Call;

public class ListRoutesRequest {

    private final int athleteID;
    private final RouteRest restService;
    private final RouteAPI api;

    public ListRoutesRequest(int athleteID, RouteRest restService, RouteAPI api) {
        this.athleteID = athleteID;
        this.restService = restService;
        this.api = api;
    }

    public List<Route> execute() {
        Call<List<Route>> call = restService.getAthleteRoutes(athleteID);
        return api.execute(call);
    }
}
