package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {
        Sandwich result;
        String name;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject jsonSandwich = new JSONObject(json);
            name = jsonSandwich.getJSONObject("name").getString("mainName");
            placeOfOrigin = jsonSandwich.getString("placeOfOrigin");
            description = jsonSandwich.getString("description");
            image = jsonSandwich.getString("image");

            JSONArray nameArr = jsonSandwich.getJSONObject("name").getJSONArray("alsoKnownAs");
            for (int i = 0; i < nameArr.length(); i++) {
                alsoKnownAs.add(nameArr.getString(i));
            }

            JSONArray ingArr = jsonSandwich.getJSONArray("ingredients");
            for (int i = 0; i < ingArr.length(); i++) {
                ingredients.add(ingArr.getString(i));
            }

            result = new Sandwich(name, alsoKnownAs, placeOfOrigin, description, image, ingredients);

            return result;
        } catch (Exception e) {
            //To be polished
            return null;
        }
    }
}


//  {
//    "name":{
//      "mainName":"Bosna",
//      "alsoKnownAs":["Bosner"]
//    },
//    "placeOfOrigin":"Austria",
//    "description":"Bosna is a spicy Austrian fast food dish, said to have originated in either Salzburg or Linz. It is now popular all over western Austria and southern Bavaria.",
//    "image":"https://upload.wikimedia.org/wikipedia/commons/c/ca/Bosna_mit_2_Bratw%C3%BCrsten.jpg",
//    "ingredients":["White bread","Bratwurst","Onions","Tomato ketchup","Mustard","Curry powder"]
//  }