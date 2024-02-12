package com.example.runmaze.stravazpot.club.rest;

import com.example.runmaze.stravazpot.activity.model.Activity;
import com.example.runmaze.stravazpot.athlete.model.Athlete;
import com.example.runmaze.stravazpot.club.model.Announcement;
import com.example.runmaze.stravazpot.club.model.Club;
import com.example.runmaze.stravazpot.club.model.Event;
import com.example.runmaze.stravazpot.club.model.JoinResult;
import com.example.runmaze.stravazpot.club.model.LeaveResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClubRest {
    @GET("clubs/{id}")
    Call<Club> getClub(@Path("id") Integer id);

    @GET("clubs/{id}/announcements")
    Call<List<Announcement>> getClubAnnouncements(@Path("id") Integer id);

    @GET("clubs/{id}/group_events")
    Call<List<Event>> getClubGroupEvents(@Path("id") Integer id);

    @GET("athlete/clubs")
    Call<List<Club>> getMyClubs();

    @GET("clubs/{id}/members")
    Call<List<Athlete>> getClubMembers(
            @Path("id") Integer id,
            @Query("page") Integer page,
            @Query("per_page") Integer perPage);

    @GET("clubs/{id}/admins")
    Call<List<Athlete>> getClubAdmins(
            @Path("id") Integer id,
            @Query("page") Integer page,
            @Query("per_page") Integer perPage);

    @GET("clubs/{id}/activities")
    Call<List<Activity>> getClubActivities(
            @Path("id") Integer id,
            @Query("before") Integer before,
            @Query("page") Integer page,
            @Query("per_page") Integer perPage);

    @POST("clubs/{id}/join")
    Call<JoinResult> joinClub(@Path("id") Integer id);

    @POST("clubs/{id}/leave")
    Call<LeaveResult> leaveClub(@Path("id") Integer id);
}
