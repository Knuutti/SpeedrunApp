package com.example.speedrunapp;

public class Methods {

    private static Methods methods;

    private Methods(){}

    public static Methods getInstance(){
        if (methods == null) {
            methods = new Methods();
        }
        return methods;
    }

    public String getRealtime(String timeString) {
        int timeInt = Integer. parseInt(timeString);
        int hours = timeInt/3600;
        int minutes = (timeInt - (3600*hours)) / 60;
        int seconds = timeInt - 3600*hours - 60*minutes;
        String time = hours + ":" + minutes + ":" + seconds;
        return time;
    }
}
