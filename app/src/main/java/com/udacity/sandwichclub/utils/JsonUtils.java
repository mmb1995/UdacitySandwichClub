package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JsonUtils {
    // Tag for debugging
    private static final String TAG = "JsonUtils";

    // Constant Values that correspond to the key values in the given json string
    private static final String MAIN_NAME_PARAM = "mainName";
    private static final String ALSO_KNOWN_AS_PARAM = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN_PARAM = "placeOfOrigin";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String IMAGE_PARAM = "image";
    private static final String INGREDIENTS_PARAM = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Log.i("TAG", json);
        if (json == null || json == "") {
            return null;
        }
        // Creates empty sandwich object
        Sandwich sandwich = new Sandwich();
        try {
            Log.i(TAG, "Parsing JSON");
            // Creates the JSON object
            JSONObject sandwichJsonObject = new JSONObject(json);

            // Gets the JsonObject containing the information on the sandwiches names
            JSONObject nameObject = sandwichJsonObject.getJSONObject("name");

            // Gets and sets the mainName property
            String mainName = nameObject.getString(MAIN_NAME_PARAM);
            sandwich.setMainName(mainName);

            // Gets and sets the list of alternate names
            List<String> alternateNames = new LinkedList<>();
            JSONArray namesArray = nameObject.getJSONArray(ALSO_KNOWN_AS_PARAM);

            for (int i = 0; i < namesArray.length(); i++) {
                alternateNames.add(namesArray.getString(i));
            }
            sandwich.setAlsoKnownAs(alternateNames);

            // Gets and sets the place of origin
            String placeOfOrigin = sandwichJsonObject.getString(PLACE_OF_ORIGIN_PARAM);
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            // Gets and sets the description
            String description = sandwichJsonObject.getString(DESCRIPTION_PARAM);
            sandwich.setDescription(description);

            // Gets and sets the image location
            String imageLink = sandwichJsonObject.getString(IMAGE_PARAM);
            sandwich.setImage(imageLink);

            // Gets and sets the list of ingredients
            List<String> ingredientsList = new LinkedList<>();
            JSONArray ingredientsArray = sandwichJsonObject.getJSONArray(INGREDIENTS_PARAM);

            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsList.add(ingredientsArray.getString(i));
            }
            sandwich.setIngredients(ingredientsList);

            // Returns the sandwich object
            return sandwich;
        } catch (JSONException e) {
            // JSON Parsing Failed
            Log.e(TAG, "Unable to parse JSON");
            return null;
        }
    }

}
