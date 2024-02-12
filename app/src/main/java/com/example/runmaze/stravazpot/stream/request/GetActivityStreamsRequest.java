package com.example.runmaze.stravazpot.stream.request;

import com.example.runmaze.stravazpot.stream.api.StreamAPI;
import com.example.runmaze.stravazpot.stream.model.Resolution;
import com.example.runmaze.stravazpot.stream.model.SeriesType;
import com.example.runmaze.stravazpot.stream.model.Stream;
import com.example.runmaze.stravazpot.stream.model.StreamType;
import com.example.runmaze.stravazpot.stream.rest.StreamRest;

import java.util.List;

import retrofit2.Call;

public class GetActivityStreamsRequest {

    private final int activityID;
    private final StreamRest restService;
    private final StreamAPI api;
    private StreamType[] types;
    private Resolution resolution;
    private SeriesType seriesType;

    public GetActivityStreamsRequest(int activityID, StreamRest restService, StreamAPI api) {
        this.activityID = activityID;
        this.restService = restService;
        this.api = api;
    }

    public GetActivityStreamsRequest forTypes(StreamType... types) {
        this.types = types;
        return this;
    }

    public GetActivityStreamsRequest withResolution(Resolution resolution) {
        this.resolution = resolution;
        return this;
    }

    public GetActivityStreamsRequest withSeriesType(SeriesType seriesType) {
        this.seriesType = seriesType;
        return this;
    }

    public List<Stream> execute() {
        Call<List<Stream>> call = restService.getActivityStreams(activityID,
                StreamType.getQueryString(types), resolution, seriesType);
        return api.execute(call);
    }

}
