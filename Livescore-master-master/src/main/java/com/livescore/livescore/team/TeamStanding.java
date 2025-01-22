package com.livescore.livescore;

public class TeamStanding {
    private final int rank;
    private final int teamID;
    private final String teamName;
    private final int played;
    private final int wins;
    private final int losses;
    private final int draws;
    private final int points;

    public TeamStanding(int rank, int teamID, String teamName, int played, int wins, int losses, int draws, int points) {
        this.rank = rank;
        this.teamID = teamID;
        this.teamName = teamName;
        this.played = played;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.points = points;
    }

    public int getRank() {
        return rank;
    }

    public int getTeamID() {
        return teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getPlayed() {
        return played;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }

    public int getPoints() {
        return points;
    }
}