package com.example.runmaze.stravazpot.gear.rest;

import com.example.runmaze.stravazpot.gear.model.Gear;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GearRest {
    @GET("gear/{id}")
    Call<Gear> getGear(@Path("id") String id);
}
