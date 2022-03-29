package com.example.speedrunapp;

public class User {
    private String userId;
    private String username;
    private String country = "default";
    private String colorStart = "#FFFFFF";
    private String colorEnd = "#FFFFFF";

    public User(String id, String username, String country, String colSta, String colEnd){
        this.userId = id;
        this.username = username;
        this.country = country;
        this. colorStart = colSta;
        this.colorEnd = colEnd;
    }

    public User(String username){
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getCountry() {
        return country;
    }

    public String getColorStart() {
        return colorStart;
    }

    public String getColorEnd() {
        return colorEnd;
    }
}
