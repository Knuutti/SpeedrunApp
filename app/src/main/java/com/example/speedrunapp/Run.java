package com.example.speedrunapp;

public class Run {

    private String run_id;
    private String place;
    private String game_id;
    private String date;
    private String user_id;
    private String username;
    private String time;

    ReadJSON json = ReadJSON.getInstance();
    Methods methods = Methods.getInstance();

    public Run(String place, String run_id){
        String[] run_data = json.readRunData(json.getRunDataJSON(run_id));
        this.run_id = run_id;
        this.place = place;
        this.game_id = run_data[0];
        this.date = run_data[1];
        this.user_id = run_data[2];
        this.username = json.readUserData(json.getUserDataJSON(this.user_id));
        this.time = methods.getRealtime(run_data[3]);
    }

    @Override
    public String toString() {
        String print = this.place + "\t" + this.username + "\t" + this.time;
        return print;
    }
}
