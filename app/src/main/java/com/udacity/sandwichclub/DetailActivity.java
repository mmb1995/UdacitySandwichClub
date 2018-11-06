package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Log.i(TAG, json);
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Sets the text for the origin textView
        TextView originTv = (TextView) findViewById(R.id.origin_tv);

        if (!sandwich.getPlaceOfOrigin().equals("")) {
            originTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            originTv.setText("Unknown");
        }

        // Sets the text for the description TextView
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
        descriptionTextView.setText(sandwich.getDescription());

        // populates the alsoKnownAs TextView with a list of alternate names for the sandwich
        TextView alternateNamesTextView = (TextView) findViewById(R.id.also_known_tv);
        StringBuilder otherNames = new StringBuilder();
        List<String> namesList = sandwich.getAlsoKnownAs();

        if (namesList.size() == 0) {
            // For empty list
            alternateNamesTextView.setText("Unknown");
        } else {
            for (int i = 0; i < namesList.size() - 1; i++) {
                otherNames.append(namesList.get(i) + ", ");
            }
            otherNames.append(namesList.get(namesList.size() - 1));
            alternateNamesTextView.setText(otherNames.toString());
        }

        // Populates the ingredient TextView with the ingredients in the sandwich
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        StringBuilder ingredients = new StringBuilder();
        List<String> ingredientsList = sandwich.getIngredients();

        if (ingredientsList.size() == 0) {
            // For empty list
            ingredientsTextView.setText("Unknown");
        } else {
            for (int i = 0; i < ingredientsList.size() - 1; i++) {
                ingredients.append(ingredientsList.get(i) + ", ");
            }
            ingredients.append(ingredientsList.get(ingredientsList.size() - 1));
            ingredientsTextView.setText(ingredients.toString());
        }
    }
}
