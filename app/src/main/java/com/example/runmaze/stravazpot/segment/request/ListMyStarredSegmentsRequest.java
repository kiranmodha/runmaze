package com.example.runmaze.stravazpot.segment.request;

import com.example.runmaze.stravazpot.segment.api.SegmentAPI;
import com.example.runmaze.stravazpot.segment.model.Segment;
import com.example.runmaze.stravazpot.segment.rest.SegmentRest;

import java.util.List;

import retrofit2.Call;

public class ListMyStarredSegmentsRequest {

    private final SegmentRest restService;
    private final SegmentAPI api;
    private Integer page;
    private Integer perPage;

    public ListMyStarredSegmentsRequest(SegmentRest restService, SegmentAPI api) {
        this.restService = restService;
        this.api = api;
    }

    public ListMyStarredSegmentsRequest inPage(int page) {
        this.page = page;
        return this;
    }

    public ListMyStarredSegmentsRequest perPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public List<Segment> execute() {
        Call<List<Segment>> call = restService.getMyStarredSegments(page, perPage);
        return api.execute(call);
    }

}
