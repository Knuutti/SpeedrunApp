package com.example.speedrunapp;

import java.util.ArrayList;

public class Leaderboard {
    private static Leaderboard leaderboard;
    private int lastPage = 0;
    private ArrayList<Run> runList = new ArrayList<>();

    private Leaderboard(){}

    public static Leaderboard getInstance(){
        if (leaderboard == null) {
            leaderboard = new Leaderboard();
        }
        return leaderboard;
    }

    public void addRun(Run run){
        runList.add(run);
    }

    public int getLastPage(){
        return lastPage;
    }

    public void setLastPage(int i){
        lastPage = i;
    }

    public ArrayList getRunList(){
        return runList;
    }

    public void clear() {
        runList.clear();
    }
}
