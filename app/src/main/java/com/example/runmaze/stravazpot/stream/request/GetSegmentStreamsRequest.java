package com.example.runmaze.stravazpot.stream.request;

import com.example.runmaze.stravazpot.stream.api.StreamAPI;
import com.example.runmaze.stravazpot.stream.model.Resolution;
import com.example.runmaze.stravazpot.stream.model.SeriesType;
import com.example.runmaze.stravazpot.stream.model.Stream;
import com.example.runmaze.stravazpot.stream.model.StreamType;
import com.example.runmaze.stravazpot.stream.rest.StreamRest;

import java.util.List;

import retrofit2.Call;

public class GetSegmentStreamsRequest {

    private final int segmentID;
    private final StreamRest restService;
    private final StreamAPI api;
    private StreamType[] types;
    private Resolution resolution;
    private SeriesType seriesType;

    public GetSegmentStreamsRequest(int segmentID, StreamRest restService, StreamAPI api) {
        this.segmentID = segmentID;
        this.restService = restService;
        this.api = api;
    }

    public GetSegmentStreamsRequest forTypes(StreamType... types) {
        this.types = types;
        return this;
    }

    public GetSegmentStreamsRequest withResolution(Resolution resolution) {
        this.resolution = resolution;
        return this;
    }

    public GetSegmentStreamsRequest withSeriesType(SeriesType seriesType) {
        this.seriesType = seriesType;
        return this;
    }

    public List<Stream> execute() {
        Call<List<Stream>> call = restService.getSegmentStreams(segmentID,
                StreamType.getQueryString(types), resolution, seriesType);
        return api.execute(call);
    }

}
