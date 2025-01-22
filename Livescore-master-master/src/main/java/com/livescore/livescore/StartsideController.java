package com.livescore.livescore;

import javafx.fxml.FXML;

public class StartsideController {

    private HandballApp mainApp;

    public void setMainApp(HandballApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleGoToCreateTeam() {
        if (mainApp != null) {
            mainApp.showCreateTeam();
        }
    }

    @FXML
    private void handleGoToHelloView() {
        if (mainApp != null) {
            mainApp.showHelloView("Home Team", "Away Team", 0); // Pass a placeholder matchID
        }
    }

    @FXML
    private void handleGoToCreateMatch() {
        if (mainApp != null) {
            mainApp.showCreateMatch();
        }
    }

    @FXML
    private void handleGoToManageTeam() {
        if (mainApp != null) {
            mainApp.showManageTeams();
        }
    }

    @FXML
    private void handleGoToLeaguePlacements() {
        if (mainApp != null) {
            mainApp.showLeaguePlacements();
        }
    }

    @FXML
    private void handleGoToAdmin() {
        if (mainApp != null) {
            mainApp.showAdmin();
        }
    }
}