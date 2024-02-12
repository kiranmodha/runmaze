package com.example.runmaze.stravazpot.activity.request;

import com.example.runmaze.stravazpot.activity.api.KudosAPI;
import com.example.runmaze.stravazpot.activity.rest.KudosRest;
import com.example.runmaze.stravazpot.athlete.model.Athlete;

import java.util.List;

import retrofit2.Call;

public class ListActivityKudoersRequest {

    private final int activityID;
    private final KudosRest restService;
    private final KudosAPI api;
    private Integer page;
    private Integer perPage;

    public ListActivityKudoersRequest(int activityID, KudosRest restService, KudosAPI api) {
        this.activityID = activityID;
        this.restService = restService;
        this.api = api;
    }

    public ListActivityKudoersRequest inPage(int page) {
        this.page = page;
        return this;
    }

    public ListActivityKudoersRequest perPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public List<Athlete> execute() {
        Call<List<Athlete>> call = restService.getActivityKudos(activityID, page, perPage);
        return api.execute(call);
    }

}
