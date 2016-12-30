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
    private ClarifaiClient clarifaiClient;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        clarifaiClient = new ClarifaiBuilder("QdnCcIurkcUp_ECPBECUWAPG9koTBU5SYVS3CDtC", "4LS-ewqJI1A2MPwGeAmN4yXhGXN_q5tus6FTHx_H")
                .buildSync();
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nutritionix.com/v1_1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public ClarifaiClient clarifaiClient() {
        if (clarifaiClient == null) {
            throw new IllegalStateException("Cannot use Clarifai client before initialized");
        }
        return clarifaiClient;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            throw new IllegalStateException("Cannot use Retrofit before initialized");
        }
        return retrofit;
    }
}
