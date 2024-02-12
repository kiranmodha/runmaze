package com.example.runmaze.stravazpot.segment.request;

import com.example.runmaze.stravazpot.segment.api.SegmentAPI;
import com.example.runmaze.stravazpot.segment.model.SegmentEffort;
import com.example.runmaze.stravazpot.segment.rest.SegmentRest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

public class ListSegmentEffortsRequest {

    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final int segmentID;
    private final SegmentRest restService;
    private final SegmentAPI api;
    private Integer athleteID;
    private Date startDateLocal;
    private Date endDateLocal;
    private Integer page;
    private Integer perPage;

    public ListSegmentEffortsRequest(int segmentID, SegmentRest restService, SegmentAPI api) {
        this.segmentID = segmentID;
        this.restService = restService;
        this.api = api;
    }

    public ListSegmentEffortsRequest forAthlete(int athleteID) {
        this.athleteID = athleteID;
        return this;
    }

    public ListSegmentEffortsRequest startingOn(Date startDateLocal) {
        this.startDateLocal = startDateLocal;
        return this;
    }

    public ListSegmentEffortsRequest endingOn(Date endDateLocal) {
        this.endDateLocal = endDateLocal;
        return this;
    }

    public ListSegmentEffortsRequest inPage(int page) {
        this.page = page;
        return this;
    }

    public ListSegmentEffortsRequest perPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public List<SegmentEffort> execute() {
        Call<List<SegmentEffort>> call = restService.getSegmentEfforts(segmentID, athleteID,
                formatter.format(startDateLocal), formatter.format(endDateLocal), page, perPage);
        return api.execute(call);
    }

}
