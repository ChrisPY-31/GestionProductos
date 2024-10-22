package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button btnAgregarProductos;

    @FXML
    void AgregarProductos(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/AgregarProductos.fxml"));
        Parent root = loader.load();
        AgregarProductosController controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> controller.closeWindows());

        Stage myStage = (Stage) this.btnAgregarProductos.getScene().getWindow();
        myStage.close();

    }

    @FXML
    void eliminarProducto(ActionEvent event) {

    }

}
