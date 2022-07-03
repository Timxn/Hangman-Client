module de.oose.gameservice.gameservice_client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    exports de.oose.gameservice.gameservice_client;
    exports de.oose.gameservice.api;
    opens de.oose.gameservice.gameservice_client to javafx.fxml;
}