package com.example.runmaze;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.utils.Settings;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;


public class StatisticsFragment extends Fragment {

    Settings settings;
    int mAthleteId;

    String activity_type = "Run";
    RadioGroup rgActivityType;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_statistics, container, false);
        //SharedPreferences settings = getContext().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        //mAthleteId = settings.getInt("id", 0);

        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();

        rgActivityType = (RadioGroup) fragmentView.findViewById(R.id.rgActivityType);

        rgActivityType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) fragmentView.findViewById(checkedId);
                activity_type = rb.getText().toString();
                showStatistics(fragmentView);
            }
        });

        showStatistics(fragmentView);
        return  fragmentView;
    }


    void showStatistics(@NotNull View view)
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        TextView longest_distance = view.findViewById(R.id.statsFragment_longest_distance);
        TextView fullMarathons = view.findViewById(R.id.statsFragment_FullMarathon);
        TextView halfMarathons = view.findViewById(R.id.statsFragment_HalfMarathons);
        TextView run10K = view.findViewById(R.id.statsFragment_10KRun);
        TextView run5K = view.findViewById(R.id.statsFragment_5KRun);

        TextView txtFullMarathons = view.findViewById(R.id.statsFragment_txtFullMarathon);
        TextView txtHalfMarathons = view.findViewById(R.id.statsFragment_txtHalfMarathons);
        TextView txtRun10K = view.findViewById(R.id.statsFragment_txt10KRun);
        TextView txtRun5K = view.findViewById(R.id.statsFragment_txt5KRun);

        TextView activities = view.findViewById(R.id.statsFragment_activities);
        TextView distance = view.findViewById(R.id.statsFragment_distance);
        TextView duration = view.findViewById(R.id.statsFragment_duration);
        TextView pace = view.findViewById(R.id.statsFragment_pace);
        TextView speed = view.findViewById(R.id.statsFragment_speed);
        ImageView imgView = view.findViewById(R.id.statsFragment_activity_image);

        txtFullMarathons.setVisibility(View.VISIBLE);
        txtHalfMarathons.setVisibility(View.VISIBLE);
        txtRun10K.setVisibility(View.VISIBLE);
        txtRun5K.setVisibility(View.VISIBLE);
        fullMarathons.setVisibility(View.VISIBLE);
        halfMarathons.setVisibility(View.VISIBLE);
        run10K.setVisibility(View.VISIBLE);
        run5K.setVisibility(View.VISIBLE);

        switch (activity_type) {
            case "Run" :
               // longest_distance.setText(String.format("%.2f km",dbHandler.workoutTable.getRunStatistics(1, mAthleteId)));
                fullMarathons.setText(String.format("%.0f",dbHandler.workoutTable.getRunStatistics(2 , mAthleteId)));
                halfMarathons.setText(String.format("%.0f",dbHandler.workoutTable.getRunStatistics(3 , mAthleteId)));
                run10K.setText(String.format("%.0f",dbHandler.workoutTable.getRunStatistics(4 , mAthleteId)));
                run5K.setText(String.format("%.0f",dbHandler.workoutTable.getRunStatistics(5 , mAthleteId)));
                imgView.setImageResource(R.drawable.run);
                txtFullMarathons.setText("Full Marathons");
                txtHalfMarathons.setText("Half Marathons");
                txtRun10K.setText("10 km Runs");
                txtRun5K.setText("5 Km Runs");
                break;
            case "Ride" :
               // longest_distance.setText(String.format("%.2f km",dbHandler.workoutTable.getRideStatistics(1, mAthleteId)));
                fullMarathons.setText(String.format("%.0f",dbHandler.workoutTable.getRideStatistics(2 , mAthleteId)));
                halfMarathons.setText(String.format("%.0f",dbHandler.workoutTable.getRideStatistics(3 , mAthleteId)));
                run10K.setText(String.format("%.0f",dbHandler.workoutTable.getRideStatistics(4 , mAthleteId)));
                run5K.setText(String.format("%.0f",dbHandler.workoutTable.getRideStatistics(5 , mAthleteId)));
                txtFullMarathons.setText("100 Km Rides :");
                txtHalfMarathons.setText("75 km Rides :");
                txtRun10K.setText("50 km Rides :");
                txtRun5K.setText("25 km Rides :");
                imgView.setImageResource(R.drawable.ride);
                break;
            case "Walk" :
                txtFullMarathons.setVisibility(View.INVISIBLE);
                txtHalfMarathons.setVisibility(View.INVISIBLE);
                txtRun10K.setVisibility(View.INVISIBLE);
                txtRun5K.setVisibility(View.INVISIBLE);
                fullMarathons.setVisibility(View.INVISIBLE);
                halfMarathons.setVisibility(View.INVISIBLE);
                run10K.setVisibility(View.INVISIBLE);
                run5K.setVisibility(View.INVISIBLE);
                imgView.setImageResource(R.drawable.walk);
                break;
        }

        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String sql = "select count(*) activities, max(distance) max_distance, sum(distance) distance, sum(duration_hh) hours, sum(duration_mm) minutes, sum(duration_ss) seconds  from workout " +
                " where athlete_id = " + mAthleteId
                +  " and activity_type =  '" + activity_type + "' ";

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int countActivities = 0;
        float sumDistance = 0f;
        int sumHours = 0;
        int sumMinutes = 0;
        int sumSeconds = 0;

        if (null != cursor) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                countActivities = cursor.getInt(cursor.getColumnIndex("activities"));
                sumDistance = cursor.getFloat(cursor.getColumnIndex("distance"));
                sumHours = cursor.getInt(cursor.getColumnIndex("hours"));
                sumMinutes = cursor.getInt(cursor.getColumnIndex("minutes"));
                sumSeconds = cursor.getInt(cursor.getColumnIndex("seconds"));

                longest_distance.setText(String.format("%.2f km",cursor.getFloat(cursor.getColumnIndex("max_distance"))));

            }
        }
        cursor.close();
        dbHandler.close();

        activities.setText(String.format("%d", countActivities ));

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        distance.setText(df.format(sumDistance) + " km");

        sumMinutes = sumMinutes + (sumSeconds / 60);
        sumSeconds = sumSeconds % 60;
        sumHours = sumHours + (sumMinutes / 60);
        sumMinutes = sumMinutes % 60;

        duration.setText(String.format("%d h %d min", sumHours, sumMinutes));

        float totalTime = (sumHours + sumMinutes/60f + sumSeconds/3600f);

        float paceTotal = totalTime / sumDistance * 60;
        int paceMin = (int) paceTotal;
        int paceSec = (int)((paceTotal - paceMin) * 60);
        pace.setText(String.format("%d:%02d min/km", paceMin, paceSec));

        speed.setText(String.format("%.2f km/h", sumDistance / totalTime));

    }

}