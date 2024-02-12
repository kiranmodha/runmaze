package com.example.runmaze.stravazpot.club.request;

import com.example.runmaze.stravazpot.athlete.model.Athlete;
import com.example.runmaze.stravazpot.club.api.ClubAPI;
import com.example.runmaze.stravazpot.club.rest.ClubRest;

import java.util.List;

import retrofit2.Call;

public class ListClubMembersRequest {

    private final int clubID;
    private final ClubRest restService;
    private final ClubAPI api;
    private Integer page;
    private Integer perPage;

    public ListClubMembersRequest(int clubID, ClubRest restService, ClubAPI api) {
        this.clubID = clubID;
        this.restService = restService;
        this.api = api;
    }

    public ListClubMembersRequest inPage(int page) {
        this.page = page;
        return this;
    }

    public ListClubMembersRequest perPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public List<Athlete> execute() {
        Call<List<Athlete>> call = restService.getClubMembers(clubID, page, perPage);
        return api.execute(call);
    }
}
