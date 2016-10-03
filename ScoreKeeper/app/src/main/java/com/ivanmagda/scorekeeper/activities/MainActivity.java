package com.ivanmagda.scorekeeper.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ivanmagda.scorekeeper.R;
import com.ivanmagda.scorekeeper.model.Team;

public class MainActivity extends AppCompatActivity {

    // Score text views.
    private TextView lhsTeamScoreTextView;
    private TextView rhsTeamScoreTextView;

    // Fouls text views.
    private TextView lhsTeamFoulTextView;
    private TextView rhsTeamFoulTextView;

    // Teams info.
    private Team lhsTeam = new Team();
    private Team rhsTeam = new Team();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configure();
    }

    // Actions.

    public void onButtonClick(View button) {
        switch (button.getId()) {
            case R.id.lhs_team_goal_button:
                incrementScoreForTeam(lhsTeam);
                break;
            case R.id.rhs_team_goal_button:
                incrementScoreForTeam(rhsTeam);
                break;
            case R.id.lhs_team_foal_button:
                incrementFoalsForTeam(lhsTeam);
                break;
            case R.id.rhs_team_foal_button:
                incrementFoalsForTeam(rhsTeam);
                break;
        }
    }

    public void onResetButtonClick(View view) {
        lhsTeam.resetData();
        rhsTeam.resetData();

        updateUI();
    }

    // Configure and update UI.

    private void configure() {
        lhsTeamScoreTextView = (TextView) findViewById(R.id.lhs_team_score_text_view);
        lhsTeamFoulTextView  = (TextView) findViewById(R.id.lhs_team_foul_text_view);
        rhsTeamScoreTextView = (TextView) findViewById(R.id.rhs_team_score_text_view);
        rhsTeamFoulTextView  = (TextView) findViewById(R.id.rhs_team_foul_text_view);

        updateUI();
    }

    private void updateUI() {
        lhsTeamScoreTextView.setText(String.format("%d", lhsTeam.getScore()));
        lhsTeamFoulTextView.setText(String.format("%d", lhsTeam.getFouls()));
        rhsTeamScoreTextView.setText(String.format("%d", rhsTeam.getScore()));
        rhsTeamFoulTextView.setText(String.format("%d", rhsTeam.getFouls()));
    }

    // Private helpers.

    private void incrementScoreForTeam(Team team) {
        team.incrementScore();
        updateUI();
    }

    private void incrementFoalsForTeam(Team team) {
        team.incrementFouls();
        updateUI();
    }

}
