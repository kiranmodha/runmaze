package com.example.runmaze.stravazpot.athlete.model;

import com.google.gson.annotations.SerializedName;
import com.example.runmaze.stravazpot.common.model.Interval;

import java.util.List;

public class HeartRate {
    @SerializedName("custom_zones") private boolean customZones;
    @SerializedName("zones") private List<Interval<Float>> zones;

    public boolean hasCustomZones() {
        return customZones;
    }

    public List<Interval<Float>> getZones() {
        return zones;
    }
}
