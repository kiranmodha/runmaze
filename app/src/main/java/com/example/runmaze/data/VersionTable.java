package com.example.runmaze.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.runmaze.data.model.MasterDataVersion;

public class VersionTable {
    private static final String TABLE_MASTER_DATA_VERSIONS = "master_data_versions";
    private static final String COL_MASTER_DATA = "master_data";
    private static final String COL_VERSION_NUMBER = "version_number";

    private final SQLiteOpenHelper dbHandler;

    public VersionTable(SQLiteOpenHelper dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void createTable(SQLiteDatabase db) {
        String createVersionTable = " CREATE TABLE " + TABLE_MASTER_DATA_VERSIONS + " ( " +
                COL_MASTER_DATA + " TEXT, " +
                COL_VERSION_NUMBER + " REAL ) ";
        db.execSQL(createVersionTable);
//        addVersion(new MasterDataVersion("city", 0));
//        addVersion(new MasterDataVersion("club", 0));
//        addVersion(new MasterDataVersion("company", 0));

        addDefaultData(db);

    }

    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the table if already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER_DATA_VERSIONS);
        // Create table again
        createTable(db);
    }


    public void addOrUpdateMasterDataVersion(MasterDataVersion masterDataVersion) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql = "select * from  " + TABLE_MASTER_DATA_VERSIONS
                + " where " + COL_MASTER_DATA + " = '"
                + masterDataVersion.getMasterData() + "'";

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            cursor.close();
  //          db.close();
            updateVersion(masterDataVersion);
        } else {
            cursor.close();
  //          db.close();
            addVersion(masterDataVersion);
        }
    }

   void addDefaultData(SQLiteDatabase db)
   {
       ContentValues values = new ContentValues();

       values.put(COL_MASTER_DATA, "city");
       values.put(COL_VERSION_NUMBER, 0);
       db.insert(TABLE_MASTER_DATA_VERSIONS, null, values);

       values.put(COL_MASTER_DATA, "club");
       values.put(COL_VERSION_NUMBER, 0);
       db.insert(TABLE_MASTER_DATA_VERSIONS, null, values);

       values.put(COL_MASTER_DATA, "company");
       values.put(COL_VERSION_NUMBER, 0);
       db.insert(TABLE_MASTER_DATA_VERSIONS, null, values);
   }



    void addVersion(MasterDataVersion masterDataVersion) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_MASTER_DATA, masterDataVersion.getMasterData());
        values.put(COL_VERSION_NUMBER, masterDataVersion.getVersionNumber());

        db.insert(TABLE_MASTER_DATA_VERSIONS, null, values);

   //     db.close();
    }

    @SuppressLint("DefaultLocale")
    void updateVersion(MasterDataVersion masterDataVersion) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_VERSION_NUMBER, masterDataVersion.getVersionNumber());

        db.update(TABLE_MASTER_DATA_VERSIONS, values, COL_MASTER_DATA + " = '" + masterDataVersion.getMasterData() + "'", null);
    //    db.close();
    }

    public MasterDataVersion getVersion(String data) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql = "select " + COL_VERSION_NUMBER
                + " from  " + TABLE_MASTER_DATA_VERSIONS
                + " where " + COL_MASTER_DATA + " = '" + data + "'";

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        MasterDataVersion masterDataVersion = null;
        if (!cursor.isAfterLast()) {
            masterDataVersion = new MasterDataVersion(data, cursor.getFloat(0));
        }
        cursor.close();
   //     db.close();
        return masterDataVersion;
    }

}
