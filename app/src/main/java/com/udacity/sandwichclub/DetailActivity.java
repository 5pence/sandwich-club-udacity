package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    protected ImageView mImageImageView;
    protected TextView mAlsoKnownAsLabelTextView;
    protected TextView mAlsoKnownAsTextView;
    protected TextView mDescriptionLabelTextView;
    protected TextView mDescriptionTextView;
    protected TextView mIngredientsLabelTextView;
    protected TextView mIngredientsTextView;
    protected TextView mPlaceOfOriginTextView;
    protected TextView mPlaceOfOriginLabelTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mImageImageView = findViewById(R.id.image_iv);
        mAlsoKnownAsTextView = findViewById(R.id.also_known_tv);
        mAlsoKnownAsLabelTextView = findViewById(R.id.also_known_as_label_tv);
        mDescriptionLabelTextView = findViewById(R.id.description_label_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mIngredientsLabelTextView = findViewById(R.id.ingredients_label_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mPlaceOfOriginLabelTextView = findViewById(R.id.place_of_origin_label_tv);
        mPlaceOfOriginTextView = findViewById(R.id.place_of_origin_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // TODO: Populate views with sandwich fields
        setTitle(sandwich.getMainName());

        if (!sandwich.getImage().isEmpty()) {
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(mImageImageView);
        }
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownAsLabelTextView.setVisibility(View.VISIBLE);
            mAlsoKnownAsTextView.setVisibility(View.VISIBLE);
            List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
            mAlsoKnownAsTextView.append(" ");
            int count = alsoKnownAsList.size();
            for (int i=0; i < count; i++) {
                mAlsoKnownAsTextView.append(alsoKnownAsList.get(i));
                if (i == count-1) {
                    mAlsoKnownAsTextView.append(".");
                } else {
                    mAlsoKnownAsTextView.append(", ");
                }
            }
        }
        if (!sandwich.getDescription().isEmpty()) {
            mDescriptionLabelTextView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setText(sandwich.getDescription());
        }
        if (!sandwich.getIngredients().isEmpty()) {
            mIngredientsLabelTextView.setVisibility(View.VISIBLE);
            mIngredientsTextView.setVisibility(View.VISIBLE);
            List<String> ingredientsList = sandwich.getIngredients();
            mIngredientsTextView.append(" ");
            int count = ingredientsList.size();
            for (int i=0; i < count; i++) {
                mIngredientsTextView.append(ingredientsList.get(i));
                if (i == count-1) {
                    mIngredientsTextView.append(".");
                } else {
                    mIngredientsTextView.append(", ");
                }
            }
        }
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            mPlaceOfOriginLabelTextView.setVisibility(View.VISIBLE);
            mPlaceOfOriginTextView.setVisibility(View.VISIBLE);
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }



    }
}
