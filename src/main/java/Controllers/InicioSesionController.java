package Controllers;

import Modelo.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        String correo = txtCorreo.getText();
        String contrasena = txtContraseña.getText();
        Persona persona = new Persona(correo, contrasena);

        String role = persona.autenticacion();
        if(role.equals("Administrador")){
            navegacinoAdministrador();
        }
        if(role.equals("usuario")){
            navegacinoUsuario();
        }
        if(role.equals("Error de contraseña")){
            alertas("La contraseña o el correo son incorrectos" ,false);
        }


    }


    public void alertas(String mensaje, boolean rol) {
        if (rol) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de formulario");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();

        }
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

    public void navegacinoUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuario.fxml"));
            Parent root = loader.load();
            UsuarioController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.closeWindowsUser());

            Stage myStage = (Stage) this.txtCorreo.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void navegacinoAdministrador() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Administrador.fxml"));
            Parent root = loader.load();
            AdministradorController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.closeWindowsAdministrador());

            Stage myStage = (Stage) this.txtCorreo.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

}
