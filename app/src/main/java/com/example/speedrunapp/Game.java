package com.example.speedrunapp;

import java.util.ArrayList;

/*
LIST OF SLY GAMES:

SLY COOPER AND THE THIEVIUS RACCOONUS
    GAME ID = nd2egvd0
    CATEGORIES:
        ANY% = 02q8ly2y
        ALL KEYS = wk61pexd
        ALL VAULTS = 7kj1gx3k
        100% = 824mrw25

SLY 2: BAND OF THIEVES
    GAME ID = 4d79zg17
    CATEGORIES:
        ANY% = zd3l8ydn
        100% = 9kvj7jkg

SLY 3: HONOR AMONG THIEVES
    GAME ID = 9d3rkgdl
    CATEGORIES:
        ANY% = 9d8jx7kn
        100% = zdnz07dq

SLY COOPER: THIEVES IN TIME
    GAME ID = o1y9526q
    CATEGORIES:
        ANY% = 5dwy8nkg
        100% = jdzpvgkv

SLY COOPER CATEGORY EXTENSIONS
    GAME ID = 9do8o0o1
    CATEGORIES:

SLYFECTA
    GAME ID = m1mxemj6
 */

public class Game {
    private String gameId;
    private String gameName;
    private String coverImageLink;
    private String releaseYear;

    ReadJSON json = ReadJSON.getInstance();
    private ArrayList<Category> categories = new ArrayList<>();

    public Game(String id){
        this.gameId = id;
        String[] data = json.getGameData(id);
        this.gameName = data[0];
        this.coverImageLink = data[1];
        this.releaseYear = data[2];
        this.categories = json.getCategoryData(id);
    }

    @Override
    public String toString() {
        return this.gameName;
    }

    public String getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getCoverImageLink() {
        return coverImageLink;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory(String categoryId) {
        categories.add(new Category(categoryId));
    }
}
