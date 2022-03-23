package com.example.speedrunapp;

public class Category {
    private String categoryId;
    private String categoryName;

    public Category(String id) {
        this.categoryId = id;
    }

    public String getCategoryId(){
        return this.categoryId;
    }
}
