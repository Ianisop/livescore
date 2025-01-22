package com.livescore.livescore;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.sql.SQLException;

public class HandballAppController {

    @FXML
    private Label homeScoreLabel;
    @FXML
    private Label awayScoreLabel;
    @FXML
    private Label timerLabel;
    @FXML
    private ListView<String> homeEventLogListView;
    @FXML
    private ListView<String> awayEventLogListView;
    @FXML
    private Label homeTeamLabel;
    @FXML
    private Label awayTeamLabel;

    private HandballApp mainApp;
    private Timeline timeline;
    private int secondsElapsed;
    private int homeScore = 0;
    private int awayScore = 0;
    private int matchID; // Add matchID to identify the match

    public void setMainApp(HandballApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setTeams(String homeTeam, String awayTeam) {
        homeTeamLabel.setText(homeTeam);
        awayTeamLabel.setText(awayTeam);
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    @FXML
    private void handleBack() {
        if (mainApp != null) {
            mainApp.showStartPage();
        }
    }

    @FXML
    private void handleAwayGoal() {
        awayScore++;
        awayScoreLabel.setText(String.valueOf(awayScore));
        awayEventLogListView.getItems().add("Away team scored at " + timerLabel.getText());
        recordGoal(matchID, getTeamID(awayTeamLabel.getText()), timerLabel.getText());
    }

    @FXML
    private void handleHomeGoal() {
        homeScore++;
        homeScoreLabel.setText(String.valueOf(homeScore));
        homeEventLogListView.getItems().add("Home team scored at " + timerLabel.getText());
        recordGoal(matchID, getTeamID(homeTeamLabel.getText()), timerLabel.getText());
    }

    private void recordGoal(int matchID, int teamID, String goalTime) {
        try {
            DatabaseHelper.recordGoal(matchID, teamID, goalTime);
        } catch (SQLException e) {
            e.printStackTrace();
            mainApp.showAlert("Error", "Failed to record the goal.");
        }
    }

    private int getTeamID(String teamName) {
        try {
            return DatabaseHelper.getTeamID(teamName);
        } catch (SQLException e) {
            e.printStackTrace();
            mainApp.showAlert("Error", "Failed to get team ID.");
            return -1;
        }
    }

    @FXML
    private void handleAwayDeleteGoal() {
        if (awayScore > 0) {
            awayScore--;
            awayScoreLabel.setText(String.valueOf(awayScore));
            awayEventLogListView.getItems().add("Away team goal deleted at " + timerLabel.getText());
            deleteMostRecentGoal(matchID, getTeamID(awayTeamLabel.getText()));
        }
    }

    @FXML
    private void handleHomeDeleteGoal() {
        if (homeScore > 0) {
            homeScore--;
            homeScoreLabel.setText(String.valueOf(homeScore));
            homeEventLogListView.getItems().add("Home team goal deleted at " + timerLabel.getText());
            deleteMostRecentGoal(matchID, getTeamID(homeTeamLabel.getText()));
        }
    }

    private void deleteMostRecentGoal(int matchID, int teamID) {
        try {
            DatabaseHelper.deleteMostRecentGoal(matchID, teamID);
        } catch (SQLException e) {
            e.printStackTrace();
            mainApp.showAlert("Error", "Failed to delete the goal.");
        }
    }

    @FXML
    private void handleHomeSuspension() {
        homeEventLogListView.getItems().add("Home team suspension at " + timerLabel.getText());
        recordPenalty(matchID, getTeamID(homeTeamLabel.getText()), timerLabel.getText());
    }

    @FXML
    private void handleAwaySuspension() {
        awayEventLogListView.getItems().add("Away team suspension at " + timerLabel.getText());
        recordPenalty(matchID, getTeamID(awayTeamLabel.getText()), timerLabel.getText());
    }

    private void recordPenalty(int matchID, int teamID, String penaltyTime) {
        try {
            DatabaseHelper.recordPenalty(matchID, teamID, penaltyTime);
        } catch (SQLException e) {
            e.printStackTrace();
            mainApp.showAlert("Error", "Failed to record the penalty.");
        }
    }

    @FXML
    private void handleHomePenalty() {
        homeEventLogListView.getItems().add("Home team free throw at " + timerLabel.getText());
        recordFreeThrow(matchID, getTeamID(homeTeamLabel.getText()), timerLabel.getText());
    }

    @FXML
    private void handleAwayPenalty() {
        awayEventLogListView.getItems().add("Away team free throw at " + timerLabel.getText());
        recordFreeThrow(matchID, getTeamID(awayTeamLabel.getText()), timerLabel.getText());
    }

    private void recordFreeThrow(int matchID, int teamID, String freeThrowTime) {
        try {
            DatabaseHelper.recordFreeThrow(matchID, teamID, freeThrowTime);
        } catch (SQLException e) {
            e.printStackTrace();
            mainApp.showAlert("Error", "Failed to record the free throw.");
        }
    }

    @FXML
    private void handleResume() {
        if (timeline != null) {
            timeline.play();
        }
    }

    @FXML
    private void handleStart() {
        if (timeline != null) {
            timeline.stop();
        }
        secondsElapsed = 0;
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsElapsed++;
            int minutes = secondsElapsed / 60;
            int seconds = secondsElapsed % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void handleStop() {
        if (timeline != null) {
            timeline.stop();
        }
        secondsElapsed = 0;
        timerLabel.setText("00:00");
        homeEventLogListView.getItems().clear();
        awayEventLogListView.getItems().clear();

        // Update standings
        try {
            int homeTeamID = getTeamID(homeTeamLabel.getText());
            int awayTeamID = getTeamID(awayTeamLabel.getText());
            DatabaseHelper.updateStandings(homeTeamID, awayTeamID, homeScore, awayScore);
        } catch (SQLException e) {
            e.printStackTrace();
            mainApp.showAlert("Error", "Failed to update standings.");
        }
    }

    @FXML
    private void handlePause() {
        if (timeline != null) {
            timeline.pause();
        }
    }
}