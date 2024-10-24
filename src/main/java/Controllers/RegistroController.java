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

public class RegistroController {

    @FXML
    private PasswordField txtContraseñaUsuario;

    @FXML
    private TextField txtCorreoUsuario;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtTelefonoUsuario;

    @FXML
    void btnIniciaSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/InicioSesion.fxml"));
            Parent root = loader.load();
            InicioSesionController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnRegister());
            stage.setOnCloseRequest(e -> controller.closeWindowsInicioSesion());

            Stage myStage = (Stage) this.txtNombreUsuario.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @FXML
    void btnRegistrarUsuario(ActionEvent event) {

    }

    public void closeWindowsRegistro() {
        try {

            Stage myStage = (Stage) this.txtNombreUsuario.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
