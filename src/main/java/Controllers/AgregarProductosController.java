package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AgregarProductosController {

    @FXML
    private Button GuardarProducto;

    @FXML
    private Label txtAgregar;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtNombreProducto;

    @FXML
    private TextField txtPrecio;

    @FXML
    void ElegirCategoria(ActionEvent event) {

    }

    @FXML
    void btnAgregarProducto(ActionEvent event) {

    }

    @FXML
    void btnInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Administrador.fxml"));
            Parent root = loader.load();
            AdministradorController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnVentanaAgregarProductos());
            stage.setOnCloseRequest(e -> controller.closeWindowsAdministrador());

            Stage myStage = (Stage) this.txtAgregar.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(AgregarProductosController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void closeWindowsProductos() {
        try {
            Stage myStage = (Stage) this.txtAgregar.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(AgregarProductosController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
