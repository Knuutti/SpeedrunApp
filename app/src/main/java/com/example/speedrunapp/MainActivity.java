package com.example.speedrunapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ReadJSON json = ReadJSON.getInstance();

    private ListView leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        leaderboard = findViewById(R.id.leaderboard);

    }

    public void readLeaderboardJSON(View v) {
        Game sly3 = new Game("9d3rkgdl");
        sly3.addCategory("9d8jx7kn");

        ArrayAdapter<Run> leaderboardAdapter = new ArrayAdapter<Run>(getApplicationContext(), android.R.layout.simple_spinner_item, json.loadLeaderboard(sly3, sly3.getCategory(0)).getRunList());
        leaderboard.setAdapter(leaderboardAdapter);
    }





}