package com.example.runmaze.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.runmaze.data.model.City;
import com.example.runmaze.data.model.Workout;

import java.util.ArrayList;


public class CityTable {
    private static final String TABLE_CITY_MASTER = "city_master";
    private static final String COL_ROW_ID = "row_id";
    private static final String COL_CITY_NAME = "city_name";

    private final SQLiteOpenHelper dbHandler;

    public CityTable(SQLiteOpenHelper dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void createTable(SQLiteDatabase db) {
        String createCityTable = " CREATE TABLE " + TABLE_CITY_MASTER + " ( " +
                COL_ROW_ID + " INTEGER, " +
                COL_CITY_NAME + " TEXT ) ";
        db.execSQL(createCityTable);
    }

    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the table if already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY_MASTER);
        // Create table again
        createTable(db);
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(TABLE_CITY_MASTER, null, null);
        db.close();
    }

    public void addCity(City city) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_ROW_ID, city.getId());
        values.put(COL_CITY_NAME, city.getName());

        db.insert(TABLE_CITY_MASTER, null, values);

        db.close();
    }

    public String getTextFromId(int id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql;
        String text = "" ;
        sql = "select "
                + COL_CITY_NAME
                + " from " + TABLE_CITY_MASTER
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

    public ArrayList<City> getCities() {
        ArrayList<City> arrayList = new ArrayList<>();

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql;

        sql = "select "
                + COL_ROW_ID + " , "
                + COL_CITY_NAME
                + " from " + TABLE_CITY_MASTER +
                " order by " + COL_CITY_NAME;

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            City city = new City(
                    cursor.getString(0),
                    cursor.getString(1));
            arrayList.add(city);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return arrayList;
    }

}
