package com.livescore.livescore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.sql.SQLException;

public class OpretKampController {

    @FXML
    private ComboBox<String> hjemmeHold;
    @FXML
    private ComboBox<String> gaesteHold;

    private HandballApp mainApp;

    public void setMainApp(HandballApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleCreateMatch() {
        String homeTeam = hjemmeHold.getValue();
        String awayTeam = gaesteHold.getValue();

        if (homeTeam != null && awayTeam != null && !homeTeam.equals(awayTeam)) {
            try {
                int matchID = DatabaseHelper.addMatch(homeTeam, awayTeam, "2023-12-01", "18:00");
                mainApp.showAlert("Success", "Match created successfully with MatchID: " + matchID);
                mainApp.showHelloView(homeTeam, awayTeam, matchID); // Pass matchID to hello-view.fxml
            } catch (SQLException e) {
                e.printStackTrace();
                mainApp.showAlert("Error", "Failed to create the match.");
            }
        } else {
            mainApp.showAlert("Validation Error", "Please select different teams for home and away.");
        }
    }

    @FXML
    private void initialize() {
        // Initialize ComboBoxes with team names
        hjemmeHold.setItems(getTeamNames());
        gaesteHold.setItems(getTeamNames());
    }

    private ObservableList<String> getTeamNames() {
        try {
            return DatabaseHelper.getTeamNames();
        } catch (SQLException e) {
            e.printStackTrace();
            mainApp.showAlert("Error", "Failed to load team names.");
            return FXCollections.observableArrayList();
        }
    }

    @FXML
    private void handleBack() {
        if (mainApp != null) {
            mainApp.showStartPage();
        }
    }
}