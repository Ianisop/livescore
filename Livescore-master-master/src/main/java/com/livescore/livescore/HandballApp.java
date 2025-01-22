package com.livescore.livescore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HandballApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showStartPage();
    }

    public void showStartPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/livescore/livescore/Startside.fxml"));
            AnchorPane startside = loader.load();

            StartsideController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(startside);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showHelloView(String homeTeam, String awayTeam, int matchID) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/livescore/livescore/hello-view.fxml"));
            AnchorPane helloView = loader.load();

            HandballAppController controller = loader.getController();
            controller.setMainApp(this);
            controller.setTeams(homeTeam, awayTeam);
            controller.setMatchID(matchID); // Set matchID in controller

            Scene scene = new Scene(helloView);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCreateTeam() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/livescore/livescore/oprethold.fxml"));
            AnchorPane opretHold = loader.load();

            OpretHoldController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(opretHold);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCreateMatch() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/livescore/livescore/opretkamp.fxml"));
            AnchorPane opretKamp = loader.load();

            OpretKampController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(opretKamp);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showManageTeams() {
        // Implement this method if needed
    }

    public void showLeaguePlacements() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/livescore/livescore/ligastilling.fxml"));
            AnchorPane ligaStilling = loader.load();

            LigaStillingController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(ligaStilling);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAdmin() {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/livescore/livescore/admin.fxml"));
        AnchorPane adminView = loader.load();

        AdminController controller = loader.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(adminView);
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}