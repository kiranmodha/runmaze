package com.example.runmaze.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.runmaze.data.model.City;
import com.example.runmaze.data.model.Club;

import java.util.ArrayList;


public class ClubTable {
    private static final String TABLE_CLUB_MASTER = "club_master";
    private static final String COL_ROW_ID = "row_id";
    private static final String COL_CLUB_NAME = "club_name";

    private final SQLiteOpenHelper dbHandler;

    public ClubTable(SQLiteOpenHelper dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void createTable(SQLiteDatabase db) {
        String createClubTable = " CREATE TABLE " + TABLE_CLUB_MASTER + " ( " +
                COL_ROW_ID + " INTEGER, " +
                COL_CLUB_NAME + " TEXT ) ";
        db.execSQL(createClubTable);
    }

    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the table if already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLUB_MASTER);
        // Create table again
        createTable(db);
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(TABLE_CLUB_MASTER,null,null);
        db.close();
    }

    public void addClub(Club club) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_ROW_ID, club.getId());
        values.put(COL_CLUB_NAME, club.getName());

        db.insert(TABLE_CLUB_MASTER, null, values);

        db.close();
    }

    public String getTextFromId(int id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql;
        String text = "" ;
        sql = "select "
                + COL_CLUB_NAME
                + " from " + TABLE_CLUB_MASTER
                + " where " + COL_ROW_ID + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            text = cursor.getString(0);
            break;
        }
        cursor.close();
        return text;
    }

    public ArrayList<Club> getClubs() {
        ArrayList<Club> arrayList = new ArrayList<>();

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql;

        sql = "select "
                + COL_ROW_ID + " , "
                + COL_CLUB_NAME
                + " from " + TABLE_CLUB_MASTER +
                " order by " + COL_CLUB_NAME;

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Club club = new Club(
                    cursor.getString(0),
                    cursor.getString(1));
            arrayList.add(club);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return arrayList;
    }
}
