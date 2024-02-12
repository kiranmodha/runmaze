package com.example.runmaze;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.runmaze.data.DatabaseHandler;
import com.example.runmaze.data.model.Athlete;
import com.example.runmaze.data.model.City;
import com.example.runmaze.data.model.Club;
import com.example.runmaze.data.model.Company;
import com.example.runmaze.utils.CommonUtils;
import com.example.runmaze.utils.Settings;
import com.example.runmaze.data.model.StravaAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AthleteFragment extends Fragment {

    Button btnSubmit, btnDate;
    EditText edtName, edtEmail, edtPassword, edtReenter, edtBirthdate;
    Spinner spnGender, spnCity, spnClub, spnCompany;

    int mYear, mMonth, mDay;

    // private static final String ID = "id";


    Settings settings;

    //private int recordId;
    private int mMode = 0;  //  0- Add Mode     1-Edit Mode

    public AthleteFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static AthleteFragment newInstance(int mode) {
        AthleteFragment fragment = new AthleteFragment();
        Bundle args = new Bundle();
        args.putInt("mode", mode);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMode = getArguments().getInt("mode"); // Edit Mode = 1
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_athlete, container, false);

        btnSubmit = fragmentView.findViewById(R.id.data_btnSubmit);
        btnDate = fragmentView.findViewById((R.id.data_btnDate));


        edtName = fragmentView.findViewById(R.id.data_edtName);
        edtEmail = fragmentView.findViewById(R.id.data_edtEmail);
        edtPassword = fragmentView.findViewById(R.id.data_edtPassword);
        edtReenter = fragmentView.findViewById(R.id.data_edtReenter);
        edtBirthdate = fragmentView.findViewById(R.id.data_edtBirthdate);

        spnGender = fragmentView.findViewById(R.id.data_spnGender);
        spnCity = fragmentView.findViewById(R.id.data_spnCity);
        spnClub = fragmentView.findViewById(R.id.data_spnClub);
        spnCompany = fragmentView.findViewById(R.id.data_spnCompany);


        settings = new Settings(getContext());

        //builder = new AlertDialog.Builder(getContext());

        setSpnCity();
        setSpnClub();
        setSpnCompany();

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                   btnSubmit.setEnabled(false);
                   if (mMode == 0) {
                       submitData();
                   } else {
                       updateToLocalDatabase();
                   }
                }
            }
        });

        if (mMode == 1) {
            loadData();
        }

        return fragmentView;
    }


    public void loadData() {
        edtEmail.setEnabled(false);
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        Athlete athlete = dbHandler.athleteTable.athleteByID(settings.getAthleteId());
        dbHandler.close();

        edtEmail.setText(athlete.getEmail());
        edtName.setText(athlete.getName());
        edtPassword.setText(athlete.getPassword());
        edtReenter.setText(athlete.getPassword());
        edtBirthdate.setText(CommonUtils.getStringDateDMY(athlete.getBirthdate()));

        int indexPos = 0;
        for (int i = 0; i < spnCity.getCount(); i++) {
            if (Integer.parseInt(((City) spnCity.getItemAtPosition(i)).getId()) == athlete.getCity()) {
                indexPos = i;
                break;
            }
        }
        spnCity.setSelection(indexPos);

        indexPos = 0;
        for (int i = 0; i < spnClub.getCount(); i++) {
            if (Integer.parseInt(((Club) spnClub.getItemAtPosition(i)).getId()) == athlete.getClub()) {
                indexPos = i;
                break;
            }
        }
        spnClub.setSelection(indexPos);

        indexPos = 0;
        for (int i = 0; i < spnCompany.getCount(); i++) {
            if (Integer.parseInt(((Company) spnCompany.getItemAtPosition(i)).getId()) == athlete.getCompany()) {
                indexPos = i;
                break;
            }
        }
        spnCompany.setSelection(indexPos);

        indexPos = 0;
        for (int i = 0; i < spnGender.getCount(); i++) {
            if (spnGender.getItemAtPosition(i).toString().substring(0, 1).equals(athlete.getGender())) {
                indexPos = i;
                break;
            }
        }
        spnGender.setSelection(indexPos);


    }


    String getJSON() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", edtName.getText().toString());
            jsonObject.put("email", edtEmail.getText().toString());
            jsonObject.put("password", edtPassword.getText().toString());
            jsonObject.put("gender", spnGender.getSelectedItem().toString().substring(0, 1));
            jsonObject.put("birthdate", getFormattedDateInMySqlFormat(edtBirthdate.getText().toString()));
            jsonObject.put("city", ((City) (spnCity.getSelectedItem())).getId());
            jsonObject.put("club", ((Club) (spnClub.getSelectedItem())).getId());
            jsonObject.put("company", ((Company) (spnCompany.getSelectedItem())).getId());
            jsonObject.put("strava_athlete_id", JSONObject.NULL);
            jsonObject.put("access_token", JSONObject.NULL);
            jsonObject.put("expires_at", JSONObject.NULL);
            jsonObject.put("refresh_token", JSONObject.NULL);
            jsonObject.put("remote_update", 2);
            jsonObject.put("client_id", settings.getClientId());
            jsonObject.put("client_secret", settings.getClientSecret());
            jsonObject.put("direct_strava", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private void setSpnCity() {

        DatabaseHandler db = new DatabaseHandler(getContext());
        ArrayList<City> cityList = db.cityTable.getCities();

/*        ArrayList<City> cityList = new ArrayList<>();
        cityList.add(new City("1", "Ahmedabad"));
        cityList.add(new City("2", "Surat"));
        cityList.add(new City("3", "Rajkot"));
        cityList.add(new City("4", "Vadodara"));*/

        //fill data in spinner
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(getContext(), android.R.layout.simple_spinner_dropdown_item, cityList);
        spnCity.setAdapter(adapter);
    }

    private void setSpnClub() {
        DatabaseHandler db = new DatabaseHandler(getContext());
        ArrayList<Club> clubList = db.clubTable.getClubs();



        //fill data in spinner
        ArrayAdapter<Club> adapter = new ArrayAdapter<Club>(getContext(), android.R.layout.simple_spinner_dropdown_item, clubList);
        spnClub.setAdapter(adapter);
    }


    private void setSpnCompany() {

        DatabaseHandler db = new DatabaseHandler(getContext());
        ArrayList<Company> companyList = db.companyTable.getCompanies();

/*        ArrayList<Company> companyList = new ArrayList<>();
        companyList.add(new Company("1", "Torrent Power Limited"));
        companyList.add(new Company("2", "Other Company"));
        companyList.add(new Company("3", "Third Company"));*/
        //fill data in spinner
        ArrayAdapter<Company> adapter = new ArrayAdapter<Company>(getContext(), android.R.layout.simple_spinner_dropdown_item, companyList);
        spnCompany.setAdapter(adapter);
    }


    public void showDateDialog() {

        if (mYear == 0) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        edtBirthdate.setError(null);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                edtBirthdate.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (monthOfYear + 1)) + "/" + year);
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    boolean validateData() {
        if (!edtPassword.getText().toString().equals(edtReenter.getText().toString())) {
            edtReenter.setError("Passwords do not match");
            return false;
        } else {
            edtReenter.setError(null);
        }
        if (edtEmail.getText().toString().trim().length() == 0) {
            edtEmail.setError("Please enter email");
            return false;
        } else {
            edtEmail.setError(null);
        }

        String regex = "^(.+)@(.+)$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        //Create instance of matcher
        Matcher matcher = pattern.matcher(edtEmail.getText().toString());
        if (!matcher.matches()) {
            edtEmail.setError("Enter valid email address");
            return false;
        } else {
            edtEmail.setError(null);
        }


        if (edtName.getText().toString().trim().length() == 0) {
            edtName.setError("Please enter name");
            return false;
        } else {
            edtName.setError(null);
        }
        return true;
    }

    void submitData() {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody requestBody = RequestBody.create(getJSON(), mediaType);

        //String server_url = "https://runmaze-api.000webhostapp.com/api/athlete/create.php";
        String server_url = "https://eklavyarun.in/api/athlete/create.php";

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
                        // Log.d("Kiran Response", strResponse);
                        try {
                            JSONObject jsonObject = new JSONObject(strResponse);
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                int id = jsonObject.getInt("id");
                                addToLocalDatabase(id);
                                Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_LONG).show();
                                getActivity().onBackPressed();
                            } else {
                                //Error creating athlete
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Error while creating account", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }


    void updateToLocalDatabase() {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        Athlete athlete = dbHandler.athleteTable.athleteByID(settings.getAthleteId());
        athlete.setName(edtName.getText().toString());
        athlete.setEmail(edtEmail.getText().toString());
        athlete.setPassword(edtPassword.getText().toString());
        athlete.setGender(spnGender.getSelectedItem().toString().substring(0, 1));
        athlete.setBirthdate(getFormattedDateInMySqlFormat(edtBirthdate.getText().toString()));
        athlete.setCity(Integer.parseInt(((City) (spnCity.getSelectedItem())).getId()));
        athlete.setClub(Integer.parseInt(((Club) (spnClub.getSelectedItem())).getId()));
        athlete.setCompany(Integer.parseInt(((Company) (spnCompany.getSelectedItem())).getId()));
        athlete.setRemote_update(0);

        dbHandler.athleteTable.updateAthlete(athlete);
        dbHandler.close();
        Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }

    void addToLocalDatabase(int id) {

        Athlete athlete = new Athlete();
        athlete.setId(id);
        athlete.setName(edtName.getText().toString());
        athlete.setEmail(edtEmail.getText().toString());
        athlete.setPassword(edtPassword.getText().toString());
        athlete.setGender(spnGender.getSelectedItem().toString().substring(0, 1));
        athlete.setBirthdate(getFormattedDateInMySqlFormat(edtBirthdate.getText().toString()));
        athlete.setCity(Integer.parseInt(((City) (spnCity.getSelectedItem())).getId()));
        athlete.setClub(Integer.parseInt(((Club) (spnClub.getSelectedItem())).getId()));
        athlete.setCompany(Integer.parseInt(((Company) (spnCompany.getSelectedItem())).getId()));

        StravaAuth stravaAuth = new StravaAuth();
        stravaAuth.setClientId(settings.getClientId());
        stravaAuth.setClientSecret(settings.getClientSecret());
        athlete.setStrava_auth(stravaAuth);
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
        dbHandler.athleteTable.addAthlete(athlete);
        dbHandler.close();
    }


    public String getFormattedDateInMySqlFormat(String dateString) {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Convert string into Date
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat dateFormatMySql = new SimpleDateFormat("yyyy-MM-dd");
        return (dateFormatMySql.format(date));
    }
}