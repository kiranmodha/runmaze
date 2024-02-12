package com.example.runmaze;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.NonNull;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.utils.Settings;
import com.example.runmaze.data.model.Workout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {


    Button btnSave, btnDate, btnTime;
    EditText editDate, editTime, editDistance, editLink, editHH, editMM, editSS;
    Spinner spinActivityType;
    ImageView imageActivity;
    int mYear, mMonth, mDay, mHour, mMinute;

    private static final String ID = "id";

    private int recordId;
    private static int mode = 0;  //  0- Add Mode     1-Edit Mode

    Settings settings;
    int mAthleteId;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static WorkoutFragment newInstance(String id) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            recordId = Integer.parseInt(getArguments().getString(ID));
            //mode = Integer.parseInt(getArguments().getString(ID)); // Edit Mode
            mode = 1;
            setHasOptionsMenu(true);   // Delete option menu will be visible only in edit mode
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_workout, menu);
        // return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menuWorkoutDelete) {
            deleteWorkout();
        }
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_workout, container, false);

        btnSave = fragmentView.findViewById(R.id.btnSave);
        btnDate = fragmentView.findViewById(R.id.btnDate);
        btnTime = fragmentView.findViewById(R.id.btnTime);

        editDate = fragmentView.findViewById(R.id.editDate);
        editTime = fragmentView.findViewById(R.id.editTime);
        editDistance = fragmentView.findViewById(R.id.editDistance);
        spinActivityType = fragmentView.findViewById(R.id.spinActivities);
        editHH = fragmentView.findViewById(R.id.editHH);
        editMM = fragmentView.findViewById(R.id.editMM);
        editSS = fragmentView.findViewById(R.id.editSS);
        editLink = fragmentView.findViewById(R.id.editLink);
        imageActivity = fragmentView.findViewById(R.id.imageActivity);

        //SharedPreferences settings = getContext().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        //mAthleteId = settings.getInt("id", 0);

        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();

        // add button click listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });


        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });

        if (mode == 1) {
            Display();

        }

        return fragmentView;
    }


    @SuppressLint("DefaultLocale")
    void Display() {
        DatabaseHandler db = new DatabaseHandler(getContext());

        Workout workout = db.workoutTable.getWorkout(recordId, mAthleteId);

        editDistance.setText(String.format("%.2f", workout.getDistance()));
        editDate.setText(getFormattedDate(workout.getDateTime()));
        editTime.setText(getFormattedTime(workout.getDateTime()));
        editHH.setText(String.format("%d", workout.getHH()));
        editMM.setText(String.format("%d", workout.getMM()));
        editSS.setText(String.format("%d", workout.getSS()));
        editLink.setText(workout.getLink());
        spinActivityType.setSelection(getIndex(spinActivityType, workout.getActivityType()));


        switch (workout.getActivityType()) {
            case "Run":
                imageActivity.setImageResource(R.drawable.run);
                break;
            case "Ride":
                imageActivity.setImageResource(R.drawable.ride);
                break;
            case "Swim":
                imageActivity.setImageResource(R.drawable.swim);
                break;
            case "Walk":
                imageActivity.setImageResource(R.drawable.walk);
                break;
            case "Yoga":
                imageActivity.setImageResource(R.drawable.yoga);
                break;
        }

        // Initialize local variables for workout date and time to use as default values in date and time dialog
        Date date = null;
        DateFormat dfGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dfGMT.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            date = dfGMT.parse(workout.getDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);   // this will convert GMT into local datetime
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        // - Initialize local ....


        // if the record was imported from server
        if (workout.getRemote_update() == 2){
            btnSave.setEnabled(false);
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    void Save() {
        String _datetime;
        float _distance;
        int _HH;
        int _MM;
        int _SS;
        String _activityType;
        String _link;

        if (!validateData()) {
            return;
        }

        _datetime = getFormattedGMTDateTime(editDate.getText().toString(), editTime.getText().toString());
        _distance = getFloat(editDistance.getText().toString());
        _activityType = String.valueOf(spinActivityType.getSelectedItem());
        _HH = getIntegers(editHH.getText().toString());
        _MM = getIntegers(editMM.getText().toString());
        _SS = getIntegers(editSS.getText().toString());
        _link = editLink.getText().toString();
        DatabaseHandler db = new DatabaseHandler(getContext());

        // if Add Mode
        if (mode == 0) {
            Workout workout = new Workout(-1, mAthleteId, _datetime, _distance, _activityType, _HH, _MM, _SS, _link);
            db.workoutTable.addWorkout(workout);
        } else if (mode == 1) { //Edit Mode
            Workout workout = new Workout(recordId, mAthleteId, _datetime, _distance, _activityType, _HH, _MM, _SS, _link);
            db.workoutTable.updateWorkout(workout);
        }

        // Going back to calling screen
        Toast.makeText(getContext(), "Workout Saved Successfully", Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();

    }


    public boolean validateData() {
        if (editDate.length() == 0) {
            editDate.setError("Date is required");
            return false;
        }
        if (editTime.length() == 0) {
            editTime.setError("Time is required field");
            return false;
        }

        if (editDistance.length() == 0) {
            editDistance.setError("Please enter distance");
            return false;
        }

        if ((editHH.length() == 0) || (editMM.length() == 0) || (editSS.length() == 0)) {
            editHH.setError("Please enter workout duration");
            return false;
        }

        return true;
    }


    void deleteWorkout() {
        if (mode != 1) return;

        recordId = Integer.parseInt(getArguments().getString(ID));

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure to delete this workout?");
        //alertDialog.setIcon(R.drawable.delete);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        db.workoutTable.deleteWorkout(recordId);
                        db.close();
                        Toast.makeText(getContext(), "Workout Deleted", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                }
        );
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        alertDialog.show();
    }


    public void showDateDialog() {

        if (mYear == 0) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        editDate.setError(null);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                editDate.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (monthOfYear + 1)) + "/" + year);
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void showTimeDialog() {
        if (mHour == 0) {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                editTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                mHour = hourOfDay;
                mMinute = minute;
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    @NonNull
    private String getFormattedGMTDateTime(String dateString, String timeString) {
        // Followings are the two data entered by the user in the input fields
        // String dateString = "28/12/2021";
        // String timeString = "03:12";

        Date today = null;
        String datetimeString = dateString + " " + timeString;

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        df.setTimeZone(tz);
        try {
            // Convert string into Date
            today = df.parse(datetimeString);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //   DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm z");
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        df1.setTimeZone(TimeZone.getTimeZone("GMT"));

        datetimeString = df1.format(today);

        return datetimeString;

    }

    public String getFormattedDate(String datetimeStringGMT) {
        // Following is the data taken from the database
        //String datetimeString = "27/12/2021 21:42 GMT";
        Date date = null;
        //DateFormat dfGMT = new SimpleDateFormat("dd/MM/yyyy HH:mm z");
        DateFormat dfGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dfGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            // Convert string into Date
            date = dfGMT.parse(datetimeStringGMT);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        DateFormat dfDMY = new SimpleDateFormat("dd/MM/yyyy");
        dfDMY.setTimeZone(Calendar.getInstance().getTimeZone());  // Local TimeZone

        return (dfDMY.format(date));

    }

    public String getFormattedTime(String datetimeStringGMT) {
        // Following is the data taken from the database
        //String datetimeString = "27/12/2021 21:42 GMT";
        Date date = null;
        DateFormat dfGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dfGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            // Convert string into Date
            date = dfGMT.parse(datetimeStringGMT);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        DateFormat dfHM = new SimpleDateFormat("HH:mm");
        dfHM.setTimeZone(Calendar.getInstance().getTimeZone());  //Local Time Zone

        return (dfHM.format(date));
    }

    int getIntegers(String strNum) {
        return Integer.parseInt(strNum);
    }

    float getFloat(String strNum) {
        return Float.parseFloat(strNum);
    }

}