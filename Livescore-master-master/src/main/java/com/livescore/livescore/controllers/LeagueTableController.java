package com.livescore.livescore;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LeagueTableController {

    @FXML
    private TableView<?> leagueTable;
    @FXML
    private TableColumn<?, ?> rankColumn;
    @FXML
    private TableColumn<?, ?> teamNameColumn;
    @FXML
    private TableColumn<?, ?> playedColumn;
    @FXML
    private TableColumn<?, ?> winsColumn;
    @FXML
    private TableColumn<?, ?> lossesColumn;
    @FXML
    private TableColumn<?, ?> drawsColumn;
    @FXML
    private TableColumn<?, ?> pointsColumn;

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
}