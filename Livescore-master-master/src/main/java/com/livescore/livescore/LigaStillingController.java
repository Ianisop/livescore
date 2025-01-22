package com.livescore.livescore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class LigaStillingController {

    @FXML
    private TableView<TeamStanding> leagueTable;
    @FXML
    private TableColumn<TeamStanding, Integer> rankColumn;
    @FXML
    private TableColumn<TeamStanding, String> teamNameColumn;
    @FXML
    private TableColumn<TeamStanding, Integer> playedColumn;
    @FXML
    private TableColumn<TeamStanding, Integer> winsColumn;
    @FXML
    private TableColumn<TeamStanding, Integer> lossesColumn;
    @FXML
    private TableColumn<TeamStanding, Integer> drawsColumn;
    @FXML
    private TableColumn<TeamStanding, Integer> pointsColumn;
    @FXML
    private TableColumn<TeamStanding, Integer> teamIDColumn;

    private HandballApp mainApp;

    public void setMainApp(HandballApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        playedColumn.setCellValueFactory(new PropertyValueFactory<>("played"));
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));
        drawsColumn.setCellValueFactory(new PropertyValueFactory<>("draws"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        teamIDColumn.setCellValueFactory(new PropertyValueFactory<>("teamID"));

        leagueTable.setItems(getLeagueStandings());
    }

    private ObservableList<TeamStanding> getLeagueStandings() {
        ObservableList<TeamStanding> standings = FXCollections.observableArrayList();
        try {
            standings.addAll(DatabaseHelper.getLeagueStandings());
        } catch (SQLException e) {
            e.printStackTrace();
            mainApp.showAlert("Error", "Failed to load league standings.");
        }
        return standings;
    }

    @FXML
    private void handleBack() {
        if (mainApp != null) {
            mainApp.showStartPage();
        }
    }
}