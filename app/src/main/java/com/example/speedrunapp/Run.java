package com.example.speedrunapp;

public class Run {

    private String run_id;
    private User user;
    private String place = "0";
    private String game_id;
    private String date;
    private String username;
    private String time;
    private String imgURL = "default";

    ReadJSON json = ReadJSON.getInstance();
    Methods methods = Methods.getInstance();

    // General builder
    public Run(String place, String runId, String gameId, String userId, String username, String date, String time){
        if (userId != null) {
            this.user = json.getUserData(userId);
        }
        else {
            this.user = new User(username);
        }

        this.run_id = run_id;
        this.place = place;
        this.game_id = gameId;
        this.date = date;
        this.imgURL = "https://www.speedrun.com/images/flags/" + user.getCountry() + ".png";
        this.time = methods.getRealtime(time);
    }

    // Builder for getting the header for the leaderboard
    public Run(String username, String time, String imgURL) {
        this.username = username;
        this.time = time;
        this.imgURL = imgURL;
    }

    @Override
    public String toString() {
        String print = this.place + "\t" + this.username + "\t" + this.time;
        return print;
    }

    public String getPlace(){
        return this.place;
    }

    public User getUser(){
        return this.user;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getUsername(){
        return this.username;
    }

    public String getTime(){
        return this.time;
    }
}
