package com.example.speedrunapp;

import java.util.ArrayList;

public class Game {
    private String gameId;
    private String gameName;
    private ArrayList<Category> categories = new ArrayList<>();

    public Game(String id){
        this.gameId = id;
    }

    public String getGameId() {
        return this.gameId;
    }

    public void addCategory(String id) {
        categories.add(new Category(id));
    }

    public Category getCategory(int i) {
        return categories.get(i);
    }
}
