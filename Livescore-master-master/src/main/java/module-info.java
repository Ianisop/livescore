module com.livescore.livescore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.livescore.livescore to javafx.fxml;
    exports com.livescore.livescore;
}