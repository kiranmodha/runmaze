package com.example.runmaze.stravazpot.route.api;

import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;
import com.example.runmaze.stravazpot.route.request.GetRouteRequest;
import com.example.runmaze.stravazpot.route.request.ListRoutesRequest;
import com.example.runmaze.stravazpot.route.rest.RouteRest;

public class RouteAPI extends StravaAPI{

    public RouteAPI(StravaConfig config) {
        super(config);
    }

    public GetRouteRequest getRoute(int routeID) {
        return new GetRouteRequest(routeID, getAPI(RouteRest.class), this);
    }

    public ListRoutesRequest listRoutes(int athleteID) {
        return new ListRoutesRequest(athleteID, getAPI(RouteRest.class), this);
    }
}
