package com.example.runmaze.data.model;

//import java.sql.Time;
import android.widget.TextView;

import com.example.runmaze.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Workout {

    private int _id;
    private int _athlete_id;
    private String _datetime;
    private float _distance;
    private String _activityType;
    private int _HH;
    private int _MM;
    private int _SS;
    private String _link;
    private int remote_update;

    public Workout() { }

    public Workout(int id,  int athleteId, String datetime,  float distance, String activityType, int Hours, int Minutes, int Seconds, String link) {
        this._id = id;
        this._athlete_id = athleteId;
        this._datetime = datetime;
        this._distance = distance;
        this._activityType = activityType;
        this._HH = Hours;
        this._MM = Minutes;
        this._SS = Seconds;
        this._link = link;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + _id +
                "athlete_id=" + _athlete_id +
                ", datetime=" + _datetime +
                ", distance=" + _distance +
                ", activityType=" + _activityType +
                ", HH=" + _HH +
                ", MM=" + _MM +
                ", SS=" + _SS +
                ", link='" + _link + '\'' +
                '}';
    }



//    public String getJSON() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("id", getId());
//            jsonObject.put("athlete_id", getAthleteId());
//            jsonObject.put("datetime", getDateTime());
//            jsonObject.put("distance", getDistance());
//            jsonObject.put("activityType", getActivityType());
//            jsonObject.put("HH", getHH());
//            jsonObject.put("MM", getMM());
//            jsonObject.put("SS", getSS());
//            jsonObject.put("link", getLink());
//            jsonObject.put("remote_update", getRemote_update());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }

    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("athlete_id", getAthleteId());
            jsonObject.put("datetime", getDateTime());
            jsonObject.put("distance", getDistance());
            jsonObject.put("activityType", getActivityType());
            jsonObject.put("HH", getHH());
            jsonObject.put("MM", getMM());
            jsonObject.put("SS", getSS());
            jsonObject.put("link", getLink());
            jsonObject.put("remote_update", getRemote_update());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }



    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public int getAthleteId() {
        return _athlete_id;
    }

    public void setAthleteId(int athleteId) {
        this._athlete_id = athleteId;
    }


    public String getDateTime() {
        return _datetime;
    }

    public void setDatetime(String datetime) {
        this._datetime = datetime;
    }

    public String getActivityType() {
        return _activityType;
    }

    public void setActivityType(String activityType) {
        this._activityType = activityType;
    }

    public float getDistance() {
        return _distance;
    }

    public void setDistance(float distance) {
        this._distance = distance;
    }

    public int getHH() {
        return _HH;
    }

    public void setHH(int HH) {
        this._HH = HH;
    }

    public int getMM() {
        return _MM;
    }

    public void setMM(int MM) {
        this._MM = MM;
    }

    public int getSS() {
        return _SS;
    }

    public void setSS(int SS) {
        this._SS = SS;
    }

    public String getLink() {
        return _link;
    }

    public void setLink(String link) {
        this._link = link;
    }

    public int getRemote_update() {
        return remote_update;
    }

    public void setRemote_update(int remote_update) {
        this.remote_update = remote_update;
    }

    public String getPace() {
        float totalTime = (_HH + _MM/60f + _SS/3600f);
        float pace = totalTime / _distance * 60;
        int paceMin = (int) pace;
        int paceSec = (int)((pace - paceMin) * 60);
        return String.format("%d:%02d min/km", paceMin, paceSec);
    }

    public String getSpeed() {
        float totalTime = (_HH + _MM/60f + _SS/3600f);
        return String.format("%.2f km/h", _distance / totalTime);
    }
}
