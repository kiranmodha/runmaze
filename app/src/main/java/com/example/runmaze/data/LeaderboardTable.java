package com.example.runmaze.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.runmaze.data.model.LeaderboardItem;

import java.util.ArrayList;


public class LeaderboardTable {

    private static final String TABLE_LEADERBOARD = "leaderboard";
    private static final String COL_NAME = "name";
    private static final String COL_NOS = "activity_count";
    private static final String COL_DISTANCE = "distance";
    private static final String COL_DAYS = "days";
    private static final String COL_CLUB = "club";
    private static final String COL_COMPANY = "company";
    private static final String COL_CITY = "city";
    private static final String COL_PERIOD = "period";
    private static final String COL_LB_TYPE = "leaderboard_type";
    private static final String COL_ACTIVITY_TYPE = "activity_type";

    private final SQLiteOpenHelper dbHandler;

    public LeaderboardTable(SQLiteOpenHelper dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void createTable(SQLiteDatabase db) {
        String createCityTable = " CREATE TABLE " + TABLE_LEADERBOARD + " ( " +
                COL_NAME + " TEXT, " +
                COL_NOS + " INTEGER, " +
                COL_DISTANCE + " REAL, " +
                COL_DAYS + " INTEGER, " +
                COL_CLUB + " INTEGER, " +
                COL_COMPANY + " INTEGER, " +
                COL_CITY + " INTEGER, " +
                COL_PERIOD + " TEXT, " +
                COL_ACTIVITY_TYPE + " TEXT, " +
                COL_LB_TYPE + " TEXT ) ";
        db.execSQL(createCityTable);
    }

    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the table if already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEADERBOARD);
        // Create table again
        createTable(db);
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(TABLE_LEADERBOARD, " leaderboard_type = '' ", null);
        db.close();
    }

    public void deleteAllRecordsHDC() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(TABLE_LEADERBOARD, " leaderboard_type = 'HDC' ", null);
        db.close();
    }

    public void addLeaderboardItem(LeaderboardItem leaderboardItem) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_NAME, leaderboardItem.getName());
        values.put(COL_NOS, leaderboardItem.getActivityCount());
        values.put(COL_DISTANCE, leaderboardItem.getDistance());
        values.put(COL_DAYS, leaderboardItem.getDayReported());
        values.put(COL_CLUB, leaderboardItem.getClubId());
        values.put(COL_COMPANY, leaderboardItem.getCompanyId());
        values.put(COL_CITY, leaderboardItem.getCityId());
        values.put(COL_PERIOD, leaderboardItem.getPeriod());
        values.put(COL_ACTIVITY_TYPE, leaderboardItem.getActivityType());
        values.put(COL_LB_TYPE, leaderboardItem.getLeaderboardType());
        db.insert(TABLE_LEADERBOARD, null, values);

        db.close();
    }



    public ArrayList<LeaderboardItem> getLeaderboardItems(String period, String filter) {
        ArrayList<LeaderboardItem> arrayList = new ArrayList<>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql;
        sql = "select "
                + COL_NAME + " , "
                + COL_NOS + " , "
                + COL_DISTANCE + " , "
                + COL_CLUB + " , "
                + COL_COMPANY + " , "
                + COL_CITY + " , "
                + COL_PERIOD + " , "
                + COL_ACTIVITY_TYPE + " , "
                + COL_LB_TYPE + " , "
                + COL_DAYS
                + " from " + TABLE_LEADERBOARD
                + " where " + COL_PERIOD + " = '" + period + "'" ;

        if (filter.length() > 0)
               sql = sql + " and " + filter  ;

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int i = 1;
        while (!cursor.isAfterLast()) {
            LeaderboardItem leaderboardItem = new LeaderboardItem(
                    cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getFloat(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8)
                    );
            leaderboardItem.setDayReported(cursor.getInt(9));
            leaderboardItem.setSerialNo(i);
            i++;
            arrayList.add(leaderboardItem);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }
}
