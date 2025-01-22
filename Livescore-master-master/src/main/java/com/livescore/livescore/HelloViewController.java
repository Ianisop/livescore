package com.livescore.livescore;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloViewController {

    @FXML
    private Label homeTeamLabel;
    @FXML
    private Label awayTeamLabel;

    private HandballApp mainApp;

    public void setMainApp(HandballApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setTeams(String homeTeam, String awayTeam) {
        homeTeamLabel.setText(homeTeam);
        awayTeamLabel.setText(awayTeam);
    }

    @FXML
    private void handleBack() {
        if (mainApp != null) {
            mainApp.showStartPage();
        }
    }
}