package com.example.runmaze.stravazpot.activity.rest;

import com.example.runmaze.stravazpot.athlete.model.Athlete;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KudosRest {
    @GET("activities/{id}/kudos")
    Call<List<Athlete>> getActivityKudos(
            @Path("id") Integer id,
            @Query("page") Integer page,
            @Query("per_page") Integer perPage);
}