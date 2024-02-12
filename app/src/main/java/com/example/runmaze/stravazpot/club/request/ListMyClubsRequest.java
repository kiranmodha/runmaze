package com.example.runmaze.stravazpot.club.request;

import com.example.runmaze.stravazpot.club.api.ClubAPI;
import com.example.runmaze.stravazpot.club.model.Club;
import com.example.runmaze.stravazpot.club.rest.ClubRest;

import java.util.List;

import retrofit2.Call;

public class ListMyClubsRequest {

    private final ClubRest restService;
    private final ClubAPI api;

    public ListMyClubsRequest(ClubRest restService, ClubAPI api) {
        this.restService = restService;
        this.api = api;
    }

    public List<Club> execute() {
        Call<List<Club>> call = restService.getMyClubs();
        return api.execute(call);
    }
}
