package com.example.runmaze.stravazpot.segment.api;

import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;
import com.example.runmaze.stravazpot.segment.request.GetSegmentEffortRequest;
import com.example.runmaze.stravazpot.segment.rest.SegmentEffortRest;

public class SegmentEffortAPI extends StravaAPI{

    public SegmentEffortAPI(StravaConfig config) {
        super(config);
    }

    public GetSegmentEffortRequest getSegmentEffort(long segmentEffortID) {
        return new GetSegmentEffortRequest(segmentEffortID, getAPI(SegmentEffortRest.class), this);
    }
}
