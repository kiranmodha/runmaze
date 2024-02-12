package com.example.runmaze.stravazpot.segment.api;

import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;
import com.example.runmaze.stravazpot.segment.model.Bounds;
import com.example.runmaze.stravazpot.segment.request.ExploreSegmentsRequest;
import com.example.runmaze.stravazpot.segment.request.GetSegmentLeaderboardRequest;
import com.example.runmaze.stravazpot.segment.request.GetSegmentRequest;
import com.example.runmaze.stravazpot.segment.request.ListAthleteStarredSegmentsRequest;
import com.example.runmaze.stravazpot.segment.request.ListMyStarredSegmentsRequest;
import com.example.runmaze.stravazpot.segment.request.ListSegmentEffortsRequest;
import com.example.runmaze.stravazpot.segment.request.StarSegmentRequest;
import com.example.runmaze.stravazpot.segment.rest.SegmentRest;

public class SegmentAPI extends StravaAPI{

    public SegmentAPI(StravaConfig config) {
        super(config);
    }

    public GetSegmentRequest getSegment(int segmentID) {
        return new GetSegmentRequest(segmentID, getAPI(SegmentRest.class), this);
    }

    public ListMyStarredSegmentsRequest listMyStarredSegments() {
        return new ListMyStarredSegmentsRequest(getAPI(SegmentRest.class), this);
    }

    public ListAthleteStarredSegmentsRequest listStarredSegmentsByAthlete(int athleteID) {
        return new ListAthleteStarredSegmentsRequest(athleteID, getAPI(SegmentRest.class), this);
    }

    public StarSegmentRequest starSegment(int segmentID) {
        return new StarSegmentRequest(segmentID, true, getAPI(SegmentRest.class), this);
    }

    public StarSegmentRequest unstarSegment(int segmentID) {
        return new StarSegmentRequest(segmentID, false, getAPI(SegmentRest.class), this);
    }

    public ListSegmentEffortsRequest listSegmentEfforts(int segmentID) {
        return new ListSegmentEffortsRequest(segmentID, getAPI(SegmentRest.class), this);
    }

    public GetSegmentLeaderboardRequest getLeaderboardForSegment(int segmentID) {
        return new GetSegmentLeaderboardRequest(segmentID, getAPI(SegmentRest.class), this);
    }

    public ExploreSegmentsRequest exploreSegmentsInRegion(Bounds bounds) {
        return new ExploreSegmentsRequest(bounds, getAPI(SegmentRest.class), this);
    }
}
