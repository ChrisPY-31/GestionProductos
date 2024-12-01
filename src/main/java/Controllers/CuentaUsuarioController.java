package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaUsuarioController {

    @FXML
    private TextField CorreoCuenta;

    @FXML
    private TextField NombreCuenta;

    @FXML
    private TextField PasswordCuenta;

    @FXML
    private TextField PasswordCuenta1;

    @FXML
    void btnCompras(ActionEvent event) {

    }

    @FXML
    void btnEditarCuenta(ActionEvent event) {

    }

    @FXML
    void btnGuardarInfo(ActionEvent event) {

    }

    @FXML
    void btnInicioCuenta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuario.fxml"));
            Parent root = loader.load();
            UsuarioController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnInicio());
            stage.setOnCloseRequest(e -> controller.closeWindowsUserCuenta());

            Stage myStage = (Stage) this.CorreoCuenta.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(CuentaUsuarioController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    void btnVentas(ActionEvent event) {

    }

    public void closeWindowsUsuario() {
        try {
            Stage myStage = (Stage) this.NombreCuenta.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(CuentaUsuarioController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void btnInicioCuenta() {
    }
}
