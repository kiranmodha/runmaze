package com.example.runmaze.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.runmaze.data.model.StravaAuth;

public class StravaAuthTable {
    private static final String TABLE_STRAVA_AUTH = "strava_auth";
    private static final String COL_ATHLETE_ID = "athlete_id";
    private static final String COL_ACCESS_TOKEN = "access_token";
    private static final String COL_EXPIRES_AT = "expires_at";
    private static final String COL_REFRESH_TOKEN = "refresh_token";


    private final SQLiteOpenHelper dbHandler;

    public StravaAuthTable(SQLiteOpenHelper dbHandler) {
        this.dbHandler = dbHandler;
    }

    void createTable(SQLiteDatabase db) {
        String createStravaAuthTable = " CREATE TABLE " + TABLE_STRAVA_AUTH + " ( " +
                COL_ATHLETE_ID + " INTEGER PRIMARY KEY, " +
                COL_ACCESS_TOKEN + " TEXT, " +
                COL_EXPIRES_AT + " INTEGER, " +
                COL_REFRESH_TOKEN + " TEXT ) ";
        db.execSQL(createStravaAuthTable);
    }

    void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the table if already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STRAVA_AUTH);
        // Create table again
        createTable(db);
    }


    public void addOrUpdateStravaAuth(StravaAuth stravaAuth) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql = "select * from  " + TABLE_STRAVA_AUTH
                + " where " + COL_ATHLETE_ID + " = "
                + stravaAuth.getAthlete_id();

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            cursor.close();
            db.close();
            updateStravaAuth(stravaAuth);
        } else {
            cursor.close();
            db.close();
            addStravaAuth(stravaAuth);
        }
    }


    public void addStravaAuth(StravaAuth stravaAuth) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_ATHLETE_ID, stravaAuth.getAthlete_id());
        values.put(COL_ACCESS_TOKEN, stravaAuth.getAccess_token());
        values.put(COL_EXPIRES_AT, stravaAuth.getExpires_at());
        values.put(COL_REFRESH_TOKEN, stravaAuth.getRefresh_token());

        db.insert(TABLE_STRAVA_AUTH, null, values);

        db.close();
    }

    @SuppressLint("DefaultLocale")
    public void updateStravaAuth(StravaAuth stravaAuth) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_ACCESS_TOKEN, stravaAuth.getAccess_token());
        values.put(COL_EXPIRES_AT, stravaAuth.getExpires_at());
        values.put(COL_REFRESH_TOKEN, stravaAuth.getRefresh_token());

        db.update(TABLE_STRAVA_AUTH, values, String.format(COL_ATHLETE_ID + " = %d", stravaAuth.getAthlete_id()), null);
        db.close();
    }


    public StravaAuth getStravaAuth() {

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql = "select "
                + COL_ATHLETE_ID + " , "
                + COL_ACCESS_TOKEN + " , "
                + COL_EXPIRES_AT + " , "
                + COL_REFRESH_TOKEN
                + " FROM " + TABLE_STRAVA_AUTH;

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            StravaAuth stravaAuth = new StravaAuth();
            stravaAuth.setAthlete_id(cursor.getInt(0));
            stravaAuth.setAccess_token(cursor.getString(1));
            stravaAuth.setExpires_at(cursor.getLong(2));
            stravaAuth.setRefresh_token(cursor.getString(3));

            cursor.close();
            db.close();
            return (stravaAuth);
        }
        cursor.close();
        db.close();
        return null;
    }

}
