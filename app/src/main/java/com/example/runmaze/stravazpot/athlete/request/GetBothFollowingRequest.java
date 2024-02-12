package com.example.runmaze.stravazpot.athlete.request;

import com.example.runmaze.stravazpot.athlete.api.FriendAPI;
import com.example.runmaze.stravazpot.athlete.model.Athlete;
import com.example.runmaze.stravazpot.athlete.rest.FriendRest;

import java.util.List;

import retrofit2.Call;

public class GetBothFollowingRequest {

    private final int athleteID;
    private final FriendRest restService;
    private final FriendAPI api;
    private Integer page;
    private Integer perPage;

    public GetBothFollowingRequest(int athleteID, FriendRest restService, FriendAPI api) {
        this.athleteID = athleteID;
        this.restService = restService;
        this.api = api;
    }

    public GetBothFollowingRequest inPage(int page) {
        this.page = page;
        return this;
    }

    public GetBothFollowingRequest perPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public List<Athlete> execute() {
        Call<List<Athlete>> call = restService.getBothFollowing(athleteID, page, perPage);
        return api.execute(call);
    }

}
