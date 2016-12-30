package bataemperor.com.showmethecalories;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bataemperor.com.showmethecalories.adapter.CaloriesAdapter;
import bataemperor.com.showmethecalories.model.FoodWrapper;
import bataemperor.com.showmethecalories.retrofit.CaloriesApi;
import bataemperor.com.showmethecalories.model.Item;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity {
    public static final int IMAGE = 300;
    ImageView iv;
    byte[] imageBytes;
    List<FoodWrapper> list;
    RecyclerView rv;
    ProgressBar pb;
    TextView tvIm;
    CaloriesAdapter caloriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.ivImage);
        rv = (RecyclerView) findViewById(R.id.rvNutri);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                lm.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        pb = (ProgressBar) findViewById(R.id.pb);
        tvIm = (TextView) findViewById(R.id.tvIm);
        list = new ArrayList<>();
        caloriesAdapter = new CaloriesAdapter(list, MainActivity.this);
        rv.setAdapter(caloriesAdapter);
    }

    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
        startActivityForResult(intent, IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case IMAGE:
                imageBytes = Utility.retrieveSelectedImage(this, data);
                if (imageBytes != null) {
                    iv.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
                    tvIm.setVisibility(View.GONE);
                    new ATImage().execute();
                    break;
                }
        }
    }

    class ATImage extends AsyncTask<Void, Void, List<FoodWrapper>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
            caloriesAdapter.clear();
        }

        @Override
        protected List<FoodWrapper> doInBackground(Void... voids) {
            ClarifaiClient clarifaiClient = null;
            try {
                clarifaiClient =
                        new ClarifaiBuilder("QdnCcIurkcUp_ECPBECUWAPG9koTBU5SYVS3CDtC", "4LS-ewqJI1A2MPwGeAmN4yXhGXN_q5tus6FTHx_H")
                                .client(new OkHttpClient.Builder()
                                        .connectTimeout(60, TimeUnit.SECONDS)
                                        .readTimeout(60, TimeUnit.SECONDS)
                                        .writeTimeout(60, TimeUnit.SECONDS)
                                        .build())
                                .buildSync();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            List<FoodWrapper> list = new ArrayList<>();
            list.add(new FoodWrapper("Food", "kCal"));

            if (clarifaiClient == null) return list;
            ConceptModel conceptModel = clarifaiClient.getDefaultModels().foodModel();
            ClarifaiResponse<List<ClarifaiOutput<Concept>>> listClarifaiResponse = conceptModel.predict()
                    .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(imageBytes)))
                    .executeSync();


            if (!listClarifaiResponse.isSuccessful()) {
                return list;
            }

            CaloriesApi service = App.getInstance().getRetrofit().create(CaloriesApi.class);
            Call<Item> call = service.listItems(listClarifaiResponse.get().get(0).data().get(0).name());
            Call<Item> call1 = service.listItems(listClarifaiResponse.get().get(0).data().get(1).name());
            Call<Item> call2 = service.listItems(listClarifaiResponse.get().get(0).data().get(2).name());
            Call<Item> call3 = service.listItems(listClarifaiResponse.get().get(0).data().get(3).name());
            Call<Item> call4 = service.listItems(listClarifaiResponse.get().get(0).data().get(4).name());
            try {
                Item item;
                item = call.execute().body();
                FoodWrapper foodWrapper = new FoodWrapper();
                foodWrapper.setCalorie(item.getHits().get(0).getFields().getNfCalories().toString());
                foodWrapper.setFood(listClarifaiResponse.get().get(0).data().get(0).name());
                list.add(foodWrapper);

                item = call1.execute().body();
                FoodWrapper foodWrapper2 = new FoodWrapper();
                foodWrapper2.setCalorie(item.getHits().get(0).getFields().getNfCalories().toString());
                foodWrapper2.setFood(listClarifaiResponse.get().get(0).data().get(1).name());
                list.add(foodWrapper2);

                item = call2.execute().body();
                FoodWrapper foodWrapper3 = new FoodWrapper();
                foodWrapper3.setCalorie(item.getHits().get(0).getFields().getNfCalories().toString());
                foodWrapper3.setFood(listClarifaiResponse.get().get(0).data().get(2).name());
                list.add(foodWrapper3);

                item = call3.execute().body();
                FoodWrapper foodWrapper4 = new FoodWrapper();
                foodWrapper4.setCalorie(item.getHits().get(0).getFields().getNfCalories().toString());
                foodWrapper4.setFood(listClarifaiResponse.get().get(0).data().get(3).name());
                list.add(foodWrapper4);

                item = call4.execute().body();
                FoodWrapper foodWrapper5 = new FoodWrapper();
                foodWrapper5.setCalorie(item.getHits().get(0).getFields().getNfCalories().toString());
                foodWrapper5.setFood(listClarifaiResponse.get().get(0).data().get(4).name());
                list.add(foodWrapper5);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;

        }

        @Override
        protected void onPostExecute(List<FoodWrapper> list) {
            super.onPostExecute(list);
            pb.setVisibility(View.GONE);
            caloriesAdapter.addList(list);
            if (list.size() == 1) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
