package com.example.runmaze.data.model;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class StravaAuth {

    private int _athlete_id;
    private String _access_token;
    private Long _expires_at;
    private String _refresh_token;
    private int clientId;
    private String clientSecret;



    public StravaAuth() { }

    public StravaAuth(int athlete_id, String access_token, Long expires_at, String refresh_token, int clientId, String clientSecret) {
        this._athlete_id = athlete_id;
        this._access_token = access_token;
        this._expires_at = expires_at;
        this._refresh_token = refresh_token;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String toString() {
        return "StravaAuth{" +
                "_athlete_id=" + _athlete_id +
                ", _access_token='" + _access_token + '\'' +
                ", _expires_at=" + _expires_at +
                ", _refresh_token='" + _refresh_token + '\'' +
                '}';
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAthlete_id() {
        return _athlete_id;
    }

    public void setAthlete_id(int athlete_id) {
        this._athlete_id = athlete_id;
    }

    public String getAccess_token() {
        return _access_token;
    }

    public void setAccess_token(String access_token) {
        this._access_token = access_token;
    }

    public Long getExpires_at() {
        return _expires_at;
    }

    public void setExpires_at(Long expires_at) {
        this._expires_at = expires_at;
    }

    public String getRefresh_token() {
        return _refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this._refresh_token = refresh_token;
    }

    public boolean isAccessTokenValid() {
        Date validTo = new java.util.Date (_expires_at * 1000L);
        Date now = new Date();
        if (now.after(validTo))
        {
            return false;
        }
        return true;
    }






}
