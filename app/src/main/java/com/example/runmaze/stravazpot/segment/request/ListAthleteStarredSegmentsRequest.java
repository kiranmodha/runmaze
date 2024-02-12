package com.example.runmaze.stravazpot.segment.request;

import com.example.runmaze.stravazpot.segment.api.SegmentAPI;
import com.example.runmaze.stravazpot.segment.model.Segment;
import com.example.runmaze.stravazpot.segment.rest.SegmentRest;

import java.util.List;

import retrofit2.Call;

public class ListAthleteStarredSegmentsRequest {

    private final int athleteID;
    private final SegmentRest restService;
    private final SegmentAPI api;
    private Integer page;
    private Integer perPage;

    public ListAthleteStarredSegmentsRequest(int athleteID, SegmentRest restService, SegmentAPI api) {
        this.athleteID = athleteID;
        this.restService = restService;
        this.api = api;
    }

    public ListAthleteStarredSegmentsRequest inPage(int page) {
        this.page = page;
        return this;
    }

    public ListAthleteStarredSegmentsRequest perPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public List<Segment> execute() {
        Call<List<Segment>> call = restService.getAthleteStarredSegments(athleteID, page, perPage);
        return api.execute(call);
    }
}
