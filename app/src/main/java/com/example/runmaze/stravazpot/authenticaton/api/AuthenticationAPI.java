package com.example.runmaze.stravazpot.authenticaton.api;

import com.example.runmaze.stravazpot.authenticaton.model.AppCredentials;
import com.example.runmaze.stravazpot.authenticaton.request.AuthenticationRequest;
import com.example.runmaze.stravazpot.authenticaton.request.DeauthorizationRequest;
import com.example.runmaze.stravazpot.authenticaton.rest.AuthenticationRest;
import com.example.runmaze.stravazpot.common.api.Config;
import com.example.runmaze.stravazpot.common.api.StravaAPI;

public class AuthenticationAPI extends StravaAPI{

    public AuthenticationAPI(Config config) {
        super(config);
    }

    public AuthenticationRequest getTokenForApp(AppCredentials appCredentials) {
        return new AuthenticationRequest(appCredentials, getAPI(AuthenticationRest.class), this);
    }

    public DeauthorizationRequest deauthorize() {
        return new DeauthorizationRequest(getAPI(AuthenticationRest.class), this);
    }

}
