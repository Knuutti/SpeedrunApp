package com.example.speedrunapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ReadJSON {
    private static ReadJSON readJSON;
    Methods methods = Methods.getInstance();
    private ReadJSON(){}

    public static ReadJSON getInstance(){
        if (readJSON == null) {
            readJSON = new ReadJSON();
        }
        return readJSON;
    }

    public Leaderboard loadLeaderboard(Game game, Category category){
        Leaderboard leaderboard = readLeaderboardData(getLeaderboardJSON(game.getGameId(), category.getCategoryId()));
        return leaderboard;
    }

    public Leaderboard readLeaderboardData(String leaderboardJSON){
        Leaderboard leaderboard = Leaderboard.getInstance();
        leaderboard.clear();

        if (leaderboardJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(leaderboardJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONArray runs = (JSONArray) data.get("runs");

                for (int i = 0; i<runs.size() && i < 10; i++) {
                    JSONObject runObject = (JSONObject) runs.get(i);
                    JSONObject run = (JSONObject) runObject.get("run");

                    leaderboard.addRun(new Run(runObject.get("place").toString(), run.get("id").toString()));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return leaderboard;
    }

    public String getLeaderboardJSON(String game_id, String category_id){
        String response = null;

        try {
            URL url = new URL("https://www.speedrun.com/api/v1/leaderboards/" + game_id + "/category/" + category_id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            response = sb.toString();
            in.close();
            br.close();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String readUserData(String userJSON) {
        String username = null;
        if (userJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(userJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject names = (JSONObject) data.get("names");
                username = names.get("international").toString();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return username;
    }

    public String getUserDataJSON(String user_id){
        String response = null;

        try {
            URL url = new URL("https://www.speedrun.com/api/v1/users/" + user_id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            response = sb.toString();
            in.close();
            br.close();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String[] readRunData(String run_data) {
        String[] run_data_array = new String[4];
        String user_id = null;
        if (run_data != null) {
            try {
                JSONParser parser = new JSONParser();

                JSONObject obj = (JSONObject) parser.parse(run_data);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject times = (JSONObject) data.get("times");
                JSONArray players = (JSONArray) data.get("players");

                for (int j = 0; j< players.size(); j++) {
                    JSONObject playerObject = (JSONObject) players.get(j);
                    user_id = playerObject.get("id").toString();
                }

                run_data_array[0] = data.get("game").toString();
                run_data_array[1] = data.get("date").toString();
                run_data_array[2] = user_id;
                run_data_array[3] = times.get("realtime_t").toString();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return run_data_array;
    }

    public String getRunDataJSON(String run_id){
        String response = null;

        try {
            URL url = new URL("https://www.speedrun.com/api/v1/runs/" + run_id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            response = sb.toString();
            in.close();
            br.close();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
