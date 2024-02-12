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
import com.example.runmaze.data.adapter.HdcLeaderboardRecyclerAdapter;
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


public class HdcFragment extends Fragment {

    ArrayList<LeaderboardItem> arrayList;
    RecyclerView recyclerView;
    TextView txtEmptyView;


    TextView txtSerialNo,txtName,txtActivityCount, txtDistance;

    DatabaseHandler db;
    View fragmentView;

    Settings settings;
    int mAthleteId;

    Athlete athlete;


    RadioGroup grpPeriod;



    String period = "HDC";


    boolean alreadyPopulated = false;

    public HdcFragment() {
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
        fragmentView = inflater.inflate(R.layout.fragment_hdc, container, false);

        db = new DatabaseHandler(getContext());

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.list_leaderboardHDC_recyclerview);
        txtEmptyView = fragmentView.findViewById(R.id.txtEmptyView1);
        txtEmptyView.setVisibility(View.INVISIBLE);


        txtSerialNo  = fragmentView.findViewById(R.id.txtSerialNo1);
        txtName = fragmentView.findViewById(R.id.txtName1);
        txtActivityCount  = fragmentView.findViewById(R.id.txtActivityCount1);
        txtDistance = fragmentView.findViewById(R.id.txtDistance1);

        arrayList = new ArrayList<LeaderboardItem>();

        grpPeriod = (RadioGroup) fragmentView.findViewById(R.id.rgPeriodHDC);


        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();
        athlete = db.athleteTable.athleteByID(mAthleteId);

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


        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();


        return fragmentView;

    }


    String getCategoryFilter() {
        String filter = "";
//        switch (category) {
//            case "Club":
//                filter = " Club = " + athlete.getClub();
//                break;
//            case "Company":
//                filter = " Company = " + athlete.getCompany();
//                break;
//            case "City":
//                filter = " City = " + athlete.getCity();
//                break;
//        }
//        if (activity.equals("All") == false) {
//            filter = filter + " and activity_type = '" + activity + "' ";
//        } else {
//            filter = filter + " and activity_type IN ('Run','Ride','Walk') ";
//        }
        filter = filter + " leaderboard_type = 'HDC' ";
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
        long gapsInMillis = settings.getGapRequestForHdcLeaderboard();

        long currentTime = System.currentTimeMillis();
        if (currentTime > settings.getLastRequestForHdcLeaderboard() + gapsInMillis) {
            settings.setLastRequestForHdcLeaderboard(currentTime);
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

        HdcLeaderboardRecyclerAdapter adapter = new HdcLeaderboardRecyclerAdapter(arrayList);
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
        String server_url = "https://eklavyarun.in/api/leaderboard/hdc_read.php";
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
                                    db.leaderboardTable.deleteAllRecordsHDC();
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
                                                "HDC"
                                        );
                                        leaderboardItem.setDayReported(jObjLeaderboardItem.getInt("days"));
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