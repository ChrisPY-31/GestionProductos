module com.example.gestion_productos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    opens Modelo to javafx.base;
    //exports Modelo;
    exports Application;
    opens Application to javafx.fxml;
    exports Controllers;
    opens Controllers to javafx.fxml;
}