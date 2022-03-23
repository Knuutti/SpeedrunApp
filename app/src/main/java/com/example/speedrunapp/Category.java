package com.example.speedrunapp;

public class Category {
    String categoryId;
    String categoryName;

    public Category(String id) {
        this.categoryId = id;
    }

    public String getCategoryId(){
        return this.categoryId;
    }
}
