package com.example.runmaze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.data.model.City;
import com.example.runmaze.data.model.Club;
import com.example.runmaze.data.model.Company;
import com.example.runmaze.data.model.MasterDataVersion;
import com.example.runmaze.utils.Settings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    int mAthleteId;
    Settings settings;
    float current_version = 1.0f;
    float required_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        settings = new Settings(this);
        //settings.setUrl("");
        //settings.save();
        mAthleteId = settings.getAthleteId();

        // createTables();
        // populateTable();
        getOptionsFromServer();
        //TODO put interlock - further loading done only after options are received from the server
    }

/*    void populateTable() {
        LeaderboardItem lbItem = new LeaderboardItem(1,"Kiran Modha",1,10.5f,"Club","Today","Run");
        DatabaseHandler db = new DatabaseHandler(this);
        db.leaderboardTable.addLeaderboardItem(lbItem);
        lbItem = new LeaderboardItem(2,"Mahesh Prajapati",1,10.3f,"Club","Today","Run");
        db.leaderboardTable.addLeaderboardItem(lbItem);
    }*/

/*
   void createTables() {
     // DatabaseHandler db = new DatabaseHandler(this);
      // SQLiteDatabase tmp = db.getWritableDatabase();
      // db.createTempTables(tmp);

     // db.athleteTable.deleteAllRecords();
     // db.workoutTable.deleteAllRecords();
    //   db.athleteTable.alterTable(db.getWritableDatabase());
    // db.workoutTable.upgradeTable(db.getWritableDatabase(),1,1);
    // db.versionTable.upgradeTable(db.getWritableDatabase(),1,1);
    // db.cityTable.upgradeTable(db.getWritableDatabase(),1,1);
    // db.companyTable.upgradeTable(db.getWritableDatabase(),1,1);
    // db.clubTable.upgradeTable(db.getWritableDatabase(),1,1);
     //    db.leaderboardTable.upgradeTable(db.getWritableDatabase(),1,1);
     }
*/


/*    public void showWebview() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        TitleFragment titleFragment = (TitleFragment) fragmentManager.findFragmentById(R.id.mainLayout);

        if (titleFragment != null) {
            titleFragment.showWebView();
        }

    }*/

    public void getOptionsFromServer() {
        OkHttpClient client = new OkHttpClient();
       // String server_url = "https://runmaze-api.000webhostapp.com/api/options/read/";
        String server_url = "https://eklavyarun.in/api/options/read/";
        RequestBody requestBody = new FormBody.Builder()
                .add("athlete_id", String.format("%d", mAthleteId))
                .build();
        Request request = new Request.Builder()
                .url(server_url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            settings = new Settings(MainActivity.this, strResponse);
                            settings.save();
                            checkMasterDataVersions();
                           //showWebview();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    void exitApplication() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        } else {
            finish();
            System.exit(0);
        }
    }

    void checkMasterDataVersions() {
        if (settings.getRequiredVersion() > current_version) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Version Expired");
            alertDialog.setMessage("Current version of application is outdated.. Install latest version");
            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            exitApplication();
                        }
                    }
            );
            alertDialog.show();
        }

        DatabaseHandler db = new DatabaseHandler(this);

        if (settings.getCityMasterVersion() != db.versionTable.getVersion("city").getVersionNumber()) {
            // Fetch and update data from server
            getCitiesFromServer();
        }
        if (settings.getClubMasterVersion() != db.versionTable.getVersion("club").getVersionNumber()) {
            // Fetch and update data from server
            getClubsFromServer();
        }
        if (settings.getCompanyMasterVersion() != db.versionTable.getVersion("company").getVersionNumber()) {
            // Fetch and update data from server
            getCompaniesFromServer();
        }
    }


    public void getCitiesFromServer() {
        OkHttpClient client = new OkHttpClient();
        //String server_url = "https://runmaze-api.000webhostapp.com/api/city/read/";
        String server_url = "https://eklavyarun.in/api/city/read/";

        Request request = new Request.Builder()
                .url(server_url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        City city;
                        JSONObject jsonObjectResponse;
                        JSONObject jsonObjectCity;
                        JSONArray jsonArrayCities;
                        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                        ;
                        try {
                            jsonObjectResponse = new JSONObject(strResponse);
                            String status = jsonObjectResponse.getString("status");
                            if (status.equals("success")) {
                                db.cityTable.deleteAllRecords();
                                jsonArrayCities = jsonObjectResponse.getJSONArray("city");
                                for (int i = 0; i < jsonArrayCities.length(); i++) {
                                    jsonObjectCity = jsonArrayCities.getJSONObject(i);
                                    city = new City(
                                            jsonObjectCity.getString("id"),
                                            jsonObjectCity.getString("name"));
                                    db.cityTable.addCity(city);
                                }
                            }
                            MasterDataVersion masterDataVersion = new MasterDataVersion("city", settings.getCityMasterVersion());
                            db.versionTable.addOrUpdateMasterDataVersion(masterDataVersion);
                            db.close();
                        } catch (Exception e) {
                        }
                    }
                });
            }
        });
    }


    public void getClubsFromServer() {
        OkHttpClient client = new OkHttpClient();
        //String server_url = "https://runmaze-api.000webhostapp.com/api/club/read/";
        String server_url = "https://eklavyarun.in/api/club/read/";

        Request request = new Request.Builder()
                .url(server_url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Club club;
                        JSONObject jsonObjectResponse;
                        JSONObject jsonObjectClub;
                        JSONArray jsonArrayClubs;
                        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                        ;
                        try {
                            jsonObjectResponse = new JSONObject(strResponse);
                            String status = jsonObjectResponse.getString("status");
                            if (status.equals("success")) {
                                db.clubTable.deleteAllRecords();
                                jsonArrayClubs = jsonObjectResponse.getJSONArray("club");
                                for (int i = 0; i < jsonArrayClubs.length(); i++) {
                                    jsonObjectClub = jsonArrayClubs.getJSONObject(i);
                                    club = new Club(
                                            jsonObjectClub.getString("id"),
                                            jsonObjectClub.getString("name"));
                                    db.clubTable.addClub(club);
                                }
                            }
                            MasterDataVersion masterDataVersion = new MasterDataVersion("club", settings.getClubMasterVersion());
                            db.versionTable.addOrUpdateMasterDataVersion(masterDataVersion);
                            db.close();
                        } catch (Exception e) {
                        }
                    }
                });
            }
        });
    }


    public void getCompaniesFromServer() {
        OkHttpClient client = new OkHttpClient();
        //String server_url = "https://runmaze-api.000webhostapp.com/api/company/read/";
        String server_url = "https://eklavyarun.in/api/company/read/";

        Request request = new Request.Builder()
                .url(server_url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Company company;
                        JSONObject jsonObjectResponse;
                        JSONObject jsonObjectCompany;
                        JSONArray jsonArrayCompanies;
                        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                        ;
                        try {
                            jsonObjectResponse = new JSONObject(strResponse);
                            String status = jsonObjectResponse.getString("status");
                            if (status.equals("success")) {
                                db.companyTable.deleteAllRecords();
                                jsonArrayCompanies = jsonObjectResponse.getJSONArray("company");
                                for (int i = 0; i < jsonArrayCompanies.length(); i++) {
                                    jsonObjectCompany = jsonArrayCompanies.getJSONObject(i);
                                    company = new Company(
                                            jsonObjectCompany.getString("id"),
                                            jsonObjectCompany.getString("name"));
                                    db.companyTable.addCompany(company);
                                }
                            }
                            MasterDataVersion masterDataVersion = new MasterDataVersion("company", settings.getCompanyMasterVersion());
                            db.versionTable.addOrUpdateMasterDataVersion(masterDataVersion);
                            db.close();
                        } catch (Exception e) {
                        }
                    }
                });
            }
        });
    }

}