package com.example.runmaze.stravazpot.authenticaton.request;

import com.example.runmaze.stravazpot.authenticaton.api.AuthenticationAPI;
import com.example.runmaze.stravazpot.authenticaton.rest.AuthenticationRest;

import retrofit2.Call;

public class DeauthorizationRequest {

    private final AuthenticationRest restService;
    private final AuthenticationAPI api;

    public DeauthorizationRequest(AuthenticationRest restService, AuthenticationAPI api) {
        this.restService = restService;
        this.api = api;
    }

    public Void execute() {
        Call<Void> call = restService.deauthorize();
        return api.execute(call);
    }
}
