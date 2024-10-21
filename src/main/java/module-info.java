module com.example.gestion_productos {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;

    opens com.example.gestion_productos to javafx.fxml;
    exports com.example.gestion_productos;
}