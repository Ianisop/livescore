package com.livescore.livescore;

public class Match {
    private final int matchID;
    private final String teamName;

    public Match(int matchID, String teamName) {
        this.matchID = matchID;
        this.teamName = teamName;
    }

    public int getMatchID() {
        return matchID;
    }

    public String getTeamName() {
        return teamName;
    }
}