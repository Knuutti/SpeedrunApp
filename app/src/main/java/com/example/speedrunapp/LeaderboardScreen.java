package com.example.speedrunapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class LeaderboardScreen extends AppCompatActivity {

    private static final String TAG = "LeaderboardScreen";
    ReadJSON json = ReadJSON.getInstance();

    private ListView leaderboardList;
    private ListView leaderboardHeader;
    private ImageView gameCover;
    private TextView gameInfo;

    private String gameId;
    private Leaderboard leaderboard;
    private Game game;
    private Category selected;
    private TabLayout tabLayout;

    // This value tracks which runs will be shown (max. 10 runs / page)
    private int runIndex = 0;

    private ArrayList<Run> runList = new ArrayList<>();
    private ArrayList<Run> runHeader = new ArrayList<>();
    private ArrayList<Category> categories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_screen);
        Log.d(TAG, "onCreate: Starting");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        gameId = intent.getStringExtra("game_id");
        game = new Game(gameId);
        selected = game.getCategories().get(0);

        leaderboardList = findViewById(R.id.leaderboardList);
        gameCover = findViewById(R.id.gameCover1);
        leaderboardHeader = findViewById(R.id.leaderboardHeader);

        gameInfo = findViewById(R.id.gameTitle1);
        gameInfo.setText(game.getGameInfo());

        categories = game.getCategories();
        readLeaderboardJSON(game);
        setCategoryTabs(categories);
    }

    private void setCategoryTabs(ArrayList<Category> categories){
        tabLayout = findViewById(R.id.tablayout);
        loadLeaderboard(runIndex,game,selected);

        for (int i = 0; i<categories.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(categories.get(i).getCategoryName().toString()));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tabLayout.getSelectedTabPosition();
                selected = categories.get(index);
                runIndex = 0;
                loadLeaderboard(runIndex, game, selected);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void nextPage(View v) {
        if (leaderboard.getLastPage() == 0) {
            loadNextPage();
        }
    }

    public void previousPage(View v) {
        if (runIndex > 0) {
            loadPreviousPage();
        }
    }

    private void loadNextPage(){
        runIndex = runIndex + 10;
        loadLeaderboard(runIndex, game, selected);
    }

    private void loadPreviousPage(){
        runIndex = runIndex - 10;
        loadLeaderboard(runIndex, game, selected);
    }

    private void loadLeaderboard(int index, Game game, Category category){
        runList.clear();
        leaderboard = json.getLeaderboardData(game, category, index, index+10);
        runList = leaderboard.getRunList();
        LeaderboardAdapter adapter = new LeaderboardAdapter(this, R.layout.adapter_view_layout, runList);
        leaderboardList.setAdapter(adapter);
    }

    public void readLeaderboardJSON(Game game) {

        Run runHeaderInfo = new Run("Player", "Time", "https://www.speedrun.com/images/1st.png");
        runHeader.add(runHeaderInfo);

        LeaderboardAdapter adapterHeader = new LeaderboardAdapter(this, R.layout.adapter_view_layout_header, runHeader);
        leaderboardHeader.setAdapter(adapterHeader);

        Glide.with(this)
                .load(game.getCoverImageLink())
                .override(300, 200)
                .into(gameCover);

    }

    public void backToMainScreen(View v) {
        Intent intent = new Intent(LeaderboardScreen.this, MainActivity.class);
        runList.clear();
        startActivity(intent);
    }

}
