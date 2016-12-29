package bataemperor.com.showmethecalories;

import android.app.Application;


import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;

/**
 * Created by aleksandar on 29.12.16..
 */

public class App extends Application {
    private static App instance;
    private ClarifaiClient clarifaiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        clarifaiClient = new ClarifaiBuilder("QdnCcIurkcUp_ECPBECUWAPG9koTBU5SYVS3CDtC", "4LS-ewqJI1A2MPwGeAmN4yXhGXN_q5tus6FTHx_H")
                .buildSync();
        super.onCreate();
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
}
