package com.example.runmaze.stravazpot.segment.request;

import com.example.runmaze.stravazpot.segment.api.SegmentEffortAPI;
import com.example.runmaze.stravazpot.segment.model.SegmentEffort;
import com.example.runmaze.stravazpot.segment.rest.SegmentEffortRest;

import retrofit2.Call;

public class GetSegmentEffortRequest {

    private final long segmentEffortID;
    private final SegmentEffortRest restService;
    private final SegmentEffortAPI api;

    public GetSegmentEffortRequest(long segmentEffortID, SegmentEffortRest restService, SegmentEffortAPI api) {
        this.segmentEffortID = segmentEffortID;
        this.restService = restService;
        this.api = api;
    }

    public SegmentEffort execute() {
        Call<SegmentEffort> call = restService.getSegmentEffort(segmentEffortID);
        return api.execute(call);
    }
}
