package com.example.lenovo.touristcompanion;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LENOVO on 03-Jan-18.
 */

public class DataParser {

    String placeName = "--NA--";
    String vicinity = "--NA--";
    String latitude = "0.0";
    String longitude = "0.0";
    String reference = "";

    FirebaseDatabase firebasedatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRefPlace = firebasedatabase.getReference("places2");

    String TAG = "placevicinity";
    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)
    {
        HashMap<String, String> googlePlacesMap = new HashMap<>();

        Log.d("DataParser","jsonobject ="+googlePlaceJson.toString());

        try {
            if(!googlePlaceJson.isNull("name"))
            {
                placeName = googlePlaceJson.getString("name");
            }
            if(!googlePlaceJson.isNull("vicinity"))
            {
                vicinity = googlePlaceJson.getString("vicinity");
            }
//            if(!googlePlaceJson.isNull("lat"))
//            {
//                latitude = googlePlaceJson.getString("lat");
//            }
//            if(!googlePlaceJson.isNull("lng"))
//            {
//                longitude = googlePlaceJson.getString("lng");
//            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJson.getString("reference");

            googlePlacesMap.put("place_name", placeName);
            googlePlacesMap.put("vicinity", vicinity);
            googlePlacesMap.put("lat", latitude);
            googlePlacesMap.put("lng", longitude);
            googlePlacesMap.put("reference", reference);

            Log.e(TAG, placeName + ": " + vicinity + " " + latitude + ": " + longitude);

            Log.e(TAG, latitude + ": " + longitude);

            String place_id = myRefPlace.push().getKey();
            String category = "shopping", location = vicinity, place_name = placeName;
            int votes = 0;
            String reviews = "";
            places p = new places(place_id, category, placeName, vicinity, latitude, longitude, votes, reviews);
            myRefPlace.push().setValue(p);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlacesMap;
    }

    //stores all the places in a list
    private List<HashMap<String, String >> getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for(int i=0; i<count; i++)
        {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    public List<HashMap<String, String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        Log.d("json data", jsonData);

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
}
