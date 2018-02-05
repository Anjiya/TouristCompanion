package com.example.lenovo.touristcompanion;

/**
 * Created by LENOVO on 13-Dec-17.
 */

public class User {
    private String id, email, fname, lname;
    private String placesVisited;

    public User() {

    }

    public User(String id, String fname, String lname, String placesVisited) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.placesVisited = placesVisited;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPlacesVisited()
    {
        return placesVisited;
    }
}
