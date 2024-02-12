package com.example.runmaze.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.runmaze.data.model.Club;
import com.example.runmaze.data.model.Company;

import java.util.ArrayList;


public class CompanyTable {
    private static final String TABLE_COMPANY_MASTER = "company_master";
    private static final String COL_ROW_ID = "row_id";
    private static final String COL_COMPANY_NAME = "company_name";

    private final SQLiteOpenHelper dbHandler;

    public CompanyTable(SQLiteOpenHelper dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void createTable(SQLiteDatabase db) {
        String createCompanyTable = " CREATE TABLE " + TABLE_COMPANY_MASTER + " ( " +
                COL_ROW_ID + " INTEGER, " +
                COL_COMPANY_NAME + " TEXT ) ";
        db.execSQL(createCompanyTable);
    }

    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the table if already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY_MASTER);
        // Create table again
        createTable(db);
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(TABLE_COMPANY_MASTER,null,null);
        db.close();
    }

    public void addCompany(Company company) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_ROW_ID, company.getId());
        values.put(COL_COMPANY_NAME, company.getName());

        db.insert(TABLE_COMPANY_MASTER, null, values);

        db.close();
    }

    public String getTextFromId(int id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql;
        String text = "" ;
        sql = "select "
                + COL_COMPANY_NAME
                + " from " + TABLE_COMPANY_MASTER
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

    public ArrayList<Company> getCompanies() {
        ArrayList<Company> arrayList = new ArrayList<>();

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql;

        sql = "select "
                + COL_ROW_ID + " , "
                + COL_COMPANY_NAME
                + " from " + TABLE_COMPANY_MASTER +
                " order by " + COL_COMPANY_NAME;

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Company company = new Company(
                    cursor.getString(0),
                    cursor.getString(1));
            arrayList.add(company);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return arrayList;
    }
}
