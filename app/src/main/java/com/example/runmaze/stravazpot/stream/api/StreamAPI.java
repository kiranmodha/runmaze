package com.example.runmaze.stravazpot.stream.api;

import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;
import com.example.runmaze.stravazpot.stream.request.GetActivityStreamsRequest;
import com.example.runmaze.stravazpot.stream.request.GetRouteStreamsRequest;
import com.example.runmaze.stravazpot.stream.request.GetSegmentEffortStreamsRequest;
import com.example.runmaze.stravazpot.stream.request.GetSegmentStreamsRequest;
import com.example.runmaze.stravazpot.stream.rest.StreamRest;

public class StreamAPI extends StravaAPI {

    public StreamAPI(StravaConfig config) {
        super(config);
    }

    public GetActivityStreamsRequest getActivityStreams(int activityID) {
        return new GetActivityStreamsRequest(activityID, getAPI(StreamRest.class), this);
    }

    public GetSegmentEffortStreamsRequest getSegmentEffortStreams(long segmentEffortID) {
        return new GetSegmentEffortStreamsRequest(segmentEffortID, getAPI(StreamRest.class), this);
    }

    public GetSegmentStreamsRequest getSegmentStreams(int segmentID) {
        return new GetSegmentStreamsRequest(segmentID, getAPI(StreamRest.class), this);
    }

    public GetRouteStreamsRequest getRouteStreams(int routeID) {
        return new GetRouteStreamsRequest(routeID, getAPI(StreamRest.class), this);
    }
}
