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
        String time = null;
        try {
            int timeInt = Integer.parseInt(timeString);
            int h = timeInt/3600;
            int m = (timeInt - (3600*h)) / 60;
            int s = timeInt - 3600*h - 60*m;
            String minutes = "" + m;
            String seconds = "" + s;
            String hours = "" + h;
            if (m < 10) {
                minutes = "0" + minutes;
            }
            if (s < 10) {
                seconds = "0" + seconds;
            }
            time = hours + ":" + minutes + ":" + seconds;
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return time;
    }
}
