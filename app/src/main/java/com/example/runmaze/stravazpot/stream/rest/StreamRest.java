package com.example.runmaze.stravazpot.stream.rest;

import com.example.runmaze.stravazpot.stream.model.Resolution;
import com.example.runmaze.stravazpot.stream.model.SeriesType;
import com.example.runmaze.stravazpot.stream.model.Stream;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StreamRest {
    @GET("activities/{id}/streams/{types}")
    Call<List<Stream>> getActivityStreams(
            @Path("id") Integer id,
            @Path("types") String types,
            @Query("resolution") Resolution resolution,
            @Query("series_type") SeriesType seriesType);

    @GET("segment_efforts/{id}/streams/{types}")
    Call<List<Stream>> getSegmentEffortStreams(
            @Path("id") Long id,
            @Path("types") String types,
            @Query("resolution") Resolution resolution,
            @Query("series_type") SeriesType seriesType);

    @GET("segments/{id}/streams/{types}")
    Call<List<Stream>> getSegmentStreams(
            @Path("id") Integer id,
            @Path("types") String types,
            @Query("resolution") Resolution resolution,
            @Query("series_type") SeriesType seriesType);

    @GET("routes/{id}/streams")
    Call<List<Stream>> getRouteStreams(@Path("id") Integer id);
}
