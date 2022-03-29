package com.example.speedrunapp;

/*
    SLY 4 EPISODES: jdrqe5lk

    SLY 3 LEVELS: z27wr5k0

    SLY 2 ANY% EPISODES: 824g7n25
    SLY 2 100% EPISODES: 9kvqlodg

    SLY 1 LEVELS: xd405p89

 */

public class Category {
    private String categoryId;
    private String categoryName;

    // Category
    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }


    @Override
    public String toString() {
        return categoryName;
    }

    public String getCategoryId(){
        return this.categoryId;
    }

    public String getCategoryName(){
        return categoryName;
    }
}
