package com.example.runmaze.stravazpot.authenticaton.request;

import com.example.runmaze.stravazpot.authenticaton.api.AuthenticationAPI;
import com.example.runmaze.stravazpot.authenticaton.model.AppCredentials;
import com.example.runmaze.stravazpot.authenticaton.model.LoginResult;
import com.example.runmaze.stravazpot.authenticaton.rest.AuthenticationRest;

import retrofit2.Call;

public class AuthenticationRequest {

    private final AppCredentials appCredentials;
    private final AuthenticationRest restService;
    private final AuthenticationAPI api;
    private String code;

    public AuthenticationRequest(AppCredentials appCredentials, AuthenticationRest restService, AuthenticationAPI api) {
        this.appCredentials = appCredentials;
        this.restService = restService;
        this.api = api;
    }

    public AuthenticationRequest withCode(String code) {
        this.code = code;
        return this;
    }

    public LoginResult execute() {
        Call<LoginResult> call = restService.token(appCredentials.getClientID(), appCredentials.getClientSecret(), code);
        return api.execute(call);
    }
}
