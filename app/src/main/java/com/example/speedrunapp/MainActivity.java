package com.example.speedrunapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<Game> gameList = new ArrayList<>();

    private ListView gameListView;

    String game_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        gameListView = findViewById(R.id.gameList);

        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, LeaderboardScreen.class);
                intent.putExtra("game_id", gameList.get(i).getGameId());
                startActivity(intent);
            }
        });
        setGames();
        GameAdapter adapter = new GameAdapter(this, R.layout.adapter_view_layout_game, gameList);
        gameListView.setAdapter(adapter);

    }

    private void setGames() {
        gameList.add(new Game("nd2egvd0")); // SLY 1
        gameList.add(new Game("4d79zg17")); // SLY 2
        gameList.add(new Game("9d3rkgdl")); // SLY 3
        gameList.add(new Game("o1y9526q")); // SLY 4
        gameList.add(new Game("m1mxemj6")); // SLYFECTA
        gameList.add(new Game("9do8o0o1")); // SLY COOPER CE
    }



}