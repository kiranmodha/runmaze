package com.example.runmaze;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.data.model.Athlete;
import com.example.runmaze.utils.Settings;
import com.example.runmaze.data.model.Workout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TitleFragment extends Fragment {

    View fragmentView;
   // int mAthleteId;
    Settings settings;

    public TitleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_title, menu);
        menu.findItem(R.id.menuDeleteAll).setVisible(false);
        // return true;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_title, container, false);

        //SharedPreferences settings = getContext().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        //mAthleteId = settings.getInt("id", 0);

        settings = new Settings(getContext());
       // mAthleteId = settings.getAthleteId();




       // ImageView img = fragmentView.findViewById(R.id.imageView);
      // DownloadUtils.getInstance().downloadImage("https://files.000webhost.com/assets/logo-colored.svg");


        return fragmentView;
    }


    @Override
    public void onResume() {
        super.onResume();
        checkLoginStatus();
        if (settings.isLoggedIn()) {
            updateAthleteDetailsToRemoteServer();
            updateWorkoutsToRemoteServer();
            if (isWorkoutRequestDue()) {
                fetchWorkoutsFromRemoteServer();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menuStrava) {
            Navigation.findNavController(getView()).navigate(R.id.action_titleFragment_to_stravaFragment);
        }
        if (item.getItemId() == R.id.menuProfile) {
            //  deleteActivities();
            //  createTables();
            //updateAthleteDetailsToRemoteServer();
            //updateWorkoutsToRemoteServer();
          //  fetchWorkoutsFromRemoteServer();

            Bundle bundle = new Bundle();
            bundle.putInt("mode",1);
            Navigation.findNavController(fragmentView).navigate(R.id.action_titleFragment_to_athleteFragment,bundle);

            //Toast.makeText(getContext(),"To be implemented",Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.menuDeleteAll) {

           // This menu item is made invisible
           // deleteActivities();

        }
        if (item.getItemId() == R.id.menuSignOut) {
            signOut();

        }
        if (item.getItemId() == R.id.menuAbout) {
            //Navigation.findNavController(getView()).navigate(R.id.action_titleFragment_to_dataFragment);
            //  createTables();
            Toast.makeText(getContext(),"To be implemented",Toast.LENGTH_SHORT).show();

        }

        return true;
    }


    void CheckStravaConnection() {
/*        FragmentManager fragmentManager = getChildFragmentManager();
        MessageFragment msgFragment = (MessageFragment )fragmentManager.findFragmentById(R.id.fragMessage);

        if (msgFragment != null)
        {
            msgFragment.show();
        }*/

        if (settings.isLoggedIn() == false) {
            fragmentView.findViewById(R.id.fragMessage).setVisibility(View.GONE);
            return;
        }


        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        Athlete athlete = dbHandler.athleteTable.athleteByID(settings.getAthleteId());
        if (athlete != null) {
                if (athlete.getStrava_auth().getAthlete_id() != 0) {
                    fragmentView.findViewById(R.id.fragMessage).setVisibility(View.GONE);
                } else {
                    fragmentView.findViewById(R.id.fragMessage).setVisibility(View.VISIBLE);
                }
        }
        dbHandler.close();
    }


    void RefreshSubFragments() {
        FragmentManager fragmentManager = getChildFragmentManager();
        MonthChartFragment monthChart = (MonthChartFragment )fragmentManager.findFragmentById(R.id.fragMonthChart);

        if (monthChart != null)
        {
            monthChart.show();
        }

        RecentActivitiesFragment recentActivitiesFragment = (RecentActivitiesFragment )fragmentManager.findFragmentById(R.id.fragRecentActivities);

        if (recentActivitiesFragment != null)
        {
            recentActivitiesFragment.show();
        }

       // To add the MessageFragment programmatically
      //  FragmentTransaction fragTransaction = fragmentManager.beginTransaction();

      //  Fragment messageFragment = new MessageFragment();
      //  fragTransaction.add(R.id.mainLayout, messageFragment, "messageFragment" );
      //  fragTransaction.commit();
    }


    void checkLoginStatus() {
        //Context context = getContext();
        // Restore preferences
        //SharedPreferences settings = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        //boolean loggedIn = settings.getBoolean("logged", false);
        settings = new Settings(getContext());
        boolean loggedIn = settings.isLoggedIn();
        if (loggedIn) {

            //Hide login fragment
            fragmentView.findViewById(R.id.fragLogin).setVisibility(View.GONE);
            fragmentView.findViewById(R.id.fragRecentActivities).setVisibility(View.VISIBLE);
            fragmentView.findViewById(R.id.fragMonthChart).setVisibility(View.VISIBLE);
            RefreshSubFragments();
            showWebView();
           // fragmentView.findViewById(R.id.fragChart).setVisibility(View.VISIBLE);
           // fragmentView.findViewById(R.id.fragStats).setVisibility(View.VISIBLE);

        } else {
            // Display Login fragment and hide all other fragments
            fragmentView.findViewById(R.id.fragLogin).setVisibility(View.VISIBLE);
            fragmentView.findViewById(R.id.fragRecentActivities).setVisibility(View.INVISIBLE);
            fragmentView.findViewById(R.id.fragMonthChart).setVisibility(View.INVISIBLE);
            fragmentView.findViewById(R.id.fragWeb).setVisibility(View.INVISIBLE);

           // fragmentView.findViewById(R.id.fragChart).setVisibility(View.INVISIBLE);
           // fragmentView.findViewById(R.id.fragStats).setVisibility(View.INVISIBLE);

        }
        CheckStravaConnection();

    }

    public void showWebView()
    {

        settings = new Settings(getContext());
        if (settings.getUrl().length()==0) {
            fragmentView.findViewById(R.id.fragWeb).setVisibility(View.GONE);
            return;
        } else {
            fragmentView.findViewById(R.id.fragWeb).setVisibility(View.VISIBLE);
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        WebFragment webFragment = (WebFragment)fragmentManager.findFragmentById(R.id.fragWeb);

        if (webFragment != null)
        {
            webFragment.url =  settings.getUrl();
            webFragment.show();
        }
    }



    boolean isWorkoutRequestDue() {
        //long gapsInMillis = 3600000; // One hour
        long currentTime = System.currentTimeMillis();

        if (settings.isFetchAllWorkouts()) {  // this is one time after creating user from server
            settings.setLastRequestForWorkout(currentTime);
            settings.save();
            return true;
        }

        long gapsInMillis = settings.getGapRequestForWorkout();


        if (currentTime > settings.getLastRequestForWorkout() + gapsInMillis) {
            settings.setLastRequestForWorkout(currentTime);
            settings.save();
            return true;
        }
        return false;
    }


    void updateAthleteDetailsToRemoteServer() {
        DatabaseHandler db = new DatabaseHandler(getContext());
       // Athlete athlete = db.athleteTable.athleteByID(mAthleteId);
        Athlete athlete = db.athleteTable.athleteByID(settings.getAthleteId());
        if (athlete == null) {
            return;
        }
        if (athlete.getRemote_update() != 0) {
            return;
        }
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        JSONObject obj = athlete.getJSONObject();
        try {
            obj.put("remote_update",2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(obj.toString(), mediaType);
        //String server_url = "https://runmaze-api.000webhostapp.com/api/athlete/update/";
        String server_url = "https://eklavyarun.in/api/athlete/update/";
        Request request = new Request.Builder()
                .url(server_url)
                .post(requestBody)
                .addHeader("Content-Type", "text/plain")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(strResponse);
                                String status = jsonObject.getString("status");
                                if (status.equals("success")) {
                                    athlete.setRemote_update(1);
                                    db.athleteTable.updateRemoteUpdateStatus(athlete);
                                    Toast.makeText(getContext(), "Data updated successfully", Toast.LENGTH_LONG).show();

                                    //if (db.workoutTable.getNumberOfActivities(athlete.getId(),"") == 0) {
                                    if (settings.isFirstStravaConnect()) {
                                        // After first connection with strava - 30 days workouts are fetched
                                        fetchInitialWorkoutsAfterFirstConnectWithStrava();
                                        settings.setFirstStravaConnect(false);
                                    }
                                } else {
                                    //Error creating athlete
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Error in Athlete Upload", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {}
            }
        });
    }

    void fetchInitialWorkoutsAfterFirstConnectWithStrava()
    {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        //.add("athlete_id", String.format("%d", mAthleteId))
        RequestBody requestBody = new FormBody.Builder()
                .add("athlete_id", String.format("%d", settings.getAthleteId()))
                .build();
        //String server_url = "https://runmaze-api.000webhostapp.com/api/strava/athlete_activity.php";
        String server_url = "https://eklavyarun.in/api/strava/athlete_activity.php";
        Request request = new Request.Builder()
                .url(server_url)
                .post(requestBody)
                .addHeader("Content-Type", "text/plain")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // set fetchAllWorkouts flag
                        settings.setFetchAllWorkouts(true);
                        settings.save();
                    }
                });
            }
        });
    }

    void fetchWorkoutsFromRemoteServer() {
        OkHttpClient client = new OkHttpClient();
        //String server_url = "https://runmaze-api.000webhostapp.com/api/workout/read/";
        String server_url = "https://eklavyarun.in/api/workout/read/";
        RequestBody requestBody;
        if (settings.isFetchAllWorkouts()) {
            // .add("athlete_id", String.format("%d", mAthleteId))
            requestBody = new FormBody.Builder()
                    .add("athlete_id", String.format("%d", settings.getAthleteId()))
                    .build();
        } else {
            //                    .add("athlete_id", String.format("%d", mAthleteId))
            requestBody = new FormBody.Builder()
                    .add("athlete_id", String.format("%d", settings.getAthleteId()))
                    .add("remote_update", "0")
                    .build();
        }
        Request request = new Request.Builder()
                .url(server_url)
                .post(requestBody)
                .addHeader("Content-Type", "text/plain")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Toast.makeText(getContext(), "Inside", Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jObjResponse;
                                jObjResponse = new JSONObject(strResponse);
                                String status = jObjResponse.getString("status");
                                if (status.equals("success")) {
                                    Workout workout;
                                    JSONObject jObjWorkout;
                                    JSONArray jArrWorkouts;
                                    JSONArray jArrReturnResponse = new JSONArray();
                                    DatabaseHandler db = new DatabaseHandler(getContext());
                                    jArrWorkouts = jObjResponse.getJSONArray("workout");
                                    for (int i = 0; i < jArrWorkouts.length(); i++) {
                                        jObjWorkout = jArrWorkouts.getJSONObject(i);

                                        workout = new Workout(-1,
                                                settings.getAthleteId(),
                                                jObjWorkout.getString("date"),
                                                (float) jObjWorkout.getDouble("distance"),
                                                jObjWorkout.getString("type"),
                                                jObjWorkout.getInt("hh"),
                                                jObjWorkout.getInt("mm"),
                                                jObjWorkout.getInt("ss"),
                                                jObjWorkout.getString("link")
                                        );
                                        workout.setRemote_update(2);  // 2 - From Remote Server
                                        db.workoutTable.addWorkoutIfNotExists(workout);
                                        JSONObject tempObject = new JSONObject();
                                        tempObject.put("id", jObjWorkout.getInt("id"));
                                        jArrReturnResponse.put(tempObject);
                                    }
                                    Toast.makeText(getContext(), "Workout fetched successfully", Toast.LENGTH_LONG).show();
                                    db.close();
                                    if (settings.isFetchAllWorkouts() == false) {
                                        updateRemoteServerWorkoutUpdateStatus(jArrReturnResponse.toString());
                                    } else {
                                        settings.setFetchAllWorkouts(false);
                                        settings.save();
                                    }
                                    RefreshSubFragments();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Error Occurred - fetching activities from server", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {}
            }
        });
    }



    void updateRemoteServerWorkoutUpdateStatus(String jsonString)
    {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody requestBody = RequestBody.create(jsonString, mediaType);
        //String server_url = "https://runmaze-api.000webhostapp.com/api/workout/update/";
        String server_url = "https://eklavyarun.in/api/workout/update/";
        Request request = new Request.Builder()
                .url(server_url)
                .post(requestBody)
                .addHeader("Content-Type", "text/plain")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
            }
        });
    }



    void updateWorkoutsToRemoteServer() {
        DatabaseHandler db = new DatabaseHandler(getContext());

       // String jsonString = db.workoutTable.getWorkoutsJSON(-1, mAthleteId, 0);
        String jsonString = db.workoutTable.getWorkoutsJSON(-1, settings.getAthleteId(), 0);

        if (jsonString == "") {
            return;
        }
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody requestBody = RequestBody.create(jsonString, mediaType);
        //String server_url = "https://runmaze-api.000webhostapp.com/api/workout/create/";
        String server_url = "https://eklavyarun.in/api/workout/create/";
        Request request = new Request.Builder()
                .url(server_url)
                .post(requestBody)
                .addHeader("Content-Type", "text/plain")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONArray jsonArray = new JSONArray(strResponse);
                                Workout workout = new Workout();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String status = jsonObject.getString("status");
                                    if (status.equals("success")) {
                                        int id = jsonObject.getInt("id");
                                        workout.setId(id);
                                        workout.setRemote_update(1);
                                        db.workoutTable.updateRemoteUpdateStatus(workout);
                                        // getActivity().onBackPressed();
                                    } else {
                                        //Error creating athlete
                                    }
                                }
                            } catch (Exception e) {
                            }
                            Toast.makeText(getContext(), "Data updated successfully", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {}
            }
        });
    }


    void signOut() {
        // Context context = getContext();
        // SharedPreferences settings = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        // SharedPreferences.Editor editor = settings.edit();
        // editor.putBoolean("logged", false);
        // editor.putInt("id", 0);
        // editor.commit();

        settings = new Settings(getContext());
        settings.setLoggedIn(false);
        settings.setAthleteId(0);
        settings.save();


        checkLoginStatus();
    }


    // Temporary Methods

/*    void createTables() {
        DatabaseHandler db = new DatabaseHandler(getContext());
        //db.workoutTable.upgradeTable(db.getWritableDatabase(),1,1);
        db.workoutTable.alterTable(db.getWritableDatabase());
        db.athleteTable.alterTable(db.getWritableDatabase());
    }*/


/*    void deleteActivities() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure to delete all activities?");
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        db.workoutTable.deleteWorkout(-1);
                        db.close();
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
    }*/


}