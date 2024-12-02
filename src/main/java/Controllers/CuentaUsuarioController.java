package Controllers;

import Modelo.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaUsuarioController {

    @FXML
    private TextField txtContrasena;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtRol;

    public int idUser;
    Persona personaActualizada;

    public void initializeIdUser(int id) {
        idUser = id;
        initializeData(id);
    }

    public void initializeData(int idUsuario) {
        Persona persona = new Persona();
        Persona personita = persona.getInformationUser(idUsuario);
        personaActualizada = personita;
        txtNombre.setText(personita.getNombre());
        txtContrasena.setText(personita.getContrasena());
        txtCorreo.setText(personita.getCorreo());
        txtRol.setText(personita.getRol());
    }

    @FXML
    void btnCompras(ActionEvent event) {

    }

    @FXML
    void btnEditarCuenta(ActionEvent event) {
        String nombre = txtNombre.getText();
        String contrasena = txtContrasena.getText();

        if (personaActualizada.getNombre().equals(nombre) && personaActualizada.getContrasena().equals(contrasena)) {
            String mensaje = "Cambiar los datos para que se puedan actualizar";
            alertarErroneas(mensaje);
            return;
        }

        Persona persona = new Persona();
        boolean response = persona.actualizarDatos(idUser, nombre, contrasena);
        alertas(response);
        initializeData(idUser);


    }

    @FXML
    void btnInicioCuenta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuario.fxml"));
            Parent root = loader.load();
            UsuarioController controller = loader.getController();
            controller.setIdUsuario(idUser);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.btnInicio());
            stage.setOnCloseRequest(e -> controller.closeWindowsUserCuenta());

            Stage myStage = (Stage) this.txtCorreo.getScene().getWindow();
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
            Stage myStage = (Stage) this.txtNombre.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(CuentaUsuarioController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void btnInicioCuenta() {
    }

    public void alertas(boolean response) {
        if (response) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("usuario");
            alert.setHeaderText(null);
            alert.setContentText("Datos del usuario Modificado correctamente");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("usuario");
            alert.setHeaderText(null);
            alert.setContentText("Datos del usuario no modificados");
            alert.showAndWait();
        }
    }

    public void alertarErroneas(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("usuario");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
