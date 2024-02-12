package com.example.runmaze.stravazpot.club.request;

import com.example.runmaze.stravazpot.club.api.ClubAPI;
import com.example.runmaze.stravazpot.club.model.JoinResult;
import com.example.runmaze.stravazpot.club.rest.ClubRest;

import retrofit2.Call;

public class JoinClubRequest {

    private final int clubID;
    private final ClubRest restService;
    private final ClubAPI api;

    public JoinClubRequest(int clubID, ClubRest restService, ClubAPI api) {
        this.clubID = clubID;
        this.restService = restService;
        this.api = api;
    }

    public JoinResult execute() {
        Call<JoinResult> call = restService.joinClub(clubID);
        return api.execute(call);
    }
}
