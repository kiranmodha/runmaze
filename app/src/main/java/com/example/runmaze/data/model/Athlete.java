package com.example.runmaze.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Athlete {
    private int id;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String birthdate;
    private int city;
    private int club;
    private int company;
    private StravaAuth strava_auth;
    private int remote_update;

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", city=" + city +
                ", club=" + club +
                ", company=" + company +
                ", strava_auth=" + strava_auth +
                ", remote_update =" + remote_update +
                '}';
    }


//    public String getJSON() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("id", getId());
//            jsonObject.put("name", getName());
//            jsonObject.put("email", getEmail());
//            jsonObject.put("password", getPassword());
//            jsonObject.put("birthdate", getBirthdate());
//            jsonObject.put("gender", getGender());
//            jsonObject.put("company", getCompany());
//            jsonObject.put("club", getClub());
//            jsonObject.put("city", getCity());
//            jsonObject.put("strava_athlete_id", getStrava_auth().getAthlete_id());
//            jsonObject.put("access_token", getStrava_auth().getAccess_token());
//            jsonObject.put("expires_at", getStrava_auth().getExpires_at());
//            jsonObject.put("refresh_token", getStrava_auth().getRefresh_token());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObject.toString();
//    }

    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("name", getName());
            jsonObject.put("email", getEmail());
            jsonObject.put("password", getPassword());
            jsonObject.put("birthdate", getBirthdate());
            jsonObject.put("gender", getGender());
            jsonObject.put("company", getCompany());
            jsonObject.put("club", getClub());
            jsonObject.put("city", getCity());
            jsonObject.put("strava_athlete_id", getStrava_auth().getAthlete_id());
            jsonObject.put("access_token", getStrava_auth().getAccess_token());
            jsonObject.put("expires_at", getStrava_auth().getExpires_at());
            jsonObject.put("refresh_token", getStrava_auth().getRefresh_token());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getClub() {
        return club;
    }

    public void setClub(int club) {
        this.club = club;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public StravaAuth getStrava_auth() {
        return strava_auth;
    }

    public void setStrava_auth(StravaAuth strava_auth) {
        this.strava_auth = strava_auth;
    }

    public int getRemote_update() {
        return remote_update;
    }

    public void setRemote_update(int remote_update) {
        this.remote_update = remote_update;
    }



}
