package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentaProductoController {

    @FXML
    private Text TituloProductos;

    @FXML
    void btnVenderProductos() {
        //vista vender

    }

    @FXML
    void btnVistaComprar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/CompraProductos.fxml"));
            Parent root = loader.load();
            CompraController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnVender());

            Stage myStage = (Stage) this.TituloProductos.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(VentaProductoController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    void btnVistaInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuario.fxml"));
            Parent root = loader.load();
            UsuarioController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.closeWindowsUser());

            Stage myStage = (Stage) this.TituloProductos.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(VentaProductoController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void closeWindowsVender() {
        try {
            Stage myStage = (Stage) this.TituloProductos.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(VentaProductoController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
