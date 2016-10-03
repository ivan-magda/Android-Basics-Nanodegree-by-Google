package com.ivanmagda.scorekeeper.model;

public class Team {

    private int score;
    private int fouls;

    public Team() {
    }

    public Team(int score, int fouls) {
        this.score = score;
        this.fouls = fouls;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFouls() {
        return fouls;
    }

    public void setFouls(int fouls) {
        this.fouls = fouls;
    }

    public void incrementScore() {
        score++;
    }

    public void incrementFouls() {
        fouls++;
    }

    public void decrementScore() {
        if (score == 0) return;
        score--;
    }

    public void decrementFouls() {
        if (fouls == 0) return;
        fouls--;
    }

    public void resetData() {
        score = 0;
        fouls = 0;
    }

}
