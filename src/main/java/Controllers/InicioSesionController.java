package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InicioSesionController {

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private TextField txtCorreo;

    @FXML
    void btnIniciarSesion(ActionEvent event) {

    }

    @FXML
    void btnRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Registro.fxml"));
            Parent root = loader.load();
            RegistroController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnIniciaSesion());
            stage.setOnCloseRequest(e -> controller.closeWindowsRegistro());

            Stage myStage = (Stage) this.txtContraseña.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void closeWindowsInicioSesion() {
        try {
            Stage myStage = (Stage) this.txtCorreo.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

}
