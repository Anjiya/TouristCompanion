package com.example.lenovo.touristcompanion;

/**
 * Created by LENOVO on 05-Jan-18.
 */

public class places {
    private String place_id;
    private String category, location;
    private String place_name;
    private int votes;
    private String reviews;

    public places() {

    }

    public places(String place_id, String category, String place_name, String location, int votes, String reviews)
    {
        this.place_id = place_id;
        this.category = category;
        this.place_name = place_name;
        this.location = location;
        this.votes = votes;
        this.reviews = reviews;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getCategory() {
        return category;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getLocation() {
        return location;
    }

    public int getVotes() {
        return votes;
    }

    public String getReviews() {
        return reviews;
    }
}
