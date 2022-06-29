module de.oose.gameservice.gameservice_client {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.oose.gameservice.gameservice_client to javafx.fxml;
    exports de.oose.gameservice.gameservice_client;
    exports de.oose.gameservice.api;
    opens de.oose.gameservice.api to javafx.fxml;
}