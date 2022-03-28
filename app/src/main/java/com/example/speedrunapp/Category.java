package com.example.speedrunapp;

public class Category {
    private String categoryId;
    private String categoryName;

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
