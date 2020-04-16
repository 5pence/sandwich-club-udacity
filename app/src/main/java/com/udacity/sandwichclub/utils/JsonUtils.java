package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject base = new JSONObject(json);
        JSONObject name = base.getJSONObject(NAME);
        String mainName = name.getString(MAIN_NAME);
        JSONArray aka = name.getJSONArray(ALSO_KNOWN_AS);
        List<String> akaList = new ArrayList<>();

        if (aka != null && aka.length() > 0) {
            for (int i = 0; i < aka.length(); i++) {
                akaList.add(aka.get(i).toString());
            }
        }

        String placeOfOrigin = base.getString(PLACE_OF_ORIGIN);
        String description = base.getString(DESCRIPTION);
        String image = base.getString(IMAGE);
        JSONArray ingredients = base.getJSONArray(INGREDIENTS);
        List<String> listOfIngredients = new ArrayList<>();

        if (ingredients != null && ingredients.length() > 0) {
            for (int i = 0; i < ingredients.length(); i++ ) {
                listOfIngredients.add(ingredients.get(i).toString());
            }
        }

        return new Sandwich(mainName, akaList, placeOfOrigin, description, image, listOfIngredients);
    }
}
