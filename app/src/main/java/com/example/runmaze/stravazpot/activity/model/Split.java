package com.example.runmaze.stravazpot.activity.model;

import com.google.gson.annotations.SerializedName;
import com.example.runmaze.stravazpot.common.model.Distance;
import com.example.runmaze.stravazpot.common.model.Time;

public class Split {
    @SerializedName("distance") private Distance distance;
    @SerializedName("elapsed_time") private Time elapsedTime;
    @SerializedName("elevation_difference") private Distance elevationDifference;
    @SerializedName("moving_time") private Time movingTime;
    @SerializedName("split") private int split;

    public Distance getDistance() {
        return distance;
    }

    public Time getElapsedTime() {
        return elapsedTime;
    }

    public Distance getElevationDifference() {
        return elevationDifference;
    }

    public Time getMovingTime() {
        return movingTime;
    }

    public int getSplit() {
        return split;
    }
}
