package com.example.speedrunapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class LeaderboardScreen extends AppCompatActivity {

    private static final String TAG = "LeaderboardScreen";
    ReadJSON json = ReadJSON.getInstance();

    private ListView lvLeaderboard;
    private ListView lvLeaderboardHeader;
    private ImageView ivGameCover;
    private TextView tvGameInfo;
    private TabLayout tlCategories;
    private TabLayout tlLevels;
    private TabLayout tlLeaderboards;

    private String gameId;
    private Leaderboard leaderboard;
    private Game game;
    private Category selectedCategory;
    private Level selectedLevel;

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
        game.setCategories();
        game.setLevels();

        // Displays the info of the selected game
        tvGameInfo = findViewById(R.id.tvGameTitle);
        tvGameInfo.setText(game.getGameInfo());

        lvLeaderboard = findViewById(R.id.lvLeaderboard);
        tlLevels = findViewById(R.id.tlLevels);
        ivGameCover = findViewById(R.id.ivGameImage);
        lvLeaderboardHeader = findViewById(R.id.lvLeaderboardHeader);

        // Storing all the categories of the selected game
        categories = game.getCategories();

        // Default category is the first in the array (usually Any%)
        selectedCategory = categories.get(0);

        // Displaying default info
        setLeaderboard(game);
        loadLeaderboard(runIndex,game, selectedCategory);
        setLeaderboardTabs();
        setCategoryTabs(categories);
    }

    private void setLeaderboardTabs(){
        tlLeaderboards = findViewById(R.id.tlLeaderboards);
        // Sly 1 exclusive tabs
        if (game.getGameId().compareTo("nd2egvd0") == 0) {
            tlLeaderboards.addTab(tlLeaderboards.newTab().setText("Full Game"));
            tlLeaderboards.addTab(tlLeaderboards.newTab().setText("Master Thief Sprints"));
        }
        // Sly 2 exclusive tabs
        else if (game.getGameId().compareTo("4d79zg17") == 0) {
            tlLeaderboards.addTab(tlLeaderboards.newTab().setText("Full Game"));
            tlLeaderboards.addTab(tlLeaderboards.newTab().setText("Episodes"));
        }
        // Sly 3 exclusive tabs
        else if (game.getGameId().compareTo("9d3rkgdl") == 0) {
            tlLeaderboards.addTab(tlLeaderboards.newTab().setText("Full Game"));
            tlLeaderboards.addTab(tlLeaderboards.newTab().setText("Episodes"));
            tlLeaderboards.addTab(tlLeaderboards.newTab().setText("Master Thief Challenges"));
        }
        // Sly 4 exclusive tabs
        else if (game.getGameId().compareTo("o1y9526q") == 0) {
            tlLeaderboards.addTab(tlLeaderboards.newTab().setText("Full Game"));
            tlLeaderboards.addTab(tlLeaderboards.newTab().setText("Episodes"));
        }
        // Slyfecta exclusive tabs
        else if (game.getGameId().compareTo("m1mxemj6") == 0) {
            tlLeaderboards.addTab(tlLeaderboards.newTab().setText("Full Game"));
        }
        // Sly CE exclusive tabs
        else if (game.getGameId().compareTo("9do8o0o1") == 0) {
            for (int i = 0; i<5; i++) {
                tlLeaderboards.addTab(tlLeaderboards.newTab().setText(categories.get(i).getCategoryName()));
            }
        }

        tlLeaderboards.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tlLeaderboards.getSelectedTabPosition();

                // Condition for checking which tabs to show (full game / levels)
                if (index == 0) {
                    selectedCategory = game.getCategories().get(index);
                    runIndex = 0;
                    loadLeaderboard(runIndex, game, selectedCategory);
                    setCategoryTabs(game.getCategories());
                }
                else if (index == 1) {
                    selectedCategory = game.getLevels().get(0).getCategories().get(0);
                    selectedLevel = game.getLevels().get(0);
                    runIndex = 0;
                    loadLevelLeaderboard(runIndex, game, selectedLevel, selectedCategory);

                    if (selectedLevel.getLevelId().compareTo("xd0xjxdq") == 0) {
                        setLevelTabs(game.getLevels(), 1);
                    }
                    else {
                        setLevelTabs(game.getLevels(), 0);
                    }

                }
                else {
                    selectedCategory = game.getLevels().get(0).getCategories().get(0);
                    selectedLevel = game.getLevels().get(0);
                    runIndex = 0;

                    if (selectedLevel.getLevelId().compareTo("xd0xjxdq") == 0) {
                        setLevelTabs(game.getLevels(), 2);
                        loadLevelLeaderboard(runIndex, game, game.getLevels().get(6), selectedCategory);
                    }
                    else {
                        setLevelTabs(game.getLevels(), 0);
                        loadLevelLeaderboard(runIndex, game, selectedLevel, selectedCategory);
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setCategoryTabs(ArrayList<Category> categories){
        tlCategories = findViewById(R.id.tlCategories);

        if (tlLevels.getTabCount() > 0) {
            tlLevels.removeAllTabs();
            tlCategories.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i<categories.size(); i++) {
            tlCategories.addTab(tlCategories.newTab().setText(categories.get(i).getCategoryName()));
        }

        tlCategories.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tlCategories.getSelectedTabPosition();
                selectedCategory = categories.get(index);
                runIndex = 0;
                loadLeaderboard(runIndex, game, selectedCategory);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setLevelTabs(ArrayList<Level> levels, int separator){
        if (tlCategories.getTabCount() > 0) {
            tlCategories.removeAllTabs();
            tlCategories.setVisibility(View.INVISIBLE);
        }
        if (tlLevels.getTabCount() > 0) {
            tlLevels.removeAllTabs();
        }

        // Condition for separating Episodes and Master Thief Challenges
        if (separator == 1) {
            for (int i = 0; i<6; i++) {
                tlLevels.addTab(tlLevels.newTab().setText(levels.get(i).getLevelName()));
            }
        }
        else if (separator == 2) {
            for (int i = 6; i<levels.size(); i++) {
                tlLevels.addTab(tlLevels.newTab().setText(levels.get(i).getLevelName()));
            }
        }
        else {
            for (int i = 0; i<levels.size(); i++) {
                tlLevels.addTab(tlLevels.newTab().setText(levels.get(i).getLevelName()));
            }
        }

        tlLevels.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tlLevels.getSelectedTabPosition();

                // Condition for displaying Master Thief Challenges correctly
                if (tlLeaderboards.getSelectedTabPosition() == 2) {
                    selectedLevel = levels.get(index + 6);
                }
                else {
                    selectedLevel = levels.get(index);
                }
                selectedCategory = levels.get(index).getCategories().get(0);
                runIndex = 0;

                loadLevelLeaderboard(runIndex, game, selectedLevel, selectedCategory);
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
        if (tlCategories.getTabCount() > 0) {
            loadLeaderboard(runIndex, game, selectedCategory);
        }
        else {
            loadLevelLeaderboard(runIndex, game, selectedLevel, selectedCategory);
        }
    }

    private void loadPreviousPage(){
        runIndex = runIndex - 10;
        if (tlCategories.getTabCount() > 0) {
            loadLeaderboard(runIndex, game, selectedCategory);
        }
        else {
            loadLevelLeaderboard(runIndex, game, selectedLevel, selectedCategory);
        }
    }

    private void loadLeaderboard(int index, Game game, Category category){
        runList.clear();
        leaderboard = json.getLeaderboardData(game, category, index, index+10);
        runList = leaderboard.getRunList();
        LeaderboardAdapter adapter = new LeaderboardAdapter(this, R.layout.adapter_view_layout, runList);
        lvLeaderboard.setAdapter(adapter);
    }

    private void loadLevelLeaderboard(int index, Game game, Level level, Category category){
        runList.clear();
        leaderboard = json.getLevelLeaderboardData(game, level, level.getCategories().get(0), index, index+10);
        runList = leaderboard.getRunList();
        LeaderboardAdapter adapter = new LeaderboardAdapter(this, R.layout.adapter_view_layout, runList);
        lvLeaderboard.setAdapter(adapter);
    }

    public void setLeaderboard(Game game) {

        Run runHeaderInfo = new Run("Player", "Time", "https://www.speedrun.com/images/1st.png");
        runHeader.add(runHeaderInfo);

        LeaderboardAdapter adapterHeader = new LeaderboardAdapter(this, R.layout.adapter_view_layout_header, runHeader);
        lvLeaderboardHeader.setAdapter(adapterHeader);

        Glide.with(this)
                .load(game.getCoverImageLink())
                .override(300, 200)
                .into(ivGameCover);

    }

    public void backToMainScreen(View v) {
        Intent intent = new Intent(LeaderboardScreen.this, MainActivity.class);
        runList.clear();
        startActivity(intent);
    }

}
