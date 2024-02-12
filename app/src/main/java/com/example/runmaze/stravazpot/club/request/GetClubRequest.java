package com.example.runmaze.stravazpot.club.request;

import com.example.runmaze.stravazpot.club.api.ClubAPI;
import com.example.runmaze.stravazpot.club.model.Club;
import com.example.runmaze.stravazpot.club.rest.ClubRest;

import retrofit2.Call;

public class GetClubRequest {

    private final int clubID;
    private final ClubRest restService;
    private final ClubAPI api;

    public GetClubRequest(int clubID, ClubRest restService, ClubAPI api) {
        this.clubID = clubID;
        this.restService = restService;
        this.api = api;
    }

    public Club execute() {
        Call<Club> call = restService.getClub(clubID);
        return api.execute(call);
    }
}
