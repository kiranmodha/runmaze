package com.example.runmaze;

import static com.example.runmaze.stravazpot.authenticaton.api.ApprovalPrompt.AUTO;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.data.model.Athlete;
import com.example.runmaze.utils.CommonUtils;
import com.example.runmaze.utils.Settings;
import com.example.runmaze.data.model.StravaAuth;
import com.example.runmaze.data.model.Workout;
import com.example.runmaze.stravazpot.authenticaton.api.AccessScope;
import com.example.runmaze.stravazpot.authenticaton.api.StravaLogin;
import com.example.runmaze.stravazpot.authenticaton.ui.StravaLoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class StravaFragment extends Fragment {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final int RQ_LOGIN = 1001;
//    private static final int CLIENT_ID = 76621;
    //   private static final String CLIENT_SECRET = "5635717f59e4ade74bf85b16eb0ce74555e25125";


    ImageView imgStrava;
    TextView txtAccessCode;
    Button btnGetActivities;
    EditText editDate;

    Button btnDate;

    String mAuthorizationCode;
    int mStravaAthleteID;
    StravaAuth mStravaAuth;
    DatabaseHandler mDb;
    int mFetchCounter = 1;

    int mAthleteId;
    Settings settings;

    // This variable used for fetching data after first time connection with strava
    String mAuthType;


    private ProgressDialog progressDialog;


    public StravaFragment() {
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
        inflater.inflate(R.menu.menu_strava, menu);
        // return true;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (settings.isAllowDirectStrava() == false)
            menu.findItem(R.id.menuLast_7_days_data).setEnabled(false);

        // It is not implemented yet - so made it invisible
        menu.findItem(R.id.menuReauthorize).setVisible(false);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menuLast_7_days_data) {
            // resetToInitial();
            //Date now = new Date();
            //long epoch = now.getTime()/1000L - (7 * 24 * 60 * 60);
            long epoch = System.currentTimeMillis() / 1000L - (7 * 24 * 60 * 60);
            getActivities(epoch);
        }

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_strava, container, false);

        imgStrava = fragmentView.findViewById(R.id.imageStrava);
        txtAccessCode = fragmentView.findViewById(R.id.txtAccessCode);
        btnGetActivities = fragmentView.findViewById(R.id.btnGetActivities);
        editDate = fragmentView.findViewById(R.id.strava_editDate);

        btnDate = fragmentView.findViewById((R.id.activity_btnDate));

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        mDb = new DatabaseHandler(getContext());

        //SharedPreferences settings = getContext().getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
        //mAthleteId = settings.getInt("id", 0);

        settings = new Settings(getContext());
        mAthleteId = settings.getAthleteId();

        // mStravaAuth = mDb.stravaAuthTable.getStravaAuth();
        mStravaAuth = mDb.athleteTable.getStravaAuth(mAthleteId);

        if (mStravaAuth == null) {
            btnGetActivities.setVisibility(View.INVISIBLE);
            editDate.setVisibility(View.INVISIBLE);
            btnDate.setVisibility(View.INVISIBLE);
            imgStrava.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });
        } else {
            imgStrava.setVisibility(View.INVISIBLE);
            if (mStravaAuth.isAccessTokenValid() == false) {
                btnGetActivities.setVisibility(View.INVISIBLE);
                editDate.setVisibility(View.INVISIBLE);
                btnDate.setVisibility(View.INVISIBLE);
                mStravaAthleteID = mStravaAuth.getAthlete_id();
                //txtAccessCode.setText(String.valueOf(mStravaAuth.isAccessTokenValid()));
                if (settings.isAllowDirectStrava() == true) {
                    txtAccessCode.setText("Waiting for New access token...");
                    getToken("refresh_token");
                } else {
                    txtAccessCode.setText("Already linked with Strava!");
                }
            } else {
                //mStravaAuth = mDb.stravaAuthTable.getStravaAuth();
                if (settings.isAllowDirectStrava() == false) {
                    btnGetActivities.setVisibility(View.INVISIBLE);
                    editDate.setVisibility(View.INVISIBLE);
                    btnDate.setVisibility(View.INVISIBLE);
                }
                txtAccessCode.setText("Ready!");
            }

            btnGetActivities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mStravaAthleteID = mStravaAuth.getAthlete_id();
                    // getActivities(1546300800);
                    String dateString = editDate.getText().toString();
                    Date date = null;
                    DateFormat dfGMT = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        // Convert string into Date
                        date = dfGMT.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long epoch = date.getTime() / 1000L;
                    mFetchCounter = 1;
                    getActivities(epoch);
                }
            });
        }

        return fragmentView;
    }


    private void login() {
//        Intent intent = StravaLogin.withContext(getContext())
//                .withClientID(CLIENT_ID)
//                .withRedirectURI("http://localhost/token_exchange")
//                .withApprovalPrompt(AUTO)
//                .withAccessScope(AccessScope.ACTIVITY_READ_ALL)
//                .makeIntent();

        Intent intent = StravaLogin.withContext(getContext())
                .withClientID(settings.getClientId())
                .withRedirectURI("http://localhost/token_exchange")
                .withApprovalPrompt(AUTO)
                .withAccessScope(AccessScope.ACTIVITY_READ_ALL)
                .makeIntent();
        startActivityForResult(intent, RQ_LOGIN);
    }

    public void showDateDialog() {
        final int[] mYear = new int[1];
        final int[] mMonth = new int[1];
        final int[] mDay = new int[1];

        final Calendar c = Calendar.getInstance();
            mYear[0] = c.get(Calendar.YEAR);
            mMonth[0] = c.get(Calendar.MONTH);
            mDay[0] = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                editDate.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (monthOfYear + 1)) + "/" + year);
                mYear[0] = year;
                mMonth[0] = monthOfYear;
                mDay[0] = dayOfMonth;
            }
        }, mYear[0], mMonth[0], mDay[0]);
        datePickerDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_LOGIN && resultCode == Activity.RESULT_OK && data != null) {
            mAuthorizationCode = data.getStringExtra(StravaLoginActivity.RESULT_CODE);
            Log.d("Strava code", mAuthorizationCode);
            getToken("Authorization_Code");
            imgStrava.setVisibility(View.INVISIBLE);
        }
    }


    void getToken(String authType) {

        //don't delete
        mAuthType = authType;

        OkHttpClient client = new OkHttpClient();
        String urlString;
//        if (authType == "Authorization_Code") {
//            urlString = "https://www.strava.com/oauth/token?"
//                    + "client_id=" + CLIENT_ID
//                    + "&client_secret=" + CLIENT_SECRET
//                    + "&code=" + mAuthorizationCode
//                    + "&grant_type=authorization_code";
//        } else {   // Refresh_Token
//            urlString = "https://www.strava.com/oauth/token?"
//                    + "client_id=" + CLIENT_ID
//                    + "&client_secret=" + CLIENT_SECRET
//                    + "&refresh_token=" + mStravaAuth.getRefresh_token()
//                    + "&grant_type=refresh_token";
//        }

        if (authType == "Authorization_Code") {
            urlString = "https://www.strava.com/oauth/token?"
                    + "client_id=" + settings.getClientId()
                    + "&client_secret=" + settings.getClientSecret()
                    + "&code=" + mAuthorizationCode
                    + "&grant_type=authorization_code";
        } else {   // Refresh_Token
            urlString = "https://www.strava.com/oauth/token?"
                    + "client_id=" + settings.getClientId()
                    + "&client_secret=" + settings.getClientSecret()
                    + "&refresh_token=" + mStravaAuth.getRefresh_token()
                    + "&grant_type=refresh_token";
        }

        RequestBody requestBody = RequestBody.create(new byte[0], null);
        //  RequestBody requestBody = RequestBody.create(null, new byte[0]);

        Request request = new Request.Builder()
                .url(urlString)
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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            JSONObject jsonObject = new JSONObject(strResponse);
                            try {
                                // In case of refresh_token request, athlete object is not available in the response json
                                mStravaAthleteID = jsonObject.getJSONObject("athlete").getInt("id");
                            } catch (Exception e) {
                                // Do nothing... already assigned athleteID will be used
                            }

                            mStravaAuth = new StravaAuth(mStravaAthleteID,
                                    jsonObject.getString("access_token"),
                                    jsonObject.getLong("expires_at"),
                                    jsonObject.getString("refresh_token"),
                                    settings.getClientId(), settings.getClientSecret());

                            // mDb.stravaAuthTable.addOrUpdateStravaAuth(mStravaAuth);
                            mDb.athleteTable.updateStravaAuth(mStravaAuth, mAthleteId);

                            Athlete athlete = new Athlete();
                            athlete.setId(mAthleteId);
                            athlete.setRemote_update(0);

                            mDb.athleteTable.updateRemoteUpdateStatus(athlete);

                            if (settings.isAllowDirectStrava()) {
                                btnGetActivities.setVisibility(View.VISIBLE);
                                editDate.setVisibility(View.VISIBLE);
                                btnDate.setVisibility(View.VISIBLE);
                            }
                            txtAccessCode.setText("Ready!");

                            // If its first connection with strava - download last one year data
                            if (mAuthType == "Authorization_Code") {
                                settings.setFirstStravaConnect(true);
                                settings.save();
                                long epoch = System.currentTimeMillis() / 1000L - (5 * 365 * 24 * 60 * 60);
                                getActivities(epoch);
                            }


                        } catch (Exception e) {

                        }
                        Log.d("Response", strResponse);
                    }
                });

            }
        });
    }


    void getActivities(long epoch) {
        OkHttpClient client = new OkHttpClient();

        // maximum limit per page is 200 and default value is 30
        String urlString = "https://www.strava.com/api/v3/athlete/activities?"
                + "after=" + epoch
                + "&per_page=" + 200
                + "&access_token=" + mStravaAuth.getAccess_token();

        Request request = new Request.Builder()
                .url(urlString)
                .build();

        if (mFetchCounter == 1) {
            txtAccessCode.setText("Fetching workouts , wait...");
        }

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

                        Workout workout;
                        int totalTime, hh, mm, ss;
                        int activityUploaded = 0;
                        Date date = new Date();

                        try {

                            // {"message":"Authorization Error","errors":[{"resource":"Athlete","field":"access_token","code":"invalid"}]}
                            // TODO check if there is authorization Error --> if yes show error message and return back

                            JSONArray jsonArray = new JSONArray(strResponse);

                            // TODO add heart rate data in the database
                            // has_heartrate":true,"average_heartrate":154.5,"max_heartrate":176.0

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                totalTime = jsonObject.getInt("elapsed_time");
                                hh = totalTime / 3600;
                                mm = (totalTime - (hh * 3600)) / 60;
                                ss = totalTime - (hh * 3600) - (mm * 60);
                                date = CommonUtils.convertToDate(jsonObject.getString("start_date").replace("Z", "").replace("T", " "));
                                workout = new Workout(-1,
                                        mAthleteId,
                                        jsonObject.getString("start_date").replace("Z", "").replace("T", " "),
                                        (float) jsonObject.getDouble("distance") / 1000f,
                                        jsonObject.getString("type"),
                                        hh,
                                        mm,
                                        ss,
                                        jsonObject.getString("name"));
                                mDb.workoutTable.addWorkoutIfNotExists(workout);
                                activityUploaded++;
                            }
                            if (activityUploaded == 200) {
                                //reprocess from the last processed date
                                txtAccessCode.setText(String.format("%d Workouts updated, wait...",activityUploaded * mFetchCounter));
                                long epoch = 0;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    epoch = date.toInstant().toEpochMilli()/1000;
                                } else {
                                    epoch = date.getTime()/1000;
                                }
                                mFetchCounter = mFetchCounter + 1;
                                getActivities(epoch);
                            }

                        } catch (Exception e) {

                        }
                        if (activityUploaded < 200) {
                            Toast.makeText(getContext(), "Upload Complete", Toast.LENGTH_LONG).show();
                            getActivity().onBackPressed();
                        }
                    }
                });
            }
        });
    }
}


