package bataemperor.com.showmethecalories.retrofit;

import bataemperor.com.showmethecalories.model.Item;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by aleksandar on 29.12.16..
 */

public interface CaloriesApi {

    @GET("search/{subject}?results=0:20&fields=item_name,brand_name,item_id,nf_calories&appId=fa1b7512&appKey=a80bb72bfdd05ddb2d1afc2a83307ab9")
    Call<Item> listItems(@Path("subject") String subject);

}
