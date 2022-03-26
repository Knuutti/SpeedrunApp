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
import java.util.ArrayList;

public class ReadJSON {

    private static ReadJSON readJSON;

    private ReadJSON(){}

    public static ReadJSON getInstance(){
        if (readJSON == null) {
            readJSON = new ReadJSON();
        }
        return readJSON;
    }

    private String JsonToString(String urlString) {
        String response = null;

        try {
            URL url = new URL(urlString);
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public Leaderboard getLeaderboardData(Game game, Category category, int start, int end){

        String leaderboardJSON = JsonToString("https://www.speedrun.com/api/v1/leaderboards/" + game.getGameId() + "/category/" + category.getCategoryId());

        Leaderboard leaderboard = Leaderboard.getInstance();
        leaderboard.clear();

        String place = null;
        String runId = null;

        if (leaderboardJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(leaderboardJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONArray runs = (JSONArray) data.get("runs");

                for (int i = start; i<(runs.size()-start) && i<end; i++) {
                    JSONObject runObject = (JSONObject) runs.get(i);
                    JSONObject runJson = (JSONObject) runObject.get("run");

                    place = runObject.get("place").toString();
                    runId = runJson.get("id").toString();

                    Run run = getRunData(place, runId);

                    leaderboard.addRun(run);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return leaderboard;
    }

    // Method for creating a Run object from run id
    public Run getRunData(String place, String runId) {

        String runJson = JsonToString("https://www.speedrun.com/api/v1/runs/" + runId);

        String userId = null;
        String gameId = null;
        String date = null;
        String time = null;
        String username = null;

        if (runJson != null) {
            try {
                JSONParser parser = new JSONParser();

                JSONObject obj = (JSONObject) parser.parse(runJson);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject times = (JSONObject) data.get("times");
                JSONArray players = (JSONArray) data.get("players");
                JSONObject playerObject = (JSONObject) players.get(0);

                gameId = data.get("game").toString();
                date = data.get("date").toString();
                time = times.get("realtime_t").toString();

                // Checks if the runner has an account
                if (playerObject.get("rel").toString().compareTo("user") == 0) {
                    userId = playerObject.get("id").toString();
                }
                else {
                    username = playerObject.get("name").toString();
                }

            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        Run run = new Run(place, runId, gameId, userId, username, date, time);
        return run;
    }

    // Method for creating a User object from user id
    public User getUserData(String userId){

        String userJSON = JsonToString("https://www.speedrun.com/api/v1/users/" + userId);

        String username = "Player";
        String country = "default";
        String colorStart = "#FFFFFF";
        String colorEnd = "#FFFFFF";

        if (userJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(userJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject names = (JSONObject) data.get("names");
                JSONObject nameStyle = (JSONObject) data.get("name-style");
                JSONObject colorFrom = (JSONObject) nameStyle.get("color-from");
                JSONObject colorTo = (JSONObject) nameStyle.get("color-to");
                JSONObject location = (JSONObject) data.get("location");

                username = names.get("international").toString();
                colorEnd = colorTo.get("dark").toString();
                colorStart = colorFrom.get("dark").toString();

                if (location != null) {
                    JSONObject countryJson = (JSONObject) location.get("country");
                    country = countryJson.get("code").toString();
                }
                else {
                    country = "default";
                }

            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        User user = new User(userId, username, country, colorStart, colorEnd);

        return user;
    }

    public ArrayList getCategoryData(String game_id) {
        String categoryJSON = JsonToString("https://www.speedrun.com/api/v1/games/" + game_id + "/categories");
        ArrayList<Category> categories = new ArrayList<>();
        if (categoryJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(categoryJSON);
                JSONArray data = (JSONArray) obj.get("data");

                for (int i = 0; i < data.size(); i++) {
                    JSONObject categoryObj = (JSONObject) data.get(i);
                    categories.add(new Category(categoryObj.get("id").toString()));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    public String[] getGameData(String game_id) {
        String gameJSON = getGameDataJSON(game_id);
        String[] data_array = new String[3];

        if (gameJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(gameJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject names = (JSONObject) data.get("names");
                JSONObject assets = (JSONObject) data.get("assets");
                JSONObject cover = (JSONObject) assets.get("cover-medium");
                data_array[0] = names.get("international").toString();
                data_array[1] = cover.get("uri").toString();
                data_array[2] = data.get("released").toString();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return data_array;
    }

    private String getGameDataJSON(String game_id){
        String response = JsonToString("https://www.speedrun.com/api/v1/games/" + game_id);
        return response;
    }

}
