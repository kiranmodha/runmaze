package com.example.runmaze.stravazpot.athlete.api;

import com.example.runmaze.stravazpot.athlete.request.AthleteRequest;
import com.example.runmaze.stravazpot.athlete.request.CurrentAthleteRequest;
import com.example.runmaze.stravazpot.athlete.request.GetTotalsAndStatsRequest;
import com.example.runmaze.stravazpot.athlete.request.GetZonesRequest;
import com.example.runmaze.stravazpot.athlete.request.ListAthleteKOMSRequest;
import com.example.runmaze.stravazpot.athlete.request.UpdateAthleteRequest;
import com.example.runmaze.stravazpot.athlete.rest.AthleteRest;
import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;

public class AthleteAPI extends StravaAPI {

    public AthleteAPI(StravaConfig config) {
        super(config);
    }

    public CurrentAthleteRequest retrieveCurrentAthlete() {
        return new CurrentAthleteRequest(getAPI(AthleteRest.class), this);
    }

    public AthleteRequest retrieveAthlete(int athleteID) {
        return new AthleteRequest(athleteID, getAPI(AthleteRest.class), this);
    }

    public UpdateAthleteRequest updateAthlete() {
        return new UpdateAthleteRequest(getAPI(AthleteRest.class), this);
    }

    public GetZonesRequest getAthleteZones() {
        return new GetZonesRequest(getAPI(AthleteRest.class), this);
    }

    public GetTotalsAndStatsRequest getAthleteTotalsAndStats(int athleteID) {
        return new GetTotalsAndStatsRequest(athleteID, getAPI(AthleteRest.class), this);
    }

    public ListAthleteKOMSRequest listAthleteKOMS(int athleteID) {
        return new ListAthleteKOMSRequest(athleteID, getAPI(AthleteRest.class), this);
    }
}
