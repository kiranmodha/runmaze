package com.example.runmaze;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.utils.Settings;
import com.example.runmaze.data.model.Workout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class RecentActivitiesFragment extends Fragment {

    DatabaseHandler db;
    ImageView recentFragment_image1, recentFragment_image2;
    TextView recentFragment_distance1, recentFragment_distance2;
    TextView recentFragment_duration1,recentFragment_duration2;
    TextView recentFragment_date1,recentFragment_date2, recentFragment_pace1, recentFragment_pace2;
    TextView txtRecentActivities,txtShowMore;
    TextView txtAddActivityManually;
    TextView txtLeaderboard;
    TextView txtHDCLeaderboard;
    ConstraintLayout constraintLayout;

    Settings settings;
    int mAthleteId;

    public RecentActivitiesFragment() {
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
        db = new DatabaseHandler(getContext());
        View fragmentView =  inflater.inflate(R.layout.fragment_recent_activities, container, false);

        recentFragment_image1 = fragmentView.findViewById(R.id.recentFragment_image1);
        recentFragment_image2 = fragmentView.findViewById(R.id.recentFragment_image2);

        recentFragment_distance1 = fragmentView.findViewById(R.id.recentFragment_distance1);
        recentFragment_distance2 = fragmentView.findViewById(R.id.recentFragment_distance2);

        recentFragment_duration1 = fragmentView.findViewById(R.id.recentFragment_duration1);
        recentFragment_duration2 = fragmentView.findViewById(R.id.recentFragment_duration2);

        recentFragment_pace1 = fragmentView.findViewById(R.id.recentFragment_pace1);
        recentFragment_pace2 = fragmentView.findViewById(R.id.recentFragment_pace2);

        recentFragment_date1 = fragmentView.findViewById(R.id.recentFragment_date1);
        recentFragment_date2 = fragmentView.findViewById(R.id.recentFragment_date2);

        txtRecentActivities = fragmentView.findViewById(R.id.txtRecentActivities);
        txtShowMore = fragmentView.findViewById(R.id.txtShowMore);

        txtAddActivityManually = fragmentView.findViewById(R.id.txtAddActivityManually);
        txtLeaderboard = fragmentView.findViewById(R.id.txtLeaderboard);

        txtHDCLeaderboard = fragmentView.findViewById(R.id.txtHDCLeaderboard);


        constraintLayout = fragmentView.findViewById(R.id.recentFragment_constraintLayout);

       // settings = new Settings(getContext());
      //  mAthleteId = settings.getAthleteId();

        txtShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleFragment parentFragment = (TitleFragment) getParentFragment();
                Navigation.findNavController(parentFragment.getView()).navigate(R.id.action_titleFragment_to_listFragment);
             }
        });

        txtAddActivityManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleFragment parentFragment = (TitleFragment) getParentFragment();
                Navigation.findNavController(parentFragment.getView()).navigate(R.id.action_titleFragment_to_workoutFragment);
            }
        });

        txtLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleFragment parentFragment = (TitleFragment) getParentFragment();
                Navigation.findNavController(parentFragment.getView()).navigate(R.id.action_titleFragment_to_leaderboardFragment);
            }
        });

        txtHDCLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleFragment parentFragment = (TitleFragment) getParentFragment();
                Navigation.findNavController(parentFragment.getView()).navigate(R.id.action_titleFragment_to_hdcFragment);
            }
        });

        recentFragment_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id",view.getTag().toString());
                Navigation.findNavController(fragmentView).navigate(R.id.action_titleFragment_to_workoutFragment,bundle);
            }
        });

        recentFragment_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id",view.getTag().toString());
                Navigation.findNavController(fragmentView).navigate(R.id.action_titleFragment_to_workoutFragment,bundle);
            }
        });

        return fragmentView;
    }


    public void show()
    {
        settings = new Settings(getContext());

        if (!settings.isShowHdcLeaderboard()) {
            txtHDCLeaderboard.setVisibility(View.GONE);
        }

        mAthleteId = settings.getAthleteId();
        if (mAthleteId == 0) return;
        displayWorkouts();
    }

    void displayWorkouts() {

        ArrayList<Workout> arrayList;
        arrayList = new ArrayList<>(db.workoutTable.getWorkouts(2,mAthleteId));

        Workout workout;
        workout = getWorkout(arrayList,0);
        if (workout != null) {
            recentFragment_image1.setImageResource(ImageResourceId(workout));
            recentFragment_image1.setTag(workout.getId());
            recentFragment_distance1.setText(String.format("%.2f",workout.getDistance()));
            recentFragment_duration1.setText(String.format("%02d:%02d:%02d", workout.getHH(), workout.getMM(), workout.getSS()));
            recentFragment_date1.setText(formatDateTimeToLocalTimeZone(workout.getDateTime()));
            recentFragment_pace1.setText(workout.getPace());

            recentFragment_image1.setVisibility(View.VISIBLE);
            recentFragment_distance1.setVisibility(View.VISIBLE);
            recentFragment_duration1.setVisibility(View.VISIBLE);
            recentFragment_date1.setVisibility(View.VISIBLE);
            recentFragment_pace1.setVisibility(View.VISIBLE);
            txtShowMore.setVisibility(View.VISIBLE);
            txtRecentActivities.setText("RECENT ACTIVITIES");
        } else {

            // Specifically used GONE instead of INVISIBLE
            recentFragment_image1.setVisibility(View.GONE);
            recentFragment_distance1.setVisibility(View.GONE);
            recentFragment_duration1.setVisibility(View.GONE);
            recentFragment_date1.setVisibility(View.GONE);
            recentFragment_pace1.setVisibility(View.GONE);

            recentFragment_image2.setVisibility(View.GONE);
            recentFragment_distance2.setVisibility(View.GONE);
            recentFragment_duration2.setVisibility(View.GONE);
            recentFragment_date2.setVisibility(View.GONE);
            recentFragment_pace2.setVisibility(View.GONE);

            // Following code is not required when used GONE instead of INVISIBLE
/*            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(R.id.txtAddActivityManually,ConstraintSet.TOP,R.id.txtRecentActivities,ConstraintSet.BOTTOM);
            constraintSet.applyTo(constraintLayout);*/

            txtShowMore.setVisibility(View.INVISIBLE);
            txtRecentActivities.setText("NO ACTIVITIES YET");
            return;
        }



        workout = getWorkout(arrayList,1);
        if (workout != null) {
            recentFragment_image2.setImageResource(ImageResourceId(workout) );
            recentFragment_image2.setTag(workout.getId());
            recentFragment_distance2.setText(String.format("%.2f",workout.getDistance()));
            recentFragment_duration2.setText(String.format("%02d:%02d:%02d", workout.getHH(), workout.getMM(), workout.getSS()));
            recentFragment_date2.setText(formatDateTimeToLocalTimeZone(workout.getDateTime()));;
            recentFragment_pace2.setText(workout.getPace());

            recentFragment_image2.setVisibility(View.VISIBLE);
            recentFragment_distance2.setVisibility(View.VISIBLE);
            recentFragment_duration2.setVisibility(View.VISIBLE);
            recentFragment_date2.setVisibility(View.VISIBLE);
            recentFragment_pace2.setVisibility(View.VISIBLE);
        } else {
            // Specifically used GONE instead of INVISIBLE
            recentFragment_image2.setVisibility(View.GONE);
            recentFragment_distance2.setVisibility(View.GONE);
            recentFragment_duration2.setVisibility(View.GONE);
            recentFragment_date2.setVisibility(View.GONE);
            recentFragment_pace2.setVisibility(View.GONE);


           // Following code is not required when used GONE instead of INVISIBLE
/*            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(R.id.txtAddActivityManually,ConstraintSet.TOP,R.id.recentFragment_duration1,ConstraintSet.BOTTOM);
            constraintSet.applyTo(constraintLayout);*/

            return;
        }

    }

    int ImageResourceId(Workout workout) {
        int ResId = 0;
        switch (workout.getActivityType()) {
            case "Run":
                ResId =  R.drawable.run;
                break;
            case "Ride":
                ResId = R.drawable.ride;
                break;
            case "Swim":
                ResId = R.drawable.swim;
                break;
            case "Walk":
                ResId = R.drawable.walk;
                break;
            case "Yoga":
                ResId = R.drawable.yoga;
                break;
        }
        return ResId;
    }


    //TODO same function also exists in WorkoutRecyclerAdapter - need to move it in common util class
    public String formatDateTimeToLocalTimeZone(String inputDateTimeGMT) {
        // Following is the data taken from the database
        //String datetimeString = "27/12/2021 21:42 GMT";
        Date theDate = null;
        //  DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm z");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            // Convert string into Date
            theDate = df.parse(inputDateTimeGMT);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }



        DateFormat df1 = new SimpleDateFormat("EEE, dd/MM/yyyy HH:mm");
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        df1.setTimeZone(tz);
        String dateString = df1.format(theDate);

        return dateString;
    }

    Workout getWorkout(ArrayList<Workout> arrayList, int i)
    {
        Workout workout;
        try {
            workout = arrayList.get(i);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return workout;
    }



}