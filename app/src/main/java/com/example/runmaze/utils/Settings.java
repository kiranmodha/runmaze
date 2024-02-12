package com.example.runmaze.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;


public class Settings {
    int athleteId;
    int clientId ;
    String clientSecret;
    boolean loggedIn ;
    int allowDirectStrava = 0;
    float city_master_version = 0f;
    float club_master_version = 0f;
    float company_master_version = 0f;
    long last_request_for_workout;
    long last_request_for_leaderboard;
    long last_request_for_hdc_leaderboard;
    long gap_request_for_workout;
    long gap_request_for_leaderboard ;
    long gap_request_for_hdc_leaderboard ;
    float required_version;
    boolean first_strava_connect;
    boolean show_hdc_leaderboard;
    String url;



    boolean fetchAllWorkouts;

    String userId;
    String password;
    Context context;
    SharedPreferences settings;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFirstStravaConnect() {
        return first_strava_connect;
    }

    public void setFirstStravaConnect(boolean firstStravaConnect) {
        this.first_strava_connect = firstStravaConnect;
    }


    public float getRequiredVersion() {
        return required_version;
    }

    public void setRequiredVersion(float requiredVersion) {
        this.required_version = requiredVersion;
    }

    public boolean isFetchAllWorkouts() {
        return fetchAllWorkouts;
    }

    public void setFetchAllWorkouts(boolean fetchAllWorkouts) {
        this.fetchAllWorkouts = fetchAllWorkouts;
    }

    public boolean isShowHdcLeaderboard() {
        return show_hdc_leaderboard;
    }

    public void setShowHdcLeaderboard(boolean showHdcLeaderboard) {
        this.show_hdc_leaderboard = showHdcLeaderboard;
    }

    public long getGapRequestForWorkout() {
        return gap_request_for_workout;
    }

    public void setGapRequestForWorkout(long gapRequestForWorkout) {
        this.gap_request_for_workout = gapRequestForWorkout;
    }

    public long getGapRequestForLeaderboard() {
        return gap_request_for_leaderboard;
    }

    public void setGapRequestForLeaderboard(long gapRequestForLeaderboard) {
        this.gap_request_for_leaderboard = gapRequestForLeaderboard;
    }

    public long getGapRequestForHdcLeaderboard() {
        return gap_request_for_hdc_leaderboard;
    }

    public void setGapRequestForHdcLeaderboard(long gapRequestForHdcLeaderboard) {
        this.gap_request_for_hdc_leaderboard = gapRequestForHdcLeaderboard;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getLastRequestForLeaderboard() {
        return last_request_for_leaderboard;
    }

    public void setLastRequestForLeaderboard(long lastRequestForLeaderboard) {
        this.last_request_for_leaderboard = lastRequestForLeaderboard;
    }

    public long getLastRequestForHdcLeaderboard() {
        return last_request_for_hdc_leaderboard;
    }

    public void setLastRequestForHdcLeaderboard(long lastRequestForHdcLeaderboard) {
        this.last_request_for_hdc_leaderboard = lastRequestForHdcLeaderboard;
    }

    public long getLastRequestForWorkout() {
        return last_request_for_workout;
    }

    public void setLastRequestForWorkout(long lastRequestForWorkout) {
        this.last_request_for_workout = lastRequestForWorkout;
    }

    public Float getCityMasterVersion() {
        return city_master_version;
    }

    public void setCityMasterVersion(float version) {
        this.city_master_version = version;
    }

    public Float getClubMasterVersion() {
        return club_master_version;
    }

    public void setClubMasterVersion(float version) {
        this.club_master_version = version;
    }

    public Float getCompanyMasterVersion() {
        return company_master_version;
    }

    public void setCompanyMasterVersion(float version) {
        this.company_master_version = version;
    }

    public boolean isAllowDirectStrava() {
        if (allowDirectStrava == 1)
            return true;
        else
            return false;
    }

    public void setAllowDirectStrava(boolean allowDirectStrava) {
        if (allowDirectStrava)
            this.allowDirectStrava = 1;
        else
            this.allowDirectStrava = 0;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SharedPreferences getSettings() {
        return settings;
    }

    public void setSettings(SharedPreferences settings) {
        this.settings = settings;
    }



    public Settings(Context context) {
        this.context = context;
        this.settings = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        this.athleteId = settings.getInt("id", 0);
        this.clientId = settings.getInt("client_id", 76621);
        this.clientSecret = settings.getString("client_secret", "5635717f59e4ade74bf85b16eb0ce74555e25125");
        this.loggedIn = settings.getBoolean("logged", false);
        this.allowDirectStrava = settings.getInt("direct_strava",0);
        this.city_master_version = settings.getFloat("city_master_version", 0f);
        this.club_master_version = settings.getFloat("club_master_version", 0f);
        this.company_master_version = settings.getFloat("company_master_version", 0f);
        this.last_request_for_workout = settings.getLong("last_request_for_workout", 0);
        this.last_request_for_leaderboard = settings.getLong("last_request_for_leaderboard", 0);
        this.last_request_for_hdc_leaderboard = settings.getLong("last_request_for_hdc_leaderboard", 0);
        this.userId = settings.getString("user_id", "");
        this.password = settings.getString("password", "");
        this.gap_request_for_workout = settings.getLong("gap_request_for_workout", 3600000);
        this.gap_request_for_leaderboard = settings.getLong("gap_request_for_leaderboard", 900000);
        this.gap_request_for_hdc_leaderboard = settings.getLong("gap_request_for_hdc_leaderboard", 900000);
        this.fetchAllWorkouts = settings.getBoolean("fetch_all_workouts", false);
        this.first_strava_connect = settings.getBoolean("first_strava_connect", false);
        this.show_hdc_leaderboard = settings.getBoolean("show_hdc_leaderboard", true);
        this.url = settings.getString("url", "");
    }

    public Settings(Context context, String jsonString)
    {
        this(context);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            this.clientId = jsonObject.getInt("client_id");
            this.clientSecret = jsonObject.getString("client_secret");
            this.allowDirectStrava = jsonObject.getInt("direct_strava");
            this.city_master_version = (float)jsonObject.getDouble("city_master_version");
            this.club_master_version = (float)jsonObject.getDouble("club_master_version");
            this.company_master_version = (float)jsonObject.getDouble("company_master_version");
            this.gap_request_for_workout = jsonObject.getLong("gap_workout_request");
            this.gap_request_for_leaderboard = jsonObject.getLong("gap_leaderboard_request");
            this.gap_request_for_hdc_leaderboard = jsonObject.getLong("gap_hdc_leaderboard_request");
            this.show_hdc_leaderboard = jsonObject.getBoolean("show_hdc_leaderboard");
            this.required_version = (float)jsonObject.getDouble("app_version");
            this.url = jsonObject.getString("url");
        } catch (Exception e) {}


    }


/*    "athlete_id": "29",
            "client_id": "76621",
            "client_secret": "5635717f59e4ade74bf85b16eb0ce74555e25125",
            "direct_strava": "1",
            "city_master_version": "1",
            "club_master_version": "1",
            "company_master_version": "1",
            "events": [
    {
        "event_name": "100 days - 2022",
            "event_type": "Run",
            "from_date": "2022-02-23",
            "to_date": "2022-02-26",
            "is_active": "1",
            "keep_display": "1",
            "fragment": "1"
    }*/

    public void save() {
        SharedPreferences.Editor editor = this.settings.edit();
        editor.putBoolean("logged", this.loggedIn);
        editor.putInt("id", this.athleteId);
        editor.putInt("client_id", this.clientId);
        editor.putString("client_secret", this.clientSecret);
        editor.putInt("direct_strava",  this.allowDirectStrava);
        editor.putFloat("city_master_version", this.city_master_version);
        editor.putFloat("club_master_version", this.club_master_version);
        editor.putFloat("company_master_version", this.company_master_version);
        editor.putLong("last_request_for_workout", this.last_request_for_workout);
        editor.putLong("last_request_for_leaderboard", this.last_request_for_leaderboard);
        editor.putLong("last_request_for_hdc_leaderboard", this.last_request_for_hdc_leaderboard);
        editor.putString("user_id", this.userId);
        editor.putString("password", this.password);
        editor.putLong("gap_request_for_workout", this.gap_request_for_workout);
        editor.putLong("gap_request_for_leaderboard", this.gap_request_for_leaderboard);
        editor.putLong("gap_request_for_hdc_leaderboard", this.gap_request_for_hdc_leaderboard);
        editor.putBoolean("fetch_all_workouts", this.fetchAllWorkouts);
        editor.putBoolean("first_strava_connect", this.first_strava_connect);
        editor.putBoolean("show_hdc_leaderboard", this.show_hdc_leaderboard);


        editor.putString("url",this.url);


        editor.commit();
    }

}
