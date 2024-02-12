package com.example.runmaze.stravazpot.segment.request;

import com.example.runmaze.stravazpot.common.model.Gender;
import com.example.runmaze.stravazpot.segment.api.SegmentAPI;
import com.example.runmaze.stravazpot.segment.model.AgeGroup;
import com.example.runmaze.stravazpot.segment.model.DateRange;
import com.example.runmaze.stravazpot.segment.model.Leaderboard;
import com.example.runmaze.stravazpot.segment.model.WeightClass;
import com.example.runmaze.stravazpot.segment.rest.SegmentRest;

import retrofit2.Call;

public class GetSegmentLeaderboardRequest {

    private final int segmentID;
    private final SegmentRest restService;
    private final SegmentAPI api;
    private Gender gender;
    private AgeGroup ageGroup;
    private WeightClass weightClass;
    private Boolean following;
    private Integer clubID;
    private DateRange dateRange;
    private Integer contextEntries;
    private Integer page;
    private Integer perPage;

    public GetSegmentLeaderboardRequest(int segmentID, SegmentRest restService, SegmentAPI api) {
        this.segmentID = segmentID;
        this.restService = restService;
        this.api = api;
    }

    public GetSegmentLeaderboardRequest withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public GetSegmentLeaderboardRequest inAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
        return this;
    }

    public GetSegmentLeaderboardRequest inWeightClass(WeightClass weightClass) {
        this.weightClass = weightClass;
        return this;
    }

    public GetSegmentLeaderboardRequest following(boolean following) {
        this.following = following;
        return this;
    }

    public GetSegmentLeaderboardRequest inClub(int clubID) {
        this.clubID = clubID;
        return this;
    }

    public GetSegmentLeaderboardRequest inDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
        return this;
    }

    public GetSegmentLeaderboardRequest withContextEntries(int contextEntries) {
        this.contextEntries = contextEntries;
        return this;
    }

    public GetSegmentLeaderboardRequest inPage(int page) {
        this.page = page;
        return this;
    }

    public GetSegmentLeaderboardRequest perPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public Leaderboard execute() {
        Call<Leaderboard> call = restService.getSegmentLeaderboard(segmentID, gender, ageGroup,
                weightClass, following, clubID, dateRange, contextEntries, page, perPage);
        return api.execute(call);
    }
}
