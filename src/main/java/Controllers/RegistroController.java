package Controllers;

import Modelo.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroController implements Initializable {

    @FXML
    private PasswordField txtContraseñaUsuario;

    @FXML
    private TextField txtCorreoUsuario;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private ComboBox<String> RolList;

    @FXML
    private Label lblPasswordMensaje;

    @FXML
    private Label lblCorreoMensaje;


    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    //ajuste de 6-8 caracteres en {6,8}
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{6,8}$";

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

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
    void btnRegistrarUsuario(ActionEvent event) throws SQLException {

        if (!FormularioCompleto()){
            alertaFormulario();
            return;
        }

        String nombreUsuario = txtNombreUsuario.getText();
        String correoUsuario = txtCorreoUsuario.getText();
        String contrasenaUsuario = txtContraseñaUsuario.getText();
        String rol = RolList.getSelectionModel().getSelectedItem();


        //validaciones
        if (!validateEmail(correoUsuario)){
            alertas("Correo invalido");
            return;
        }

        if (!validatePassword(contrasenaUsuario)){
            alertas("Contraseña invalida");
            return;
        }

        Persona persona = new Persona(nombreUsuario, correoUsuario, contrasenaUsuario, rol);

        try{
            String response = persona.InsertarDatos();
            if(response.equals("Administrador")){
                navegacionAdministrador();
            }
            if(response.equals("usuario")){
                navegacionUsuario();
            }
            if(response.equals("Error")){
                alertas("Hubo un error intentelo mas tarde" );
            }
        }catch (SQLException ex) {
            mostrarAlerta("No se registro el usuario");
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private boolean FormularioCompleto() {
        return !txtNombreUsuario.getText().isEmpty()
                && !txtCorreoUsuario.getText().isEmpty()
                && !txtContraseñaUsuario.getText().isEmpty()
                && RolList.getSelectionModel().getSelectedItem() != null;
    }

    public void alertaFormulario(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Registro incompleto");
        alert.setHeaderText(null);
        alert.setContentText("Todos los campos son obligatorios");
        alert.showAndWait();
    }

    public void navegacionUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Usuario.fxml"));
            Parent root = loader.load();
            UsuarioController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.closeWindowsUser());

            Stage myStage = (Stage) this.txtNombreUsuario.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void navegacionAdministrador(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vistas/Administrador.fxml"));
            Parent root = loader.load();
            AdministradorController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e -> controller.closeWindowsAdministrador());

            Stage myStage = (Stage) this.txtNombreUsuario.getScene().getWindow();
            myStage.close();
        } catch (IOException e) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void alertas(String mensaje) {
        if (mensaje.equals("Se Registro correctamente")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }
    }

    public void limpiarFormulario() {
        txtCorreoUsuario.clear();
        txtNombreUsuario.clear();
    }

    public void closeWindowsRegistro() {
        try {

            Stage myStage = (Stage) this.txtNombreUsuario.getScene().getWindow();
            myStage.close();

        } catch (Exception e) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void ElegirRol(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Administrador", "usuario");
        RolList.setItems(list);

        txtContraseñaUsuario.focusedProperty().addListener((observable, oldValue, newValue  ) -> {
            if (newValue) {
                lblPasswordMensaje.setVisible(true);
            } else {
                lblPasswordMensaje.setVisible(false); //se oculta mensaje de recomendaciones
            }
        });

        txtContraseñaUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (validatePassword(newValue)) {
                lblPasswordMensaje.setVisible(false);
            } else{
                lblPasswordMensaje.setVisible(true); //invalido; muestra mensaje de error
            }
        });

        txtCorreoUsuario.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                lblCorreoMensaje.setVisible(true);
            }else {
                lblCorreoMensaje.setVisible(false);
            }
        });

        txtCorreoUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if (validateEmail(newValue)) {
                lblCorreoMensaje.setVisible(false);
            } else {
                lblCorreoMensaje.setVisible(true);
            }
        });

    }



}
