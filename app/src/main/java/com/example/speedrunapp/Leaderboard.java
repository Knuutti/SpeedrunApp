package com.example.speedrunapp;

import java.util.ArrayList;

public class Leaderboard {
    private static Leaderboard leaderboard;
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

    public ArrayList getRunList(){
        return runList;
    }

    public void clear() {
        runList.clear();
    }
}
