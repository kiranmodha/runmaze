package com.example.runmaze.stravazpot.stream.request;

import com.example.runmaze.stravazpot.stream.api.StreamAPI;
import com.example.runmaze.stravazpot.stream.model.Stream;
import com.example.runmaze.stravazpot.stream.rest.StreamRest;

import java.util.List;

import retrofit2.Call;

public class GetRouteStreamsRequest {

    private final int routeID;
    private final StreamRest restService;
    private final StreamAPI api;

    public GetRouteStreamsRequest(int routeID, StreamRest restService, StreamAPI api) {
        this.routeID = routeID;
        this.restService = restService;
        this.api = api;
    }

    public List<Stream> execute() {
        Call<List<Stream>> call = restService.getRouteStreams(routeID);
        return api.execute(call);
    }
}
