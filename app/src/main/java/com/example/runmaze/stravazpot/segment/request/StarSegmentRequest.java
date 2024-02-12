package com.example.runmaze.stravazpot.segment.request;

import com.example.runmaze.stravazpot.segment.api.SegmentAPI;
import com.example.runmaze.stravazpot.segment.model.Segment;
import com.example.runmaze.stravazpot.segment.rest.SegmentRest;

import retrofit2.Call;

public class StarSegmentRequest {

    private final int segmentID;
    private final boolean star;
    private final SegmentRest restService;
    private final SegmentAPI api;

    public StarSegmentRequest(int segmentID, boolean star, SegmentRest restService, SegmentAPI api) {
        this.segmentID = segmentID;
        this.star = star;
        this.restService = restService;
        this.api = api;
    }

    public Segment execute() {
        Call<Segment> call = restService.starSegment(segmentID, star);
        return api.execute(call);
    }
}
