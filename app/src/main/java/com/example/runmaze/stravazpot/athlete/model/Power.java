package com.example.runmaze.stravazpot.athlete.model;

import com.google.gson.annotations.SerializedName;
import com.example.runmaze.stravazpot.common.model.Interval;

import java.util.List;

public class Power {
    @SerializedName("zones") private List<Interval<Float>> zones;

    public List<Interval<Float>> getZones() {
        return zones;
    }
}
