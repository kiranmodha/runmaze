package com.example.runmaze.stravazpot.athlete.api;

import com.example.runmaze.stravazpot.athlete.request.GetAthleteFollowersRequest;
import com.example.runmaze.stravazpot.athlete.request.GetAthleteFriendsRequest;
import com.example.runmaze.stravazpot.athlete.request.GetBothFollowingRequest;
import com.example.runmaze.stravazpot.athlete.request.GetMyFollowersRequest;
import com.example.runmaze.stravazpot.athlete.request.GetMyFriendsRequest;
import com.example.runmaze.stravazpot.athlete.rest.FriendRest;
import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;

public class FriendAPI extends StravaAPI {

    public FriendAPI(StravaConfig config) {
        super(config);
    }

    public GetMyFriendsRequest getMyFriends() {
        return new GetMyFriendsRequest(getAPI(FriendRest.class), this);
    }

    public GetAthleteFriendsRequest getAthleteFriends(int athleteID) {
        return new GetAthleteFriendsRequest(athleteID, getAPI(FriendRest.class), this);
    }

    public GetMyFollowersRequest getMyFollowers() {
        return new GetMyFollowersRequest(getAPI(FriendRest.class), this);
    }

    public GetAthleteFollowersRequest getAthleteFollowers(int athleteID) {
        return new GetAthleteFollowersRequest(athleteID, getAPI(FriendRest.class), this);
    }

    public GetBothFollowingRequest getBothFollowing(int athleteID) {
        return new GetBothFollowingRequest(athleteID, getAPI(FriendRest.class), this);
    }
}
