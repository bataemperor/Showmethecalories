package bataemperor.com.showmethecalories.retrofit;

import java.util.List;

import bataemperor.com.showmethecalories.retrofit.model.Item;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by aleksandar on 29.12.16..
 */

public interface Retrofit {

    @GET("search/{subject}/results=0:20")
    Call<List<Item>> listItems(@Path("subject") String subject,
                               @Query("address") String address);

}
