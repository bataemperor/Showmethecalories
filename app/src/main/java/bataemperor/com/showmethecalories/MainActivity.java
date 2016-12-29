package bataemperor.com.showmethecalories;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import clarifai2.api.ClarifaiResponse;
import clarifai2.api.ClarifaiUtil;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final int IMAGE = 300;
    ImageView iv;
    byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.ivImage);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nutritionix.com/v1_1/")
                .build();
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
                    new ATImage().execute();
                    break;
                }
        }
    }

    class ATImage extends AsyncTask<Void, Void, ClarifaiResponse<List<ClarifaiOutput<Concept>>>> {

        @Override
        protected ClarifaiResponse<List<ClarifaiOutput<Concept>>> doInBackground(Void... voids) {

            ConceptModel conceptModel = App.getInstance().clarifaiClient().getDefaultModels().foodModel();
            return conceptModel.predict()
                    .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(imageBytes)))
                    .executeSync();


        }

        @Override
        protected void onPostExecute(ClarifaiResponse<List<ClarifaiOutput<Concept>>> listClarifaiResponse) {
            super.onPostExecute(listClarifaiResponse);
            if (!listClarifaiResponse.isSuccessful()) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                return;
            }
            final List<ClarifaiOutput<Concept>> predictions = listClarifaiResponse.get();
            if (!predictions.isEmpty()) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
