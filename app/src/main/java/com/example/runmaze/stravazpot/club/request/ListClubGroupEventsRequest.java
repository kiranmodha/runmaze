package com.example.runmaze.stravazpot.club.request;

import com.example.runmaze.stravazpot.club.api.ClubAPI;
import com.example.runmaze.stravazpot.club.model.Event;
import com.example.runmaze.stravazpot.club.rest.ClubRest;

import java.util.List;

import retrofit2.Call;

public class ListClubGroupEventsRequest {

    private final int clubID;
    private final ClubRest restService;
    private final ClubAPI api;

    public ListClubGroupEventsRequest(int clubID, ClubRest restService, ClubAPI api) {
        this.clubID = clubID;
        this.restService = restService;
        this.api = api;
    }

    public List<Event> execute() {
        Call<List<Event>> call = restService.getClubGroupEvents(clubID);
        return api.execute(call);
    }
}
