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

    public void readUserJSON(String userId) {
        String userJSON = getUserDataJSON(userId);

        if (userJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(userJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject names = (JSONObject) data.get("names");
                String username = names.get("international").toString();
                System.out.println(username);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public void readLeaderboardJSON(){
        Game sly3 = new Game("9d3rkgdl"); //TODO
        sly3.addCategory("9d8jx7kn");

        String leaderboardJSON = getLeaderboardJSON(sly3.getGameId(), sly3.getCategory(0).getCategoryId());

        if (leaderboardJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(leaderboardJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONArray runs = (JSONArray) data.get("runs");

                for (int i = 0; i<runs.size() && i < 11; i++) {
                    JSONObject runObject = (JSONObject) runs.get(i);
                    System.out.print(runObject.get("place") + "\t");
                    JSONObject runData = (JSONObject) runObject.get("run");
                    JSONArray players = (JSONArray) runData.get("players");

                    for (int j = 0; j< players.size(); j++) {
                        JSONObject playerObject = (JSONObject) players.get(j);
                        String userId = playerObject.get("id").toString();
                        readUserJSON(userId);
                    }

                    JSONObject times = (JSONObject) runData.get("times");
                    String time = times.get("realtime_t").toString();
                    methods.getRealtime(time);


                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public String getLeaderboardJSON(String gameId, String categoryId){
        String response = null;

        try {
            URL url = new URL("https://www.speedrun.com/api/v1/leaderboards/" + gameId + "/category/" + categoryId);
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

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String getUserDataJSON(String userId){
        String response = null;

        try {
            URL url = new URL("https://www.speedrun.com/api/v1/users/" + userId);
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
