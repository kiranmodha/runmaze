package com.example.runmaze.stravazpot.club.api;

import com.example.runmaze.stravazpot.club.request.GetClubRequest;
import com.example.runmaze.stravazpot.club.request.JoinClubRequest;
import com.example.runmaze.stravazpot.club.request.LeaveClubRequest;
import com.example.runmaze.stravazpot.club.request.ListClubActivitiesRequest;
import com.example.runmaze.stravazpot.club.request.ListClubAdminsRequest;
import com.example.runmaze.stravazpot.club.request.ListClubAnnouncementsRequest;
import com.example.runmaze.stravazpot.club.request.ListClubGroupEventsRequest;
import com.example.runmaze.stravazpot.club.request.ListClubMembersRequest;
import com.example.runmaze.stravazpot.club.request.ListMyClubsRequest;
import com.example.runmaze.stravazpot.club.rest.ClubRest;
import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.common.api.StravaConfig;

public class ClubAPI extends StravaAPI {

    public ClubAPI(StravaConfig config) {
        super(config);
    }

    public GetClubRequest getClub(int clubID) {
        return new GetClubRequest(clubID, getAPI(ClubRest.class), this);
    }

    public ListClubAnnouncementsRequest listClubAnnouncements(int clubID) {
        return new ListClubAnnouncementsRequest(clubID, getAPI(ClubRest.class), this);
    }

    public ListClubGroupEventsRequest listClubGroupEvents(int clubID) {
        return new ListClubGroupEventsRequest(clubID, getAPI(ClubRest.class), this);
    }

    public ListMyClubsRequest listMyClubs() {
        return new ListMyClubsRequest(getAPI(ClubRest.class), this);
    }

    public ListClubMembersRequest listClubMembers(int clubID) {
        return new ListClubMembersRequest(clubID, getAPI(ClubRest.class), this);
    }

    public ListClubAdminsRequest listClubAdmins(int clubID) {
        return new ListClubAdminsRequest(clubID, getAPI(ClubRest.class), this);
    }

    public ListClubActivitiesRequest listClubActivities(int clubID) {
        return new ListClubActivitiesRequest(clubID, getAPI(ClubRest.class), this);
    }

    public JoinClubRequest joinClub(int clubID) {
        return new JoinClubRequest(clubID, getAPI(ClubRest.class), this);
    }

    public LeaveClubRequest leaveClub(int clubID) {
        return new LeaveClubRequest(clubID, getAPI(ClubRest.class), this);
    }
}
