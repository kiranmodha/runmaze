package com.example.runmaze.data.model;

public class LeaderboardItem {

    int serialNo;
    String name;
    int activityCount;
    int dayReported;
    Float distance;
    int clubId;
    int companyId;
    int cityId;
    String period;
    String activityType;
    String leaderboardType;



    public LeaderboardItem(String name, int activityCount, Float distance, int clubId, int companyId, int cityId, String  period, String activityType, String leaderboardType) {
        this.name = name;
        this.activityCount = activityCount;
        this.distance = distance;
        this.clubId = clubId;
        this.companyId = companyId;
        this.cityId = cityId;
        this.period = period;
        this.activityType = activityType;
        this.leaderboardType = leaderboardType;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public int getDayReported() {
        return dayReported;
    }

    public void setDayReported(int dayReported) {
        this.dayReported = dayReported;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getLeaderboardType() {
        return leaderboardType;
    }

    public void setLeaderboardType(String leaderboardType) {
        this.leaderboardType = leaderboardType;
    }
}
