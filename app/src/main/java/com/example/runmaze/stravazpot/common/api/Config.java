package com.example.runmaze.stravazpot.common.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.runmaze.stravazpot.activity.model.AchievementType;
import com.example.runmaze.stravazpot.activity.model.ActivityType;
import com.example.runmaze.stravazpot.activity.model.PhotoSource;
import com.example.runmaze.stravazpot.activity.model.WorkoutType;
import com.example.runmaze.stravazpot.athlete.model.AthleteType;
import com.example.runmaze.stravazpot.athlete.model.FriendStatus;
import com.example.runmaze.stravazpot.athlete.model.MeasurementPreference;
import com.example.runmaze.stravazpot.authenticaton.model.Token;
import com.example.runmaze.stravazpot.club.model.ClubType;
import com.example.runmaze.stravazpot.club.model.Membership;
import com.example.runmaze.stravazpot.club.model.SkillLevel;
import com.example.runmaze.stravazpot.club.model.SportType;
import com.example.runmaze.stravazpot.club.model.Terrain;
import com.example.runmaze.stravazpot.common.model.Coordinates;
import com.example.runmaze.stravazpot.common.model.Distance;
import com.example.runmaze.stravazpot.common.model.Gender;
import com.example.runmaze.stravazpot.common.model.Percentage;
import com.example.runmaze.stravazpot.common.model.ResourceState;
import com.example.runmaze.stravazpot.common.model.Speed;
import com.example.runmaze.stravazpot.common.model.Temperature;
import com.example.runmaze.stravazpot.common.model.Time;
import com.example.runmaze.stravazpot.common.typeadapter.AchievementTypeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.ActivityTypeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.AthleteTypeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.ClubTypeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.CoordinatesTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.DistanceTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.FrameTypeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.FriendStatusTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.GenderTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.MeasurementPreferenceTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.MembershipTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.PercentageTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.PhotoSourceTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.ResolutionTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.ResourceStateTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.RouteSubtypeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.RouteTypeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.SeriesTypeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.SkillLevelTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.SpeedTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.SportTypeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.StreamTypeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.TemperatureTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.TerrainTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.TimeTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.TokenTypeAdapter;
import com.example.runmaze.stravazpot.common.typeadapter.WorkoutTypeTypeAdapter;
import com.example.runmaze.stravazpot.gear.model.FrameType;
import com.example.runmaze.stravazpot.route.model.RouteSubtype;
import com.example.runmaze.stravazpot.route.model.RouteType;
import com.example.runmaze.stravazpot.stream.model.Resolution;
import com.example.runmaze.stravazpot.stream.model.SeriesType;
import com.example.runmaze.stravazpot.stream.model.StreamType;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
// Commented to avoid error by Kiran on 22-01-2022
//import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
// Commented to avoid error by Kiran on 22-01-2022
//import retrofit2.converter.gson.GsonConverterFactory;

public abstract class Config {

    private Retrofit retrofit;

    public Config(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    protected static Retrofit createRetrofit(boolean debug, String baseURL, Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if(debug){
           // HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
           // interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
           // builder.addInterceptor(interceptor);
        }
        for(Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL)
                .client(client)
                // Commented to avoid error by Kiran on 22-01-2022
                //   .addConverterFactory(GsonConverterFactory.create(makeGson()))
                .build();

        return retrofit;
    }

    private static Gson makeGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .registerTypeAdapter(Distance.class, new DistanceTypeAdapter())
                .registerTypeAdapter(ResourceState.class, new ResourceStateTypeAdapter())
                .registerTypeAdapter(Gender.class, new GenderTypeAdapter())
                .registerTypeAdapter(FriendStatus.class, new FriendStatusTypeAdapter())
                .registerTypeAdapter(AthleteType.class, new AthleteTypeTypeAdapter())
                .registerTypeAdapter(MeasurementPreference.class, new MeasurementPreferenceTypeAdapter())
                .registerTypeAdapter(Time.class, new TimeTypeAdapter())
                .registerTypeAdapter(FrameType.class, new FrameTypeTypeAdapter())
                .registerTypeAdapter(RouteType.class, new RouteTypeTypeAdapter())
                .registerTypeAdapter(RouteSubtype.class, new RouteSubtypeTypeAdapter())
                .registerTypeAdapter(Percentage.class, new PercentageTypeAdapter())
                .registerTypeAdapter(Coordinates.class, new CoordinatesTypeAdapter())
                .registerTypeAdapter(ActivityType.class, new ActivityTypeTypeAdapter())
                .registerTypeAdapter(StreamType.class, new StreamTypeTypeAdapter())
                .registerTypeAdapter(SeriesType.class, new SeriesTypeTypeAdapter())
                .registerTypeAdapter(Resolution.class, new ResolutionTypeAdapter())
                .registerTypeAdapter(ClubType.class, new ClubTypeTypeAdapter())
                .registerTypeAdapter(SportType.class, new SportTypeTypeAdapter())
                .registerTypeAdapter(Membership.class, new MembershipTypeAdapter())
                .registerTypeAdapter(SkillLevel.class, new SkillLevelTypeAdapter())
                .registerTypeAdapter(Terrain.class, new TerrainTypeAdapter())
                .registerTypeAdapter(Speed.class, new SpeedTypeAdapter())
                .registerTypeAdapter(PhotoSource.class, new PhotoSourceTypeAdapter())
                .registerTypeAdapter(WorkoutType.class, new WorkoutTypeTypeAdapter())
                .registerTypeAdapter(AchievementType.class, new AchievementTypeTypeAdapter())
                .registerTypeAdapter(Token.class, new TokenTypeAdapter())
                .registerTypeAdapter(Temperature.class, new TemperatureTypeAdapter())
                .create();
    }
}
