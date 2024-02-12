package com.example.runmaze.stravazpot.route.request;

import com.example.runmaze.stravazpot.route.api.RouteAPI;
import com.example.runmaze.stravazpot.route.model.Route;
import com.example.runmaze.stravazpot.route.rest.RouteRest;

import retrofit2.Call;

public class GetRouteRequest {

    private final int routeID;
    private final RouteRest restService;
    private final RouteAPI api;

    public GetRouteRequest(int routeID, RouteRest restService, RouteAPI api) {
        this.routeID = routeID;
        this.restService = restService;
        this.api = api;
    }

    public Route execute() {
        Call<Route> call = restService.getRoute(routeID);
        return api.execute(call);
    }
}
