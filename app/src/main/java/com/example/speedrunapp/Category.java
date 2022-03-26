package com.example.speedrunapp;

public class Category {
    private String category_id;
    private String categoryName;

    public Category(String category_id) {
        this.category_id = category_id;

    }

    public String getCategoryId(){
        return this.category_id;
    }
}
