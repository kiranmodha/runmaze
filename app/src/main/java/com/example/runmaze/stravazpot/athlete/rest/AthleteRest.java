package com.example.runmaze.stravazpot.athlete.rest;

import com.example.runmaze.stravazpot.athlete.model.Athlete;
import com.example.runmaze.stravazpot.athlete.model.Stats;
import com.example.runmaze.stravazpot.athlete.model.Zones;
import com.example.runmaze.stravazpot.common.model.Gender;
import com.example.runmaze.stravazpot.segment.model.SegmentEffort;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AthleteRest {
    @GET("athlete")
    Call<Athlete> getCurrentAthlete();

    @GET("athletes/{id}")
    Call<Athlete> getAthlete(@Path("id") Integer id);

    @PUT("athlete") @FormUrlEncoded
    Call<Athlete> updateAthlete(
            @Field("city") String city,
            @Field("state") String state,
            @Field("country") String country,
            @Field("sex") Gender sex,
            @Field("weight") Float weight);

    @GET("athlete/zones")
    Call<Zones> getAthleteZones();

    @GET("athletes/{id}/stats")
    Call<Stats> getAthleteStats(@Path("id") Integer id);

    @GET("athletes/{id}/koms")
    Call<List<SegmentEffort>> listAthleteKOMS(
            @Path("id") Integer id,
            @Query("page") Integer page,
            @Query("per_page") Integer perPage);
}
