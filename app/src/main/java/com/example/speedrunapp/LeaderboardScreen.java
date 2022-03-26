package com.example.speedrunapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardScreen extends AppCompatActivity {

    private static final String TAG = "LeaderboardScreen";
    ReadJSON json = ReadJSON.getInstance();
    private ListView leaderboardList;
    private ListView leaderboardHeader;
    private String gameId;
    private ArrayList<Run> runList = new ArrayList<>();
    private ArrayList<Run> runHeader = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_screen);
        Log.d(TAG, "onCreate: Starting");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        gameId = intent.getStringExtra("game_id");

        leaderboardList = findViewById(R.id.leaderboardList);
        leaderboardHeader = findViewById(R.id.leaderboardHeader);
        readLeaderboardJSON();
    }



    public void readLeaderboardJSON() {
        Game game = new Game(gameId);

        Run runHeaderInfo = new Run("Player", "Time", "https://www.speedrun.com/images/1st.png");
        runHeader.add(runHeaderInfo);

        runList = json.getLeaderboardData(game, game.getCategories().get(0), 0, 20).getRunList();

        LeaderboardAdapter adapter = new LeaderboardAdapter(this, R.layout.adapter_view_layout, runList);
        LeaderboardAdapter adapterHeader = new LeaderboardAdapter(this, R.layout.adapter_view_layout_header, runHeader);
        leaderboardHeader.setAdapter(adapterHeader);
        leaderboardList.setAdapter(adapter);

    }

    public void backToMainScreen(View v) {
        Intent intent = new Intent(LeaderboardScreen.this, MainActivity.class);
        runList.clear();
        startActivity(intent);
    }

}
