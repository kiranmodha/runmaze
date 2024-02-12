package com.example.runmaze.stravazpot.activity.rest;

import com.example.runmaze.stravazpot.activity.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PhotosRest {
    @GET("activities/{id}/photos")
    Call<List<Photo>> getActivityPhotos(
            @Path("id") Integer id,
            @Query("photo_sources") Boolean photoSources);
}
