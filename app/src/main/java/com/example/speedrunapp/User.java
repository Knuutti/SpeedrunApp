package com.example.speedrunapp;

public class User {
    private String id;
    private String name;

    ReadJSON json = ReadJSON.getInstance();

    public User(String id){
        this.id = id;
        this.name = json.readUserData(json.getUserDataJSON(id));
    }
}
