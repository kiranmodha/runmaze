package com.example.runmaze.stravazpot.segment.request;

import com.example.runmaze.stravazpot.segment.api.SegmentAPI;
import com.example.runmaze.stravazpot.segment.model.Segment;
import com.example.runmaze.stravazpot.segment.rest.SegmentRest;

import retrofit2.Call;

public class GetSegmentRequest {

    private final int segmentID;
    private final SegmentRest restService;
    private final SegmentAPI api;

    public GetSegmentRequest(int segmentID, SegmentRest restService, SegmentAPI api) {
        this.segmentID = segmentID;
        this.restService = restService;
        this.api = api;
    }

    public Segment execute() {
        Call<Segment> call = restService.getSegment(segmentID);
        return api.execute(call);
    }
}
