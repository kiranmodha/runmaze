package com.example.runmaze;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.data.adapter.LeaderboardRecyclerAdapter;
import com.example.runmaze.data.model.Athlete;
import com.example.runmaze.data.model.LeaderboardItem;
import com.example.runmaze.utils.Settings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LeaderboardFragment extends Fragment {

    ArrayList<LeaderboardItem> arrayList;
    RecyclerView recyclerView;
    TextView txtEmptyView;
    TextView txtCategoryName;

    TextView txtSerialNo,txtName,txtActivityCount, txtDistance;

    DatabaseHandler db;
    View fragmentView;

    Settings settings;
    int mAthleteId;

    Athlete athlete;

    RadioGroup grpCategory;
    RadioGroup grpPeriod;
    RadioGroup grpActivity;

    String category = "Club";
    String period = "Day";
    String activity = "All";

    boolean alreadyPopulated = false;

    public LeaderboardFragment() {
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
        fragmentView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        db = new DatabaseHandler(getContext());

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.list_leaderboard_recyclerview);
        txtEmptyView = fragmentView.findViewById(R.id.txtEmptyView);
        txtEmptyView.setVisibility(View.INVISIBLE);
        txtCategoryName = fragmentView.findViewById(R.id.txtCategoryName);

        txtSerialNo  = fragmentView.findViewById(R.id.txtSerialNo);
        txtName = fragmentView.findViewById(R.id.txtName);
        txtActivityCount  = fragmentView.findViewById(R.id.txtActivityCount);
        txtDistance = fragmentView.findViewById(R.id.txtDistance);

        arrayList = new ArrayList<LeaderboardItem>();

        grpCategory = (RadioGroup) fragmentView.findViewById(R.id.rgCategory);
        grpPeriod = (RadioGroup) fragmentView.findViewById(R.id.rgPeriod);
        grpActivity = (RadioGroup) fragmentView.findViewById(R.id.rgActivityType);

        // Get initial values
        //TODO getting some error - need to resolve
       // category = ((RadioButton)grpCategory.getChildAt(grpCategory.getCheckedRadioButtonId())).getText().toString();
       // period = ((RadioButton)grpPeriod.getChildAt(grpPeriod.getCheckedRadioButtonId())).getText().toString();
       // activity = ((RadioButton)grpActivity.getChildAt(grpActivity.getCheckedRadioButtonId())).getText().toString();

        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();
        athlete = db.athleteTable.athleteByID(mAthleteId);

        grpCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) fragmentView.findViewById(checkedId);
                category = rb.getText().toString();
                arrayList = new ArrayList<>(db.leaderboardTable.getLeaderboardItems(period, getCategoryFilter()));
                showRecyclerview();
                //recyclerView.getAdapter().notifyDataSetChanged();
            }
        });


        grpPeriod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) fragmentView.findViewById(checkedId);
                period = rb.getText().toString();
                arrayList = new ArrayList<>(db.leaderboardTable.getLeaderboardItems(period, getCategoryFilter()));
                showRecyclerview();
                // recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        grpActivity.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb = (RadioButton) fragmentView.findViewById(checkedId);
                activity = rb.getText().toString();
                arrayList = new ArrayList<>(db.leaderboardTable.getLeaderboardItems(period, getCategoryFilter()));
                showRecyclerview();
                // recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();

/*
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        int[] location = new int[2];
        txtSerialNo.getLocationOnScreen(location);

        ScrollView scrollView = fragmentView.findViewById(R.id.scrollView);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(width-16,height - location[0]) );
*/


        return fragmentView;

    }


    String getCategoryFilter() {
        String filter = "";
        switch (category) {
            case "Club":
                filter = " Club = " + athlete.getClub();
                break;
            case "Company":
                filter = " Company = " + athlete.getCompany();
                break;
            case "City":
                filter = " City = " + athlete.getCity();
                break;
        }
        if (activity.equals("All") == false) {
            filter = filter + " and activity_type = '" + activity + "' ";
        } else {
            filter = filter + " and activity_type IN ('Run','Ride','Walk') ";
        }
        filter = filter + " and leaderboard_type = '' ";
        return filter;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (settings.isLoggedIn()) {
            if (!alreadyPopulated) {
                displayLeaderboard();
            }
            if (isLeaderboardRequestDue()) {
                fetchLeaderboardFromRemoteServer();
            }
        }
    }


    boolean isLeaderboardRequestDue() {
        // long gapsInMillis = 900000; // 15 minutes
        long gapsInMillis = settings.getGapRequestForLeaderboard();

        long currentTime = System.currentTimeMillis();
        if (currentTime > settings.getLastRequestForLeaderboard() + gapsInMillis) {
            settings.setLastRequestForLeaderboard(currentTime);
            settings.save();
            return true;
        }
        return false;
       // return true;
    }


    public void displayLeaderboard() {
        arrayList = new ArrayList<>(db.leaderboardTable.getLeaderboardItems(period, getCategoryFilter()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // LeaderboardRecyclerAdapter adapter = new LeaderboardRecyclerAdapter(arrayList);
        // recyclerView.setAdapter(adapter);
        showRecyclerview();
        alreadyPopulated = true;
    }


    void showRecyclerview() {
        switch (category) {
            case "Club":
                txtCategoryName.setText(db.clubTable.getTextFromId(athlete.getClub()).toUpperCase());
                break;
            case "Company":
                txtCategoryName.setText(db.companyTable.getTextFromId(athlete.getCompany()).toUpperCase());
                break;
            case "City":
                txtCategoryName.setText(db.cityTable.getTextFromId(athlete.getCity()).toUpperCase());
                break;
        }
        LeaderboardRecyclerAdapter adapter = new LeaderboardRecyclerAdapter(arrayList);
        if (arrayList.size() == 0) {
            txtEmptyView.setVisibility(View.VISIBLE);
            txtSerialNo.setVisibility(View.INVISIBLE);
            txtName.setVisibility(View.INVISIBLE);
            txtActivityCount.setVisibility(View.INVISIBLE);
            txtDistance.setVisibility(View.INVISIBLE);
        } else {
            txtEmptyView.setVisibility(View.INVISIBLE);
            txtSerialNo.setVisibility(View.VISIBLE);
            txtName.setVisibility(View.VISIBLE);
            txtActivityCount.setVisibility(View.VISIBLE);
            txtDistance.setVisibility(View.VISIBLE);

        }
        recyclerView.setAdapter(adapter);
    }


    void fetchLeaderboardFromRemoteServer() {
        OkHttpClient client = new OkHttpClient();
        //String server_url = "https://runmaze-api.000webhostapp.com/api/leaderboard/read/";
        String server_url = "https://eklavyarun.in/api/leaderboard/read/";
        RequestBody requestBody = new FormBody.Builder()
                .add("athlete_id", String.format("%d", mAthleteId))
                .build();
        Request request = new Request.Builder()
                .url(server_url)
                .post(requestBody)
                .addHeader("Content-Type", "text/plain")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String strResponse = response.body().string();
                // trailing try block placed to avoid crash due to NullPointerException - if user has already pressed back button and current fragment not exists.
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                LeaderboardItem leaderboardItem;
                                JSONObject jObjResponse;
                                JSONObject jObjLeaderboardItem;
                                JSONArray jArrLeaderboardItems;
                                // DatabaseHandler db = new DatabaseHandler(getContext());

                                jObjResponse = new JSONObject(strResponse);
                                String status = jObjResponse.getString("status");
                                if (status.equals("success")) {
                                    db.leaderboardTable.deleteAllRecords();
                                    jArrLeaderboardItems = jObjResponse.getJSONArray("leaderboard");
                                    for (int i = 0; i < jArrLeaderboardItems.length(); i++) {
                                        jObjLeaderboardItem = jArrLeaderboardItems.getJSONObject(i);
                                        leaderboardItem = new LeaderboardItem(
                                                jObjLeaderboardItem.getString("name"),
                                                jObjLeaderboardItem.getInt("count"),
                                                (float) jObjLeaderboardItem.getDouble("distance"),
                                                jObjLeaderboardItem.getInt("club"),
                                                jObjLeaderboardItem.getInt("company"),
                                                jObjLeaderboardItem.getInt("city"),
                                                jObjLeaderboardItem.getString("period"),
                                                jObjLeaderboardItem.getString("type"),
                                                ""
                                        );
                                        db.leaderboardTable.addLeaderboardItem(leaderboardItem);
                                    }
                                }
                                Toast.makeText(getContext(), "leaderboard fetch successfully", Toast.LENGTH_LONG).show();
                                //db.close();
                                arrayList = new ArrayList<>(db.leaderboardTable.getLeaderboardItems(period, getCategoryFilter()));
                                showRecyclerview();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Error Occurred - fetching leaderboard from server", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch(Exception e) {}
            }
        });


    }
}