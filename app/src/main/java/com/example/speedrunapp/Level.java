package com.example.speedrunapp;

import java.util.ArrayList;

public class Level {
    ReadJSON json = ReadJSON.getInstance();
    private String levelId;
    private String levelName;
    private ArrayList<Category> categories;

    public Level(String levelId, String levelName) {
        this.levelId = levelId;
        this.levelName = levelName;
        this.categories = json.getLevelCategoryData(levelId);
    }

    public String getLevelId() {
        return levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }
}
