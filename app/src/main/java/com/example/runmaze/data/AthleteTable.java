package com.example.runmaze.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.runmaze.data.model.Athlete;
import com.example.runmaze.data.model.StravaAuth;

public class AthleteTable {
    private static final String TABLE_ATHLETE = "athlete";
    private static final String COL_ID = "id";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COL_NAME = "athlete_name";
    private static final String COL_GENDER = "gender";
    private static final String COL_BIRTHDATE = "birthdate";
    private static final String COL_CITY = "city";
    private static final String COL_CLUB = "club";
    private static final String COL_COMPANY = "company";
    private static final String COL_STRAVA_ATHLETE_ID = "strava_athlete_id";
    private static final String COL_ACCESS_TOKEN = "access_token";
    private static final String COL_EXPIRES_AT = "expires_at";
    private static final String COL_REFRESH_TOKEN = "refresh_token";
    private static final String COL_REMOTE_UPDATE = "remote_update";
    private static final String COL_CLIENT_ID = "client_id";
    private static final String COL_CLIENT_SECRET = "client_secret";

    private final SQLiteOpenHelper dbHandler;

    public AthleteTable(SQLiteOpenHelper dbHandler) {
        this.dbHandler = dbHandler;
    }

    void createTable(SQLiteDatabase db) {
        String createAthleteTable = " CREATE TABLE " + TABLE_ATHLETE + " ( " +
                COL_ID + " INTEGER PRIMARY KEY, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_GENDER + " TEXT, " +
                COL_BIRTHDATE + " TEXT, " +
                COL_CITY + " INTEGER, " +
                COL_CLUB + " INTEGER, " +
                COL_COMPANY + " INTEGER, " +
                COL_STRAVA_ATHLETE_ID + " INTEGER, " +
                COL_ACCESS_TOKEN + " TEXT, " +
                COL_EXPIRES_AT + " INTEGER, " +
                COL_REFRESH_TOKEN + " TEXT, " +
                COL_CLIENT_ID + " INTEGER, " +
                COL_CLIENT_SECRET + " TEXT, " +
                COL_REMOTE_UPDATE + " INTEGER )";

        db.execSQL(createAthleteTable);
    }

    void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the table if already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATHLETE);
        // Create table again
        createTable(db);
    }

 //   public void alterTable(SQLiteDatabase db) {
/*        db.execSQL("ALTER TABLE  " + TABLE_ATHLETE + " ADD COLUMN " + COL_REMOTE_UPDATE + " INTEGER DEFAULT 0");
        db.execSQL("ALTER TABLE  " + TABLE_ATHLETE + " ADD COLUMN " + COL_CLIENT_ID + " INTEGER");
        db.execSQL("ALTER TABLE  " + TABLE_ATHLETE + " ADD COLUMN " + COL_CLIENT_SECRET + " TEXT");*/
 //   }


 //   public void TempUpdate() {
       // SQLiteDatabase db = dbHandler.getWritableDatabase();
        //ContentValues values = new ContentValues();
        //values.put(COL_CLIENT_ID, 76621);
        //values.put(COL_CLIENT_SECRET, "5635717f59e4ade74bf85b16eb0ce74555e25125");
        //db.update(TABLE_ATHLETE, values, "", null);
       // db.delete(TABLE_ATHLETE, " ID = 31 ",null);
       // db.close();
 //   }



    public void addAthlete(Athlete athlete) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_ID, athlete.getId());
        values.put(COL_EMAIL, athlete.getEmail());
        values.put(COL_PASSWORD, athlete.getPassword());
        values.put(COL_NAME, athlete.getName());
        values.put(COL_GENDER, athlete.getGender());
        values.put(COL_BIRTHDATE, athlete.getBirthdate());
        values.put(COL_CITY, athlete.getCity());
        values.put(COL_CLUB, athlete.getClub());
        values.put(COL_COMPANY, athlete.getCompany());
        values.put(COL_STRAVA_ATHLETE_ID, athlete.getStrava_auth().getAthlete_id());
        values.put(COL_ACCESS_TOKEN, athlete.getStrava_auth().getAccess_token());
        values.put(COL_EXPIRES_AT, athlete.getStrava_auth().getExpires_at());
        values.put(COL_REFRESH_TOKEN, athlete.getStrava_auth().getRefresh_token());
        values.put(COL_CLIENT_ID, athlete.getStrava_auth().getClientId());
        values.put(COL_CLIENT_SECRET, athlete.getStrava_auth().getClientSecret());
        values.put(COL_REMOTE_UPDATE, athlete.getRemote_update());

        db.insert(TABLE_ATHLETE, null, values);
        db.close();


    }

    @SuppressLint("DefaultLocale")
    public void updateAthlete(Athlete athlete) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_EMAIL, athlete.getEmail());
        values.put(COL_PASSWORD, athlete.getPassword());
        values.put(COL_NAME, athlete.getName());
        values.put(COL_GENDER, athlete.getGender());
        values.put(COL_BIRTHDATE, athlete.getBirthdate());
        values.put(COL_CITY, athlete.getCity());
        values.put(COL_CLUB, athlete.getClub());
        values.put(COL_COMPANY, athlete.getCompany());
        values.put(COL_STRAVA_ATHLETE_ID, athlete.getStrava_auth().getAthlete_id());
        values.put(COL_ACCESS_TOKEN, athlete.getStrava_auth().getAccess_token());
        values.put(COL_EXPIRES_AT, athlete.getStrava_auth().getExpires_at());
        values.put(COL_REFRESH_TOKEN, athlete.getStrava_auth().getRefresh_token());
        values.put(COL_CLIENT_ID, athlete.getStrava_auth().getClientId());
        values.put(COL_CLIENT_SECRET, athlete.getStrava_auth().getClientSecret());
        values.put(COL_REMOTE_UPDATE, athlete.getRemote_update());

        db.update(TABLE_ATHLETE, values, String.format(COL_ID + " = %d", athlete.getId()), null);

        db.close();
    }

    @SuppressLint("DefaultLocale")
    public void updateRemoteUpdateStatus(Athlete athlete) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_REMOTE_UPDATE, athlete.getRemote_update());
        db.update(TABLE_ATHLETE, values, String.format(COL_ID + " = %d", athlete.getId()), null);
        db.close();
    }



    public Athlete login(String email, String password) {
        return  fromSQL("select * from " + TABLE_ATHLETE + " where " + COL_EMAIL+ " ='" + email + "' and " + COL_PASSWORD + " = '" + password + "'");
    }


    public Athlete athleteByID(int id) {
        return  fromSQL("select * from " + TABLE_ATHLETE + " where " + COL_ID+ " =" + id);
    }

    private Athlete fromSQL(String sql) {
        Athlete athlete = new Athlete();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                athlete.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                athlete.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
                athlete.setPassword(cursor.getString(cursor.getColumnIndex(COL_PASSWORD)));
                athlete.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                athlete.setGender(cursor.getString(cursor.getColumnIndex(COL_GENDER)));
                athlete.setBirthdate(cursor.getString(cursor.getColumnIndex(COL_BIRTHDATE)));
                athlete.setCity(cursor.getInt(cursor.getColumnIndex(COL_CITY)));
                athlete.setClub(cursor.getInt(cursor.getColumnIndex(COL_CLUB)));
                athlete.setCompany(cursor.getInt(cursor.getColumnIndex(COL_COMPANY)));
                StravaAuth stravaAuth = new StravaAuth();

                stravaAuth.setAthlete_id(cursor.getInt(cursor.getColumnIndex(COL_STRAVA_ATHLETE_ID)));
                stravaAuth.setAccess_token(cursor.getString(cursor.getColumnIndex(COL_ACCESS_TOKEN)));
                stravaAuth.setExpires_at(cursor.getLong(cursor.getColumnIndex(COL_EXPIRES_AT)));
                stravaAuth.setRefresh_token(cursor.getString(cursor.getColumnIndex(COL_REFRESH_TOKEN)));
                stravaAuth.setClientId(cursor.getInt(cursor.getColumnIndex(COL_CLIENT_ID)));
                stravaAuth.setClientSecret(cursor.getString(cursor.getColumnIndex(COL_CLIENT_SECRET)));

                athlete.setStrava_auth(stravaAuth);
                athlete.setRemote_update(cursor.getInt(cursor.getColumnIndex(COL_REMOTE_UPDATE)));
            } else {
                athlete = null;
            }
        } else {
            athlete = null;
        }
        cursor.close();
        db.close();
        return athlete;
    }


    public StravaAuth getStravaAuth(int id) {

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql = "select "
                + COL_STRAVA_ATHLETE_ID + " , "
                + COL_ACCESS_TOKEN + " , "
                + COL_EXPIRES_AT + " , "
                + COL_REFRESH_TOKEN + " , "
                + COL_CLIENT_ID + " , "
                + COL_CLIENT_SECRET
                + " FROM " + TABLE_ATHLETE
                + " WHERE id = " + id  + " and " + COL_STRAVA_ATHLETE_ID + " > 0";


        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            StravaAuth stravaAuth = new StravaAuth();
            stravaAuth.setAthlete_id(cursor.getInt(0));
            stravaAuth.setAccess_token(cursor.getString(1));
            stravaAuth.setExpires_at(cursor.getLong(2));
            stravaAuth.setRefresh_token(cursor.getString(3));
            stravaAuth.setClientId(cursor.getInt(4));
            stravaAuth.setClientSecret(cursor.getString(5));

            cursor.close();
            db.close();
            return (stravaAuth);
        }
        cursor.close();
        db.close();
        return null;
    }

    public void updateStravaAuth(StravaAuth stravaAuth, int id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_STRAVA_ATHLETE_ID, stravaAuth.getAthlete_id());
        values.put(COL_ACCESS_TOKEN, stravaAuth.getAccess_token());
        values.put(COL_EXPIRES_AT, stravaAuth.getExpires_at());
        values.put(COL_REFRESH_TOKEN, stravaAuth.getRefresh_token());
        values.put(COL_CLIENT_ID, stravaAuth.getClientId());
        values.put(COL_CLIENT_SECRET, stravaAuth.getClientSecret());


        db.update(TABLE_ATHLETE, values, String.format(COL_ID + " = %d", id), null);
        db.close();
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(TABLE_ATHLETE, null, null);
        db.close();
    }


    public void updateStravaAuth(Athlete athlete) {
        StravaAuth stravaAuth = athlete.getStrava_auth();
        updateStravaAuth(stravaAuth, athlete.getId());
    }

}
