package com.example.runmaze.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "runmaze.db";
    private static final int DATABASE_VERSION = 4;


    public WorkoutTable workoutTable = new WorkoutTable(this);
    public StravaAuthTable stravaAuthTable = new StravaAuthTable(this);
    public AthleteTable athleteTable = new AthleteTable(this);
    public VersionTable versionTable = new VersionTable(this);
    public CityTable cityTable = new CityTable(this);
    public ClubTable clubTable = new ClubTable(this);
    public CompanyTable companyTable = new CompanyTable(this);
    public LeaderboardTable leaderboardTable = new LeaderboardTable(this);



    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        workoutTable.createTable(db);
        stravaAuthTable.createTable(db);
        athleteTable.createTable(db);
        versionTable.createTable(db);
        cityTable.createTable(db);
        clubTable.createTable(db);
        companyTable.createTable(db);
        leaderboardTable.createTable(db);
        createTempDayTable(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // workoutTable.upgradeTable(db, oldVersion, newVersion);
       // stravaAuthTable.upgradeTable(db, oldVersion, newVersion);
       // athleteTable.upgradeTable(db, oldVersion, newVersion);
       // versionTable.upgradeTable(db, oldVersion, newVersion);
       // cityTable.upgradeTable(db, oldVersion, newVersion);
       // clubTable.upgradeTable(db, oldVersion, newVersion);
       // companyTable.upgradeTable(db, oldVersion, newVersion);
       // leaderboardTable.upgradeTable(db, oldVersion, newVersion);
        if (oldVersion == 1 && newVersion == 2) {
            createTempDayTable(db);
        }

        if (newVersion == 4) {
            leaderboardTable.upgradeTable(db, oldVersion, newVersion);
        }

    }


    public void createTempDayTable(SQLiteDatabase db)
    {
        String createWorkoutTable = " CREATE TABLE DAYS ( " +
                " DAY INTEGER )";
        db.execSQL(createWorkoutTable);

        ContentValues values = new ContentValues();

        values.put("DAY", 1);
        db.insert("DAYS", null, values);
        values.put("DAY", 2);
        db.insert("DAYS", null, values);
        values.put("DAY", 3);
        db.insert("DAYS", null, values);
        values.put("DAY", 4);
        db.insert("DAYS", null, values);
        values.put("DAY", 5);
        db.insert("DAYS", null, values);
        values.put("DAY", 6);
        db.insert("DAYS", null, values);
        values.put("DAY", 7);
        db.insert("DAYS", null, values);
        values.put("DAY", 8);
        db.insert("DAYS", null, values);
        values.put("DAY", 9);
        db.insert("DAYS", null, values);
        values.put("DAY", 10);
        db.insert("DAYS", null, values);
        values.put("DAY", 11);
        db.insert("DAYS", null, values);
        values.put("DAY", 12);
        db.insert("DAYS", null, values);
        values.put("DAY", 13);
        db.insert("DAYS", null, values);
        values.put("DAY", 14);
        db.insert("DAYS", null, values);
        values.put("DAY", 15);
        db.insert("DAYS", null, values);
        values.put("DAY", 16);
        db.insert("DAYS", null, values);
        values.put("DAY", 17);
        db.insert("DAYS", null, values);
        values.put("DAY", 18);
        db.insert("DAYS", null, values);
        values.put("DAY", 19);
        db.insert("DAYS", null, values);
        values.put("DAY", 20);
        db.insert("DAYS", null, values);
        values.put("DAY", 21);
        db.insert("DAYS", null, values);
        values.put("DAY", 22);
        db.insert("DAYS", null, values);
        values.put("DAY", 23);
        db.insert("DAYS", null, values);
        values.put("DAY", 24);
        db.insert("DAYS", null, values);
        values.put("DAY", 25);
        db.insert("DAYS", null, values);
        values.put("DAY", 26);
        db.insert("DAYS", null, values);
        values.put("DAY", 27);
        db.insert("DAYS", null, values);
        values.put("DAY", 28);
        db.insert("DAYS", null, values);
        values.put("DAY", 29);
        db.insert("DAYS", null, values);
        values.put("DAY", 30);
        db.insert("DAYS", null, values);
        values.put("DAY", 31);
        db.insert("DAYS", null, values);
    }


}
