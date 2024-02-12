package com.example.runmaze.stravazpot.club.request;

import com.example.runmaze.stravazpot.club.api.ClubAPI;
import com.example.runmaze.stravazpot.club.model.LeaveResult;
import com.example.runmaze.stravazpot.club.rest.ClubRest;

import retrofit2.Call;

public class LeaveClubRequest {

    private final int clubID;
    private final ClubRest restService;
    private final ClubAPI api;

    public LeaveClubRequest(int clubID, ClubRest restService, ClubAPI api) {
        this.clubID = clubID;
        this.restService = restService;
        this.api = api;
    }

    public LeaveResult execute() {
        Call<LeaveResult> call = restService.leaveClub(clubID);
        return api.execute(call);
    }
}
