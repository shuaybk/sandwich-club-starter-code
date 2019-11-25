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

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mAka_title_tv;
    TextView mAka_tv;
    TextView mIngredients_title_tv;
    TextView mIngredients_tv;
    TextView mOrigin_title_tv;
    TextView mOrigin_tv;
    TextView mDescription_title_tv;
    TextView mDescription_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mAka_title_tv = (TextView) findViewById(R.id.aka_title_tv);
        mAka_tv = (TextView) findViewById(R.id.aka_tv);
        mIngredients_title_tv = (TextView) findViewById(R.id.ingredients_title_tv);
        mIngredients_tv = (TextView) findViewById(R.id.ingredients_tv);
        mOrigin_title_tv = (TextView) findViewById(R.id.origin_title_tv);
        mOrigin_tv = (TextView) findViewById(R.id.origin_tv);
        mDescription_title_tv = (TextView) findViewById(R.id.description_title_tv);
        mDescription_tv = (TextView) findViewById(R.id.description_tv);

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
        System.out.println(json);
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        //Set the texts for the view
        setTitle(sandwich.getMainName());
        setListText(sandwich.getAlsoKnownAs(), mAka_title_tv, mAka_tv);
        setListText(sandwich.getIngredients(), mIngredients_title_tv, mIngredients_tv);
        setText(sandwich.getPlaceOfOrigin(), mOrigin_title_tv, mOrigin_tv);
        setText(sandwich.getDescription(), mDescription_title_tv, mDescription_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }

    //Function to display list text items if they exist, otherwise make it and its title invisible
    private void setListText(List<String> list, TextView viewTitle, TextView viewData) {
        if (list.isEmpty()) {
            viewTitle.setVisibility(View.GONE);
            viewData.setVisibility(View.GONE);
        } else {
            String tempStr = list.toString();
            viewData.setText(tempStr.substring(1,tempStr.length()-1));
        }
    }

    //Function to display text items if they exist, otherwise make it and its title invisible
    private void setText(String str, TextView viewTitle, TextView viewData) {
        if (str.length() == 0) {
            viewTitle.setVisibility(View.GONE);
            viewData.setVisibility(View.GONE);
        } else {
            viewData.setText(str);
        }
    }
}
