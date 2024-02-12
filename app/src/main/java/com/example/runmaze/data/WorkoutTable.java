package com.example.runmaze.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.runmaze.data.model.Athlete;
import com.example.runmaze.data.model.Workout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WorkoutTable {
    private static final String TABLE_WORKOUT = "workout";
    private static final String COL_WORKOUT_DATE = "workout_date";
    private static final String COL_ID = "id";
    private static final String COL_ATHLETE_ID = "athlete_id";
    private static final String COL_DISTANCE = "distance";
    private static final String COL_ACTIVITY_TYPE = "activity_type";
    private static final String COL_DURATION_HH = "duration_hh";
    private static final String COL_DURATION_MM = "duration_mm";
    private static final String COL_DURATION_SS = "duration_ss";
    private static final String COL_LINK = "link";
    private static final String COL_REMOTE_UPDATE = "remote_update";


    private final SQLiteOpenHelper dbHandler;

    public WorkoutTable(SQLiteOpenHelper dbHandler) {
        this.dbHandler = dbHandler;
    }

    void createTable(SQLiteDatabase db) {
        String createWorkoutTable = " CREATE TABLE " + TABLE_WORKOUT + " ( " +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ATHLETE_ID + " INTEGER, " +
                COL_WORKOUT_DATE + " TEXT, " +
                COL_ACTIVITY_TYPE + " TEXT, " +
                COL_DISTANCE + " REAL, " +
                COL_DURATION_HH + " INTEGER, " +
                COL_DURATION_MM + " INTEGER, " +
                COL_DURATION_SS + " INTEGER, " +
                COL_LINK + " TEXT,  " +
                COL_REMOTE_UPDATE + " INTEGER )";
        db.execSQL(createWorkoutTable);
    }

    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the table if already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        // Create table again
        createTable(db);
    }

    public void alterTable(SQLiteDatabase db) {

        db.execSQL("ALTER TABLE  " + TABLE_WORKOUT + " ADD COLUMN " + COL_REMOTE_UPDATE + " INTEGER DEFAULT 0");

    }


    public boolean addWorkoutIfNotExists(Workout workout) {
        boolean success = false;
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_WORKOUT + "( "
                + COL_ATHLETE_ID + ","
                + COL_WORKOUT_DATE + ","
                + COL_DISTANCE + ","
                + COL_ACTIVITY_TYPE + ","
                + COL_DURATION_HH + ","
                + COL_DURATION_MM + ","
                + COL_DURATION_SS + ","
                + COL_LINK + ","
                + COL_REMOTE_UPDATE
                + ") SELECT "
                + workout.getAthleteId() + ","
                + "'" +  workout.getDateTime() + "',"
                + workout.getDistance() + ","
                + "'" + workout.getActivityType() + "',"
                + workout.getHH() + ","
                + workout.getMM() + ","
                + workout.getSS() + ","
                + "'" + workout.getLink() + "', "
                + workout.getRemote_update()
                + " WHERE NOT EXISTS(SELECT 1 FROM " + TABLE_WORKOUT
                + " WHERE " + COL_ATHLETE_ID + " = " + workout.getAthleteId() + " and " + COL_WORKOUT_DATE + " = '" + workout.getDateTime() + "')";
        try {
            // TODO use db.rawQuery() to check whether record is inserted and accordingly return true or false value
            //db.execSQL(sql);
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                success = true;
            }
            cursor.close();
        } catch (Exception e) {
            //return false;
        }
        return success;
    }

    public void addWorkout(Workout workout) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_ATHLETE_ID, workout.getAthleteId());
        values.put(COL_WORKOUT_DATE, workout.getDateTime());
        values.put(COL_DISTANCE, workout.getDistance());
        values.put(COL_ACTIVITY_TYPE, workout.getActivityType());
        values.put(COL_DURATION_HH, workout.getHH());
        values.put(COL_DURATION_MM, workout.getMM());
        values.put(COL_DURATION_SS, workout.getSS());
        values.put(COL_LINK, workout.getLink());
        values.put(COL_REMOTE_UPDATE, workout.getRemote_update());
        db.insert(TABLE_WORKOUT, null, values);
        db.close();
    }

    @SuppressLint("DefaultLocale")
    public void updateWorkout(Workout workout) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_ATHLETE_ID, workout.getAthleteId());
        values.put(COL_WORKOUT_DATE, workout.getDateTime());
        values.put(COL_DISTANCE, workout.getDistance());
        values.put(COL_ACTIVITY_TYPE, workout.getActivityType());
        values.put(COL_DURATION_HH, workout.getHH());
        values.put(COL_DURATION_MM, workout.getMM());
        values.put(COL_DURATION_SS, workout.getSS());
        values.put(COL_LINK, workout.getLink());
        values.put(COL_REMOTE_UPDATE, workout.getRemote_update());
        db.update(TABLE_WORKOUT, values, String.format(COL_ID + " = %d", workout.getId()), null);
        db.close();
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(TABLE_WORKOUT, null, null);
        db.close();
    }

    @SuppressLint("DefaultLocale")
    public void deleteWorkout(int id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        if (id != -1) {
            // delete specific record with id
            db.delete(TABLE_WORKOUT, String.format(COL_ID + " = %d", id), null);
        } else {
            // delete all records
            db.delete(TABLE_WORKOUT, null, null);
        }
        db.close();
    }

    @SuppressLint("DefaultLocale")
    public void updateRemoteUpdateStatus(Workout workout) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_REMOTE_UPDATE, workout.getRemote_update());
        db.update(TABLE_WORKOUT, values, String.format(COL_ID + " = %d", workout.getId()), null);
        db.close();
    }


    // Returns all activities
    public int getNumberOfActivities(int athleteId, String activityType) {
        return getNumberOfActivities(athleteId, activityType, null);
    }

    // Returns activities for particular duration
    public int getNumberOfActivities(int athleteId, String activityType, String durationFilter) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String sql = "select count(*)  from " + TABLE_WORKOUT +
                " where " + COL_ATHLETE_ID + " = " + athleteId
                +   " and " + COL_ACTIVITY_TYPE + " = '" + activityType + "' ";

        if (durationFilter!= null)
        {
            sql = sql + " and " + durationFilter;
        }

        Cursor cursor = db.rawQuery(sql , null);
        cursor.moveToFirst();
        int recCount = 0;

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                recCount = cursor.getInt(0);
            }
        }
        cursor.close();
        db.close();
        return recCount;
    }

    // Returns total distance for all activities
    public float getTotalDistance(int athleteId,String activityType) {
        return getTotalDistance(athleteId,activityType,null);
    }

    // returns total distance for activities in particular duration
    public float getTotalDistance(int athleteId,String activityType, String durationFilter) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String sql = "select sum(" + COL_DISTANCE + ")  from " + TABLE_WORKOUT +
                " where " + COL_ATHLETE_ID + " = " + athleteId
                +   " and " + COL_ACTIVITY_TYPE + " = '" + activityType + "' ";

        if (durationFilter!= null)
        {
            sql = sql + " and " + durationFilter;
        }

        Cursor cursor = db.rawQuery(sql , null);
        cursor.moveToFirst();
        float sumDistance = 0.0f;

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                sumDistance = cursor.getFloat(0);
            }
        }
        cursor.close();
        db.close();
        return sumDistance;
    }


    public long getTotalDuration(int athleteId, String activityType)
    {
        return getTotalDuration(athleteId,activityType,null);
    }

    @SuppressLint("DefaultLocale")
    public long getTotalDuration(int athleteId, String activityType , String durationFilter) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String sql =  "select  sum(" + COL_DURATION_HH + ") as HOURS ," +
                " sum(" + COL_DURATION_MM + ") as MINUTES, " +
                " sum(" + COL_DURATION_SS + ") as SECONDS " +
                " from " + TABLE_WORKOUT +
                " where " + COL_ATHLETE_ID + " = " + athleteId
                +   " and " + COL_ACTIVITY_TYPE + " = '" + activityType + "' ";

        if (durationFilter!= null)
        {
            sql = sql + " and " + durationFilter;
        }

        Cursor cursor = db.rawQuery(sql , null);
        cursor.moveToFirst();
        long sumHours = 0, sumMinutes = 0, sumSeconds = 0;

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                sumHours = cursor.getLong(0);
                sumMinutes = cursor.getLong(1);
                sumSeconds = cursor.getLong(2);
            }
        }
        cursor.close();

        return (sumHours * 3600 + sumMinutes * 60 + sumSeconds);
    }



    public float getRunStatistics(int statType, int athleteId) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql = "";
        switch (statType) {
            case 1:  // longest distance in running
                sql = "select max(" + COL_DISTANCE + ")  from " + TABLE_WORKOUT
                                + " where " + COL_ACTIVITY_TYPE + " = 'Run' and "
                                + COL_ATHLETE_ID + " = " + athleteId;
                break;
            case 2: // Number of full marathons
                sql = "select count(*)  from " + TABLE_WORKOUT
                        + " where " + COL_DISTANCE + " >= 42.2 and " + COL_ACTIVITY_TYPE + " = 'Run' and "
                        + COL_ATHLETE_ID + " = " + athleteId;
                break;
            case 3: // Number of half marathons
                sql = "select count(*)  from " + TABLE_WORKOUT
                        + " where " + COL_DISTANCE + " >= 21.1 and " + COL_DISTANCE + " < 42.2 and "
                        + COL_ACTIVITY_TYPE + " = 'Run' and "
                        + COL_ATHLETE_ID + " = " + athleteId;
                break;
            case 4: // Number of 10K running
                sql = "select count(*)  from " + TABLE_WORKOUT
                        + " where " + COL_DISTANCE + " >= 10 and " + COL_DISTANCE + " < 21.1 and "
                        + COL_ACTIVITY_TYPE + " = 'Run' and "
                        + COL_ATHLETE_ID + " = " + athleteId;
                break;
            case 5: // Number of 5K running
                sql = "select count(*)  from " + TABLE_WORKOUT
                        + " where " + COL_DISTANCE + " >= 5 and " + COL_DISTANCE + " < 10 and "
                        + COL_ACTIVITY_TYPE + " = 'Run' and "
                        + COL_ATHLETE_ID + " = " + athleteId;
                break;

        }
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        float stats = 0.0f;

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                stats = cursor.getFloat(0);
            }
        }
        cursor.close();
        db.close();
        return stats;
    }


    public float getRideStatistics(int statType, int athleteId) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql = "";
        switch (statType) {
            case 1:  // longest distance in ride
                sql = "select max(" + COL_DISTANCE + ")  from " + TABLE_WORKOUT
                        + " where " + COL_ACTIVITY_TYPE + " = 'Ride' and "
                        + COL_ATHLETE_ID + " = " + athleteId;
                break;
            case 2: // Number of 100+ km
                sql = "select count(*)  from " + TABLE_WORKOUT
                        + " where " + COL_DISTANCE + " >= 100 and " + COL_ACTIVITY_TYPE + " = 'Ride' and "
                        + COL_ATHLETE_ID + " = " + athleteId;
                break;
            case 3: // Number of 75+ km
                sql = "select count(*)  from " + TABLE_WORKOUT
                        + " where " + COL_DISTANCE + " >= 75 and " + COL_DISTANCE + " < 100 and "
                        + COL_ACTIVITY_TYPE + " = 'Ride' and "
                        + COL_ATHLETE_ID + " = " + athleteId;
                break;
            case 4: // Number of 50+ km
                sql = "select count(*)  from " + TABLE_WORKOUT
                        + " where " + COL_DISTANCE + " >= 50 and " + COL_DISTANCE + " < 75 and "
                        + COL_ACTIVITY_TYPE + " = 'Ride' and "
                        + COL_ATHLETE_ID + " = " + athleteId;
                break;
            case 5: // Number of 25+ km
                sql = "select count(*)  from " + TABLE_WORKOUT
                        + " where " + COL_DISTANCE + " >= 25 and " + COL_DISTANCE + " < 50 and "
                        + COL_ACTIVITY_TYPE + " = 'Ride' and "
                        + COL_ATHLETE_ID + " = " + athleteId;
                break;

        }
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        float stats = 0.0f;

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                stats = cursor.getFloat(0);
            }
        }
        cursor.close();
        db.close();
        return stats;
    }



    public ArrayList<MonthDistance> getMonthlyDistance(int athleteId ,String activityType) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select sum(" + COL_DISTANCE + ") distance, "
                + " strftime('%Y-%m', datetime(" + COL_WORKOUT_DATE + ",'localtime')) year_month,  "
                + " strftime('%Y', datetime(" + COL_WORKOUT_DATE + ",'localtime')) year,  "
                + " strftime('%m', datetime(" + COL_WORKOUT_DATE + ",'localtime')) month  "
                + " from " + TABLE_WORKOUT
                +  " where " + COL_ATHLETE_ID + " = " + athleteId
                +   " and " + COL_ACTIVITY_TYPE + " = '" + activityType + "' "
                + " GROUP BY year_month ORDER BY year_month", null);

/*        Cursor cursor = db.rawQuery("select sum(" + COL_DISTANCE + ") distance, "
                + " strftime('%Y-%m', " + COL_WORKOUT_DATE + ") year_month,  "
                + " strftime('%Y', " + COL_WORKOUT_DATE + ") year,  "
                + " strftime('%m', " + COL_WORKOUT_DATE + ") month  "
                + " from " + TABLE_WORKOUT
                +  " where " + COL_ATHLETE_ID + " = " + athleteId
                +   " and " + COL_ACTIVITY_TYPE + " = '" + activityType + "' "
                + " GROUP BY year_month ORDER BY year_month", null);*/

        //+ " GROUP BY year_month ORDER BY year_month DESC LIMIT 12", null);
        cursor.moveToFirst();

        ArrayList<MonthDistance> arrayList = new ArrayList<>();

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    arrayList.add(new MonthDistance(cursor.getString(3), cursor.getString(2), cursor.getFloat(0)));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        //Collections.reverse(arrayList);
        return arrayList;
    }



    public ArrayList<MonthDistance> getDailyDistance(int athleteId,String yearMonth, String activityType) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

       String sql = " SELECT  d.DAY,  sum(w." + COL_DISTANCE + ") distance " +
                    "  FROM DAYS d " +
                    " LEFT JOIN " + TABLE_WORKOUT + " w ON d.DAY = strftime('%d', datetime(w." + COL_WORKOUT_DATE + ",'localtime')) "+
                    " where " + COL_ATHLETE_ID + " = " + athleteId +
                    " and  strftime('%Y-%m', datetime(w." + COL_WORKOUT_DATE + ",'localtime'))" + " = '" + yearMonth + "' " +
                    " and " + COL_ACTIVITY_TYPE + " = '" + activityType + "' " +
                    "     group by day ";

/*        String sql = " SELECT  d.DAY,  sum(w." + COL_DISTANCE + ") distance " +
                "  FROM DAYS d " +
                " LEFT JOIN " + TABLE_WORKOUT + " w ON d.DAY = strftime('%d', w." + COL_WORKOUT_DATE + ") "+
                " where " + COL_ATHLETE_ID + " = " + athleteId +
                " and  strftime('%Y-%m', w." + COL_WORKOUT_DATE + ")" + " = '" + yearMonth + "' " +
                " and " + COL_ACTIVITY_TYPE + " = '" + activityType + "' " +
                "     group by day ";*/

        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();

        ArrayList<MonthDistance> arrayList = new ArrayList<>();

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    arrayList.add(new MonthDistance(cursor.getString(0), "", cursor.getFloat(1)));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        //Collections.reverse(arrayList);
        return arrayList;
    }



    @SuppressLint("DefaultLocale")
    public String getTotalHoursMinutes(int athleteId, String activityType) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select  sum(" + COL_DURATION_HH + ") as HOURS ," +
                " sum(" + COL_DURATION_MM + ") as MINUTES, " +
                " sum(" + COL_DURATION_SS + ") as SECONDS " +
                " from " + TABLE_WORKOUT +
                " where " + COL_ATHLETE_ID + " = " + athleteId
                +   " and " + COL_ACTIVITY_TYPE + " = '" + activityType + "' ", null);
        cursor.moveToFirst();
        long sumHours = 0, sumMinutes = 0, sumSeconds = 0;

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                sumHours = cursor.getLong(0);
                sumMinutes = cursor.getLong(1);
                sumSeconds = cursor.getLong(2);
            }
        }
        cursor.close();
        sumMinutes = sumMinutes + (sumSeconds / 60);
        //sumSeconds = sumSeconds % 60;
        sumHours = sumHours + (sumMinutes / 60);
        sumMinutes = sumMinutes % 60;
        db.close();
        return String.format("%d h %d min", sumHours, sumMinutes);
    }


    public ArrayList<Workout> getWorkouts(int limit, int athleteId) {
        ArrayList<Workout> arrayList = new ArrayList<>();

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql;
        if (limit == -1) {
            sql = "select "
                    + COL_ID + " , "
                    + COL_WORKOUT_DATE + " , "
                    + COL_ACTIVITY_TYPE + " , "
                    + COL_DISTANCE + " , "
                    + COL_DURATION_HH + " , "
                    + COL_DURATION_MM + " , "
                    + COL_DURATION_SS + " , "
                    + COL_LINK + " , "
                    + COL_ATHLETE_ID + " , "
                    + COL_REMOTE_UPDATE
                    + " from " + TABLE_WORKOUT +
                    " where " + COL_ATHLETE_ID + " = " + athleteId +
                    " order by " + COL_WORKOUT_DATE + " desc ";
        } else {
            sql = "select "
                    + COL_ID + " , "
                    + COL_WORKOUT_DATE + " , "
                    + COL_ACTIVITY_TYPE + " , "
                    + COL_DISTANCE + " , "
                    + COL_DURATION_HH + " , "
                    + COL_DURATION_MM + " , "
                    + COL_DURATION_SS + " , "
                    + COL_LINK + " , "
                    + COL_ATHLETE_ID + " , "
                    + COL_REMOTE_UPDATE
                    + " from " + TABLE_WORKOUT +
                    " where " + COL_ATHLETE_ID + " = " + athleteId +
                    " order by " + COL_WORKOUT_DATE + " desc limit " + limit;
        }

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Workout workout = new Workout();
            workout.setId(cursor.getInt(0));
            workout.setDatetime(cursor.getString(1));
            workout.setActivityType(cursor.getString(2));
            workout.setDistance(cursor.getFloat(3));
            workout.setHH(cursor.getInt(4));
            workout.setMM(cursor.getInt(5));
            workout.setSS(cursor.getInt(6));
            workout.setLink(cursor.getString(7));
            workout.setAthleteId(cursor.getInt(8));
            workout.setRemote_update(cursor.getInt(9));
            arrayList.add(workout);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return arrayList;
    }


    public String getWorkoutsJSON(int limit, int athleteId, int remoteUpdate ) {
        //ArrayList<Workout> arrayList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql;

        sql = "select "
                + COL_ID + " , "
                + COL_WORKOUT_DATE + " , "
                + COL_ACTIVITY_TYPE + " , "
                + COL_DISTANCE + " , "
                + COL_DURATION_HH + " , "
                + COL_DURATION_MM + " , "
                + COL_DURATION_SS + " , "
                + COL_LINK + " , "
                + COL_ATHLETE_ID + " , "
                + COL_REMOTE_UPDATE
                + " from " + TABLE_WORKOUT +
                " where " + COL_ATHLETE_ID + " = " + athleteId;

        if (remoteUpdate != -1) {
            sql = sql + " and " + COL_REMOTE_UPDATE + " = " + remoteUpdate;
        }

        if (limit == -1) {
            sql  = sql + " order by " + COL_WORKOUT_DATE + " desc ";
        } else {
            sql  = sql + " order by " + COL_WORKOUT_DATE + " desc limit " +  limit;
        }


        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int recCount = 0;
        while (!cursor.isAfterLast()) {
            recCount++;
            Workout workout = new Workout();
            workout.setId(cursor.getInt(0));
            workout.setDatetime(cursor.getString(1));
            workout.setActivityType(cursor.getString(2));
            workout.setDistance(cursor.getFloat(3));
            workout.setHH(cursor.getInt(4));
            workout.setMM(cursor.getInt(5));
            workout.setSS(cursor.getInt(6));
            workout.setLink(cursor.getString(7));
            workout.setAthleteId(cursor.getInt(8));
            workout.setRemote_update(cursor.getInt(9));
            jsonArray.put(workout.getJSONObject());
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        if (recCount == 0) return "";

        return jsonArray.toString();
    }


    public Workout getWorkout(int id, int athleteId) {

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String sql = "select "
                + COL_ID + " , "
                + COL_WORKOUT_DATE + " , "
                + COL_ACTIVITY_TYPE + " , "
                + COL_DISTANCE + " , "
                + COL_DURATION_HH + " , "
                + COL_DURATION_MM + " , "
                + COL_DURATION_SS + " , "
                + COL_LINK + " , "
                + COL_ATHLETE_ID + " , "
                + COL_REMOTE_UPDATE
                + " FROM " + TABLE_WORKOUT
                + " WHERE " + COL_ID + " = " + id;


        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            Workout workout = new Workout();
            workout.setId(cursor.getInt(0));
            workout.setDatetime(cursor.getString(1));
            workout.setActivityType(cursor.getString(2));
            workout.setDistance(cursor.getFloat(3));
            workout.setHH(cursor.getInt(4));
            workout.setMM(cursor.getInt(5));
            workout.setSS(cursor.getInt(6));
            workout.setLink(cursor.getString(7));
            workout.setAthleteId(cursor.getInt(8));
            workout.setRemote_update(cursor.getInt(9));
            cursor.close();
            db.close();
            return (workout);
        }
        cursor.close();
        db.close();
        return null;
    }

    public class MonthDistance {
        public String Key;
        public String Month;
        public String Year;
        public float Distance;


        public MonthDistance(String month, String year, float distance) {
            Month = month;
            Year = year;
            Distance = distance;
            Key = year + "" + month;
        }
    }
}
