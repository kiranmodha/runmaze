package com.example.runmaze;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.data.WorkoutTable;
import com.example.runmaze.utils.Settings;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class MonthChartFragment extends Fragment {

    private BarChart chart;

    Settings settings;
    int mAthleteId;

    String activity_type = "Run";
    String periodStartDate, periodEndDate;

    RadioGroup rgActivityType;
    TextView txtShowMore;
    ImageView imgPrevMonth, imgNextMonth;
    TextView txtTitle;
    View fragmentView;
    int monthNumber = 0;

    public MonthChartFragment() {
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
        fragmentView = inflater.inflate(R.layout.fragment_month_chart, container, false);

        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();


        chart = fragmentView.findViewById(R.id.chart);
        //   showActivitySummary(fragmentView);
        //  showChart();

        //   show();

        rgActivityType = (RadioGroup) fragmentView.findViewById(R.id.rgActivityType);

        rgActivityType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) fragmentView.findViewById(checkedId);
                activity_type = rb.getText().toString();
                showChart();
                showActivitySummary(fragmentView);
            }
        });

        txtTitle = fragmentView.findViewById(R.id.txtTitle);

        txtShowMore = fragmentView.findViewById(R.id.txtShowMore);

        txtShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleFragment parentFragment = (TitleFragment) getParentFragment();
                Navigation.findNavController(parentFragment.getView()).navigate(R.id.action_titleFragment_to_moreDataFragment);
            }
        });

        imgPrevMonth = fragmentView.findViewById(R.id.imgPrevMonth);

        imgPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthNumber = monthNumber - 1;
                showChart();
                showActivitySummary(fragmentView);
            }
        });

        imgNextMonth = fragmentView.findViewById(R.id.imgNextMonth);

        imgNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monthNumber == 0) return;
                monthNumber = monthNumber + 1;
                showChart();
                showActivitySummary(fragmentView);
            }
        });


        return fragmentView;
    }

    public void show()
    {
        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();
        if (mAthleteId == 0) return;
        showActivitySummary(fragmentView);
        showChart();
    }

    float distanceInTheDay(ArrayList<WorkoutTable.MonthDistance> arrayList, String day )
    {
        for(WorkoutTable.MonthDistance md : arrayList){
            if(md.Month.equals(day))
            {
                return md.Distance;
            }
        }
        return 0f;
    }

    private void showChart() {

        ArrayList<BarEntry> values = new ArrayList<>();
        HashMap<Integer, String> numMap = new HashMap<>();

        DatabaseHandler db = new DatabaseHandler(getContext());
       // String yearMonth = "2022-03";
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, monthNumber);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String yearMonth = dateFormat.format(c.getTime());


       // String yearMonth = c.get(Calendar.YEAR) + "-" + String.format("%02d", c.get(Calendar.MONTH) + monthNumber);

        ArrayList<WorkoutTable.MonthDistance> monthlyData = db.workoutTable.getDailyDistance(mAthleteId,yearMonth, activity_type);
        int monthDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        periodStartDate = yearMonth + "-" + "01";
        periodEndDate =  yearMonth + "-" + String.format("%02d",monthDays);

        txtTitle.setText("Distance (km) in current month");


        switch (monthNumber)
        {
            case 0:  // current month
                txtTitle.setText("Distance (km) in current month");
                break;
            case 1:  // last month
                txtTitle.setText("Distance (km) in last month");
                break;
            default:
                dateFormat.applyPattern("MMM-yyyy");
                txtTitle.setText("Distance (km) in " + dateFormat.format(c.getTime()));
                break;
        }





        for (int i = 1; i <= monthDays; i++) {
            values.add(new BarEntry(i, distanceInTheDay(monthlyData, String.format("%d", i))));
        }

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        BarDataSet set1 = new BarDataSet(values, "Distance in km");
        // set1.setColor(Color.parseColor("#304567"));
        set1.setColor(Color.parseColor("#FF6200EE"));

        dataSets.add(set1);

        BarData data = new BarData(dataSets);

       // data.setValueTextSize(12f);
       // data.setBarWidth(0.3f);
/*        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value > 0) {
                    return String.format("%.0f", value);
                } else {
                    return "";
                }
            }
        });*/

        // hide data labels
        data.setDrawValues(false);

        chart.setData(data);

        // Hide axis
        //      chart.getXAxis().setEnabled(false) ;
       // chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);


       XAxis xAxis = chart.getXAxis();
     //    xAxis.setLabelCount(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
/*        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return numMap.get((int)value);
            }
        });*/
        xAxis.setDrawGridLines(false);

        // Hide all y axis gridlines
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisRight().setAxisMinimum(0f);
        chart.getAxisRight().setDrawGridLines(false);

        // Remove description
        chart.getDescription().setEnabled(false);


        //hide legend
        //chart.getLegend().setEnabled(false);

        chart.animateY(500);

        chart.invalidate();




    }


    void showActivitySummary(@NotNull View view)
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Calendar c = Calendar.getInstance();

        c.add(Calendar.MONTH, monthNumber);

        String yearMonth = c.get(Calendar.YEAR) + "-" + String.format("%02d", c.get(Calendar.MONTH) + 1);
        int monthDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        periodStartDate = yearMonth + "-" + "01";
        periodEndDate =  yearMonth + "-" + String.format("%02d",monthDays);



        String sql = "select count(*) activities, sum(distance) distance, sum(duration_hh) hours, sum(duration_mm) minutes, sum(duration_ss) seconds  from workout " +
                " where athlete_id = " + mAthleteId
                +  " and datetime(workout_date,'localtime') >= '" + periodStartDate + "' "
                +  " and datetime(workout_date,'localtime') <= '" + periodEndDate + "' "
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
            }
        }
        cursor.close();
        db.close();

        TextView txtActivities = view.findViewById(R.id.monthChart_Activities);
        txtActivities.setText(String.format("%d", countActivities ));

        TextView txtDistance = view.findViewById(R.id.monthChart_Distance);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        txtDistance.setText(df.format(sumDistance) + " km");

        sumMinutes = sumMinutes + (sumSeconds / 60);
        sumSeconds = sumSeconds % 60;
        sumHours = sumHours + (sumMinutes / 60);
        sumMinutes = sumMinutes % 60;

        TextView txtDuration = view.findViewById(R.id.monthChart_Duration);
        txtDuration.setText(String.format("%d h %d min", sumHours, sumMinutes));

        float totalTime = (sumHours + sumMinutes/60f + sumSeconds/3600f);

        TextView txtPace = view.findViewById(R.id.monthChart_Pace);
        float pace = totalTime / sumDistance * 60;
        int paceMin = (int) pace;
        int paceSec = (int)((pace - paceMin) * 60);
        txtPace.setText(String.format("%d:%02d min/km", paceMin, paceSec));

        TextView txtSpeed = view.findViewById(R.id.monthChart_Speed);
        txtSpeed.setText(String.format("%.2f km/h", sumDistance / totalTime));
    }
}