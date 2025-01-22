package com.livescore.livescore;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class OpretHoldController {

    @FXML
    private TextField holdNavnTextField;

    private HandballApp mainApp;

    public void setMainApp(HandballApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleBack() {
        if (mainApp != null) {
            mainApp.showStartPage();
        }
    }

    @FXML
    private void handleSave() {
        String teamName = holdNavnTextField.getText();
        if (teamName != null && !teamName.isEmpty()) {
            try {
                DatabaseHelper.addTeam(teamName);
                int teamID = DatabaseHelper.getTeamID(teamName);
                DatabaseHelper.addTeamToStandings(teamID);
                mainApp.showAlert("Success", "Team saved successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                mainApp.showAlert("Error", "Failed to save the team.");
            }
        } else {
            mainApp.showAlert("Validation Error", "Team name cannot be empty.");
        }
    }

    @FXML
    private void handleCancel() {
        mainApp.showStartPage();
    }
}