package com.example.runmaze.stravazpot.club.request;

import com.example.runmaze.stravazpot.club.api.ClubAPI;
import com.example.runmaze.stravazpot.club.model.Announcement;
import com.example.runmaze.stravazpot.club.rest.ClubRest;

import java.util.List;

import retrofit2.Call;

public class ListClubAnnouncementsRequest {

    private final int clubID;
    private final ClubRest restService;
    private final ClubAPI api;

    public ListClubAnnouncementsRequest(int clubID, ClubRest restService, ClubAPI api) {
        this.clubID = clubID;
        this.restService = restService;
        this.api = api;
    }

    public List<Announcement> execute() {
        Call<List<Announcement>> call = restService.getClubAnnouncements(clubID);
        return api.execute(call);
    }
}
