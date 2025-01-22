package com.livescore.livescore;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class AdminController {

    @FXML
    private TableView<Team> teamTableView;
    @FXML
    private TableColumn<Team, String> teamNameColumn;
    @FXML
    private TableView<Match> matchTableView;
    @FXML
    private TableColumn<Match, String> matchNameColumn;

    private HandballApp mainApp;

    public void setMainApp(HandballApp mainApp) {
        this.mainApp = mainApp;
        loadTeams();
        loadMatches();
    }

    private void loadTeams() {
        try {
            ObservableList<Team> teams = DatabaseHelper.getTeams();
            teamTableView.setItems(teams);
        } catch (SQLException e) {
            System.err.println("Error loading teams: " + e.getMessage());
            e.printStackTrace();
            mainApp.showAlert("Error", "Failed to load teams: " + e.getMessage());
        }
    }

    private void loadMatches() {
        try {
            ObservableList<Match> matches = DatabaseHelper.getMatches();
            matchTableView.setItems(matches);
        } catch (SQLException e) {
            System.err.println("Error loading matches: " + e.getMessage());
            e.printStackTrace();
            mainApp.showAlert("Error", "Failed to load matches: " + e.getMessage());
        }
    }

    @FXML
    private void initialize() {
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        matchNameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
    }

    @FXML
    private void handleDeleteTeam() {
        Team selectedTeam = teamTableView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            try {
                DatabaseHelper.deleteTeam(selectedTeam.getTeamName());
                teamTableView.getItems().remove(selectedTeam);
            } catch (SQLException e) {
                System.err.println("Error deleting team: " + e.getMessage());
                e.printStackTrace();
                mainApp.showAlert("Error", "Failed to delete team: " + e.getMessage());
            }
        } else {
            mainApp.showAlert("Validation Error", "No team selected.");
        }
    }

    @FXML
    private void handleDeleteMatch() {
        Match selectedMatch = matchTableView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            try {
                DatabaseHelper.deleteMatch(selectedMatch.getMatchID());
                matchTableView.getItems().remove(selectedMatch);
            } catch (SQLException e) {
                System.err.println("Error deleting match: " + e.getMessage());
                e.printStackTrace();
                mainApp.showAlert("Error", "Failed to delete match: " + e.getMessage());
            }
        } else {
            mainApp.showAlert("Validation Error", "No match selected.");
        }
    }

    @FXML
    private void handleBack() {
        if (mainApp != null) {
            mainApp.showStartPage();
        }
    }
}