package com.example.runmaze;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runmaze.data.AthleteTable;
import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.data.model.Athlete;
import com.example.runmaze.utils.Settings;
import com.example.runmaze.data.model.StravaAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginFragment extends Fragment {

    Button btnLogin;
    TextView txtCreateAccount;
    EditText edtEmail, edtPassword;
    Athlete athlete;
    Settings settings ;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);

        txtCreateAccount = fragmentView.findViewById(R.id.login_create_account);
        edtEmail = fragmentView.findViewById(R.id.login_email);
        edtPassword = fragmentView.findViewById(R.id.login_password);
        settings = new Settings(getContext());
        edtEmail.setText(settings.getUserId());
        edtPassword.setText(settings.getPassword());
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleFragment parentFragment = (TitleFragment) getParentFragment();
                Navigation.findNavController(parentFragment.getView()).navigate(R.id.action_titleFragment_to_athleteFragment);
            }
        });

        btnLogin = fragmentView.findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin()) {
                    // on login success - make login fragment disappeared and display other fragments
                    ((TitleFragment) getParentFragment()).checkLoginStatus();
//                } else {
//                    Toast.makeText(getContext(),"User not exists or password mismatch",Toast.LENGTH_LONG).show();
                }
            }
        });

        return fragmentView;
    }



    boolean isLogin() {

       // Settings settings = new Settings(getContext());
        AthleteTable athleteTable = new AthleteTable(new DatabaseHandler(getContext()));
        //TODO logic to get athlete only based on email and check password here - this to avoid unnecessary call to remote server
        athlete = athleteTable.login(edtEmail.getText().toString(), edtPassword.getText().toString());
        if (athlete != null) {
            settings.setLoggedIn(true);
            settings.setAthleteId(athlete.getId());
            settings.setUserId(edtEmail.getText().toString() );
            settings.setPassword(edtPassword.getText().toString());
            settings.save();
            return true;
        } else {
            fetchAthleteFromServer();
        }
        return false;
    }


    void fetchAthleteFromServer() {
        OkHttpClient client = new OkHttpClient();

        //String server_url = "https://runmaze-api.000webhostapp.com/api/athlete/read.php";
        String server_url = "https://eklavyarun.in/api/athlete/read.php";

        RequestBody requestBody = new FormBody.Builder()
                .add("email", edtEmail.getText().toString())
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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jObjResponse = new JSONObject(strResponse);
                            JSONObject jObjAthlete;
                            JSONArray jArrAthletes;
                            String status = jObjResponse.getString("status");
                            if (status.equals("success")) {
                                jArrAthletes = jObjResponse.getJSONArray("athletes");
                                for (int i = 0; i < jArrAthletes.length(); i++) {
                                    jObjAthlete = jArrAthletes.getJSONObject(i);
                                    String password = jObjAthlete.getString("password");
                                    if (password.equals(edtPassword.getText().toString())) {
                                        updateLocalDatabase(jObjAthlete);
                                        ((TitleFragment) getParentFragment()).checkLoginStatus();
                                        Toast.makeText(getContext(), "Account imported successfully", Toast.LENGTH_LONG).show();
                                        // getActivity().onBackPressed();
                                    } else {
                                        Toast.makeText(getContext(),"User not exists or password mismatch",Toast.LENGTH_LONG).show();
                                    }
                                    break;
                                }

                            } else {
                                Toast.makeText(getContext(),"User not exists or password mismatch",Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Error while importing account", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }


    void updateLocalDatabase(JSONObject jsonObject) throws JSONException {

        Athlete athlete = new Athlete();
        athlete.setId(jsonObject.getInt("id"));
        athlete.setName(jsonObject.getString("name"));
        athlete.setEmail(jsonObject.getString("email"));
        athlete.setPassword( jsonObject.getString("password"));
        athlete.setGender(jsonObject.getString("gender"));
        athlete.setBirthdate( jsonObject.getString("birthdate"));
        athlete.setCity (jsonObject.getInt("city"));
        athlete.setClub(jsonObject.getInt("club"));
        athlete.setCompany(jsonObject.getInt("company"));

        StravaAuth stravaAuth = new StravaAuth();
        stravaAuth.setAthlete_id(jsonObject.getInt("strava_athlete_id"));
        stravaAuth.setAccess_token(jsonObject.getString("access_token"));
        stravaAuth.setExpires_at(jsonObject.getLong("expires_at"));
        stravaAuth.setRefresh_token(jsonObject.getString("refresh_token"));
        stravaAuth.setClientId(jsonObject.getInt("client_id"));
        stravaAuth.setClientSecret(jsonObject.getString("client_secret"));
        athlete.setStrava_auth(stravaAuth);
        DatabaseHandler db = new DatabaseHandler(getContext());
        athlete.setRemote_update(1);
        db.athleteTable.addAthlete(athlete);

        settings.setLoggedIn(true);
        settings.setAthleteId(athlete.getId());
        settings.setUserId(athlete.getEmail());
        settings.setPassword(athlete.getPassword());
        settings.setFetchAllWorkouts(true);
        settings.save();
    }

}