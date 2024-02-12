package com.example.runmaze;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class ChartFragment extends Fragment {

  //  private LineChart mChart;
    private BarChart chart;

    Settings settings;
    int mAthleteId;

    String activity_type = "Run";

    RadioGroup rgActivityType;

    public ChartFragment() {
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
        View fragmentView = inflater.inflate(R.layout.fragment_chart, container, false);

        //SharedPreferences settings = getContext().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        //mAthleteId = settings.getInt("id", 0);

        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();
        rgActivityType = (RadioGroup) fragmentView.findViewById(R.id.rgActivityType);

        showActivitySummary(fragmentView);
        chart = fragmentView.findViewById(R.id.chart);
        showChart();

        rgActivityType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) fragmentView.findViewById(checkedId);
                activity_type = rb.getText().toString();
                showChart();
                showActivitySummary(fragmentView);
            }
        });

        return fragmentView;
    }


    float distanceInTheMonth(ArrayList<WorkoutTable.MonthDistance> arrayList, String month, String year )
    {
        for(WorkoutTable.MonthDistance md : arrayList){
            if((md.Month.equals(month)) && (md.Year.equals(year)))
            {
                return md.Distance;
            }
        }
        return 0f;
    }




    @SuppressLint("DefaultLocale")
    private void showChart() {

        ArrayList<BarEntry> values = new ArrayList<>();
        HashMap<Integer, String> numMap = new HashMap<>();

        DatabaseHandler db = new DatabaseHandler(getContext());
        ArrayList<WorkoutTable.MonthDistance> monthlyData = db.workoutTable.getMonthlyDistance(mAthleteId,activity_type);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -11);
        String month,year;
        String[] monthNames = {"J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D"};
        for (int i = 0; i < 12; i++) {
            month = String.format("%02d", c.get(Calendar.MONTH) + 1);  // calender returns 0 based month value
            year =  String.format("%d",c.get(Calendar.YEAR));
            //numMap.put(i, monthNames[c.get(Calendar.MONTH)] + "-" +  year.substring(2));
            numMap.put(i, monthNames[c.get(Calendar.MONTH)] );
            values.add(new BarEntry(i, distanceInTheMonth(monthlyData,month,year)));
            c.add(Calendar.MONTH, 1);
        }


        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        BarDataSet set1 = new BarDataSet(values, "Distance in km");
       // set1.setColor(Color.parseColor("#304567"));
        set1.setColor(Color.parseColor("#FF6200EE"));

        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        data.setValueTextSize(12f);
        data.setBarWidth(0.3f);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value > 0) {
                    return String.format("%.0f", value);
                } else {
                    return "";
                }
            }
        });

        chart.setData(data);

        // Hide axis
        //      chart.getXAxis().setEnabled(false) ;
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);


        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelCount(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return numMap.get((int)value);
            }
        });
        xAxis.setDrawGridLines(false);

        // Hide all y axis gridlines
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);

        // Remove description
        chart.getDescription().setEnabled(false);


        //hide legend
        //chart.getLegend().setEnabled(false);

        chart.animateY(500);

        chart.invalidate();


        // mChart.setTouchEnabled(true);
        // mChart.setPinchZoom(true);
        // MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
        // mv.setChartView(mChart);
        // mChart.setMarker(mv);


    }




    @SuppressLint("DefaultLocale")
    void showActivitySummary(@NotNull View view)
    {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -11);

        final Date date = c.getTime();
        String month = new SimpleDateFormat("MM").format(date);  // always 2 digits
        String year = new SimpleDateFormat("yyyy").format(date); // 4 digit year

        String sDate = year + "-" + month + "-01";
        String filter = " datetime(workout_date,'localtime') >= '" + sDate + "' ";

        DatabaseHandler db = new DatabaseHandler(getContext());
        TextView txtActivities = view.findViewById(R.id.number_activities);
        txtActivities.setText(String.format("%d",db.workoutTable.getNumberOfActivities(mAthleteId,activity_type,filter)));

        TextView txtDistance = view.findViewById(R.id.number_distance);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        float sumDistance = db.workoutTable.getTotalDistance(mAthleteId,activity_type, filter);
        txtDistance.setText(String.format("%s km", df.format(sumDistance)));

        long sumHours, sumMinutes, sumSeconds;

        sumSeconds = db.workoutTable.getTotalDuration(mAthleteId,activity_type,filter);
        sumMinutes =  (sumSeconds / 60);
        sumSeconds = sumSeconds % 60;
        sumHours = (sumMinutes / 60);
        sumMinutes = sumMinutes % 60;

        TextView txtDuration = view.findViewById(R.id.number_duration);
        txtDuration.setText(String.format("%d h %d min", sumHours, sumMinutes));

        float totalTime = (sumHours + sumMinutes/60f + sumSeconds/3600f);

        float pace = totalTime / sumDistance * 60;
        int paceMin = (int) pace;
        int paceSec = (int)((pace - paceMin) * 60);

        TextView txtPace = view.findViewById(R.id.number_pace);
        txtPace.setText(String.format("%d:%02d min/km", paceMin, paceSec));

        TextView txtSpeed = view.findViewById(R.id.number_speed);
        txtSpeed.setText(String.format("%.2f km/h", sumDistance / totalTime));

    }



}