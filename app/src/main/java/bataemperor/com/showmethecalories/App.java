package bataemperor.com.showmethecalories;

import android.app.Application;


import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aleksandar on 29.12.16..
 */

public class App extends Application {
    private static App instance;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nutritionix.com/v1_1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static App getInstance() {
        return instance;
    }


    public Retrofit getRetrofit() {
        if (retrofit == null) {
            throw new IllegalStateException("Cannot use Retrofit before initialized");
        }
        return retrofit;
    }
}
